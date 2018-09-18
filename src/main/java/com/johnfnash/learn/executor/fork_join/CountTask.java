package com.johnfnash.learn.executor.fork_join;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 5390823896306412900L;
	
	private static final int THRESHOLD = 2; //阈值
	private Integer start;
	private Integer end;
	
	public CountTask(Integer start, Integer end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		Integer sum = Integer.valueOf(0);
		//如果任务足够小就计算任务
		 boolean canCompute = (end-start) <= THRESHOLD;
		 if(canCompute) {
			 for(int i=start; i<=end; i++) {
				 sum += i;
			 }
		 } else {
			//如果任务大于阀值，就分裂成两个子任务计算
			 int middle = (start + end) / 2;
			 CountTask leftTask = new CountTask(start, middle);
			 CountTask rightTask = new CountTask(middle+1, end);
			 //执行子任务
			 leftTask.fork();
			 rightTask.fork();
			 
			//等待子任务执行完，并得到其结果
			 Integer leftResult = (Integer) leftTask.join();
			 Integer rightResult = (Integer) rightTask.join();
			//合并子任务
			 sum = leftResult + rightResult;
		 }
		return sum;
	}

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = (ForkJoinPool) Executors.newWorkStealingPool();
		//生成一个计算任务，负责计算1+2+3+4+...+99+100
		CountTask task = new CountTask(1, 100);
		//执行一个任务
		Future<Integer> result = forkJoinPool.submit(task);
		try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
}
