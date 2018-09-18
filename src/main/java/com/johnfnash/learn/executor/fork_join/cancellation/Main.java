package com.johnfnash.learn.executor.fork_join.cancellation;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		int array[] = ArrayGenerator.generateArray(1000);
		TaskManager manager = new TaskManager();
		ForkJoinPool pool = new ForkJoinPool();
		SearchNumberTask task = new SearchNumberTask(array, 0, array.length, 5, manager);
		pool.execute(task);
		pool.shutdown();

		try {
			pool.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();

			pool.shutdownNow();
		}
		System.out.printf("Main: The program has finished\n");
	}

}
