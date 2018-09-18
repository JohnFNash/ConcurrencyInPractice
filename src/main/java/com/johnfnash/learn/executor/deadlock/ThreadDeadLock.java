package com.johnfnash.learn.executor.deadlock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadDeadLock {

	ExecutorService exec = Executors.newCachedThreadPool();
			//Executors.newSingleThreadExecutor();
	
	public Future<String> submitTask(Callable<String> task) {
		return exec.submit(task);
	}
	
	public void shutdown() {
		exec.shutdown();
		try {
			exec.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class LoadFileTask implements Callable<String> {
		@SuppressWarnings("unused")
		private final String fileName;
		
		public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }
		
		@Override
		public String call() throws Exception {
			// Here's where we would actually read the file
			return "";
		}
		
	}
	
	class RenderPageTask implements Callable<String> {

		@Override
		public String call() throws Exception {
			Future<String> header, footer;
			header = exec.submit(new LoadFileTask("header.html"));
			footer = exec.submit(new LoadFileTask("footer.html"));
			String page = renderBody();
			 // Will deadlock -- task waiting for result of subtask
			return header.get() + page + footer.get();
		}
		
		private String renderBody() {
            // Here's where we would actually render the page
            return "";
        }
		
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThreadDeadLock deadLock = new ThreadDeadLock();
		Future<String> futureTask = deadLock.submitTask(deadLock.new RenderPageTask());
		System.out.println(futureTask.get());
		
		//Thread.sleep(5000);
		//deadLock.shutdown();
	}
	
}
