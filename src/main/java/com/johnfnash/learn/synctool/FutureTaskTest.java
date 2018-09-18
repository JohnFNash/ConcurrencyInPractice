package com.johnfnash.learn.synctool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

	public static void main(String[] args)  {
		long startTime = System.currentTimeMillis(); 
        System.out.println("主线程开始...");
        
        FutureTask<Integer> future = new FutureTask<>(new Task());
        System.out.println("进行Task任务计算的子线程开始...");
        new Thread(future).start();;

        try {
        	System.out.println("主线程正在执行自己的任务...");
			Thread.sleep(1000);
			System.out.println("主线程尝试获取Task结果...");
			
			System.out.println("时间过去"+(System.currentTimeMillis()-startTime));
            System.out.println("主线程获取到结果为:"+future.get());
            System.out.println("时间过去"+(System.currentTimeMillis()-startTime));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
        
	}
	
}

class Task implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		//花3s模拟计算过程
        Thread.sleep(3000);
        //模拟计算结果是1
        return 1;
	}
	
}
