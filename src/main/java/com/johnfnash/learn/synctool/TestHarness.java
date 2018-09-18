package com.johnfnash.learn.synctool;

import java.util.concurrent.CountDownLatch;

public class TestHarness {

	public long timeTasks(int nThread) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThread);
		
		Thread t;
		for(int i=0; i<nThread; i++) {
			t = new Thread() {
				public void run() {
					try {
						System.out.println(Thread.currentThread().getName() + " ready....");
						startGate.await();
						try {
							System.out.println(Thread.currentThread().getName() + " running ....");
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException ignored) {
					}
				}
			};
			
			t.start();
		}
		
		Thread.sleep(2000);
		
		long start = System.nanoTime();
		System.out.println("xxxxx");
		startGate.countDown();
		endGate.await();
		long end = System.nanoTime();
		return end - start;
	}
	
	public static void main(String[] args) throws InterruptedException {
		TestHarness test = new TestHarness();
		long time = test.timeTasks(5);
		System.out.println("time spent: " + time);
	}
	
}
