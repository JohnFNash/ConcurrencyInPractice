package com.johnfnash.learn.executor.fork_join.exception;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		int array[] = new int[100];
		Task task = new Task(array,0,100);
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(task);
		
		try {
			pool.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (task.isCompletedAbnormally()) {
			System.out.printf("Main: An exception has ocurred\n");
			System.out.printf("Main: %s\n",task.getException());
		}
		System.out.printf("Main: Result: %d", task.join());
	}

}
