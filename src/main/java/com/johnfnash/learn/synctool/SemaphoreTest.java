package com.johnfnash.learn.synctool;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	private static final int THREAD_COUNT = 12;
	
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
	
	private static Semaphore s = new Semaphore(4);
	
	public static void main(String[] args) {
		for(int i=0; i<THREAD_COUNT; i++) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						s.acquire();
						System.out.println("save data");
						Thread.sleep(new Random().nextInt(2000));
						s.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		threadPool.shutdown();
	}

}
