package com.johnfnash.learn.executor.fork_join.demo3;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		FolderProcessor task2 = new FolderProcessor("D:\\workspace\\study", ".java");
		FolderProcessor task3 = new FolderProcessor("D:\\workspace\\reference", ".java");
		pool.execute(task2);
		pool.execute(task3);

		do {
			System.out.printf("******************************************\n");
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
			System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
			System.out.printf("******************************************\n");

			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} while (!task2.isDone() || !task3.isDone());

		pool.shutdown();

		List<String> results = task2.join();
		System.out.printf("Task2: %d files found.\n", results.size());
		results = task3.join();
		System.out.printf("Task3: %d files found.\n", results.size());

	}

}
