package com.johnfnash.learn.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadExecutorTest {

	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		
		executor.scheduleAtFixedRate(() -> 
			System.out.println(System.currentTimeMillis())
		, 1000, 2000, TimeUnit.MILLISECONDS);
		
		try {
			// close pool
			Thread.sleep(10000);
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(!executor.isTerminated()) {
				executor.shutdownNow();
			}
		}
	}

}
