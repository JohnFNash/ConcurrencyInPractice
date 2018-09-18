package com.johnfnash.learn.activeness.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JstackCase {

	public static ExecutorService executor = Executors.newFixedThreadPool(5);
	private final static Object lock = new Object();
	
	public static void main(String[] args) {
		Task task1 = new Task();
		Task task2 = new Task();
		executor.execute(task1);
		executor.execute(task2);
		executor.shutdown();
	}
	
	static class Task implements Runnable {
		@Override
		public void run() {
			synchronized (lock) {
				//calculate();
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void calculate() {
			@SuppressWarnings("unused")
			long i = 0;
			while(true) {
				i++;
			}
		}
	}
	
}
