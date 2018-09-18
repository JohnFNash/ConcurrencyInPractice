package com.johnfnash.learn.completionService;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class CompletionServiceTest {

	private static final int THREAD_NUM = 10;
	private static ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);
	
	@Test
	public void test() {
		CompletionService<Long> completionService = new ExecutorCompletionService<>(executor);
		final int groupNum = 100000000 / THREAD_NUM;
		for(int i=1; i<=THREAD_NUM; i++) {
			int start = (i - 1) * groupNum + 1, end = i * groupNum;
			completionService.submit(new Callable<Long>() {
				@Override
				public Long call() throws Exception {
					long sum = 0L;
					for(int j = start; j <= end; j++) {
						sum += j;
					}
					return sum;
				}
			});
		}
		
		long result = 0L;
		try {
			for(int i=1; i<=THREAD_NUM; i++) {
				result += completionService.take().get();
				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("the result is " + result);
	}
			
}
