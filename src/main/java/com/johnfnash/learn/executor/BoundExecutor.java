package com.johnfnash.learn.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

import com.johnfnash.learn.annotation.ThreadSafe;

@ThreadSafe
public class BoundExecutor {

	private final Executor exec;
	private final Semaphore semaphore;
	
	public BoundExecutor(Executor exec, int bound) {
		this.exec = exec;
		this.semaphore = new Semaphore(bound);
	}
	
	public void submitTask(final Runnable command) throws InterruptedException {
		semaphore.acquire();
		try {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						command.run();
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} finally {
						semaphore.release();
					}
				}
			});
		} catch (RejectedExecutionException e) {
			semaphore.release();
		}
	}
	
	public void shutdown() {
		if(exec instanceof ExecutorService) {
			((ExecutorService)exec).shutdown();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		BoundExecutor exec = new BoundExecutor(Executors.newCachedThreadPool(), 2);
		exec.submitTask(new Runnable() {
			@Override
			public void run() {
				System.out.println("1");
			}
		});
		exec.submitTask(new Runnable() {
			@Override
			public void run() {
				System.out.println("2");
			}
		});
		exec.submitTask(new Runnable() {
			@Override
			public void run() {
				System.out.println("3");
			}
		});
		exec.submitTask(new Runnable() {
			@Override
			public void run() {
				System.out.println("4");
			}
		});
		
		exec.shutdown();
	}
	
}
