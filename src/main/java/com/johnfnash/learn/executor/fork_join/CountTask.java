package com.johnfnash.learn.executor.fork_join;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 5390823896306412900L;
	
	private static final int THRESHOLD = 2; //��ֵ
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
		//��������㹻С�ͼ�������
		 boolean canCompute = (end-start) <= THRESHOLD;
		 if(canCompute) {
			 for(int i=start; i<=end; i++) {
				 sum += i;
			 }
		 } else {
			//���������ڷ�ֵ���ͷ��ѳ��������������
			 int middle = (start + end) / 2;
			 CountTask leftTask = new CountTask(start, middle);
			 CountTask rightTask = new CountTask(middle+1, end);
			 //ִ��������
			 leftTask.fork();
			 rightTask.fork();
			 
			//�ȴ�������ִ���꣬���õ�����
			 Integer leftResult = (Integer) leftTask.join();
			 Integer rightResult = (Integer) rightTask.join();
			//�ϲ�������
			 sum = leftResult + rightResult;
		 }
		return sum;
	}

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = (ForkJoinPool) Executors.newWorkStealingPool();
		//����һ���������񣬸������1+2+3+4+...+99+100
		CountTask task = new CountTask(1, 100);
		//ִ��һ������
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
