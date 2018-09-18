package com.johnfnash.learn.synctool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

	public static void main(String[] args)  {
		long startTime = System.currentTimeMillis(); 
        System.out.println("���߳̿�ʼ...");
        
        FutureTask<Integer> future = new FutureTask<>(new Task());
        System.out.println("����Task�����������߳̿�ʼ...");
        new Thread(future).start();;

        try {
        	System.out.println("���߳�����ִ���Լ�������...");
			Thread.sleep(1000);
			System.out.println("���̳߳��Ի�ȡTask���...");
			
			System.out.println("ʱ���ȥ"+(System.currentTimeMillis()-startTime));
            System.out.println("���̻߳�ȡ�����Ϊ:"+future.get());
            System.out.println("ʱ���ȥ"+(System.currentTimeMillis()-startTime));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
        
	}
	
}

class Task implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		//��3sģ��������
        Thread.sleep(3000);
        //ģ���������1
        return 1;
	}
	
}
