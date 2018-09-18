package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// ͨ������ͬʱʹ����Դ�ľ�������Ŀ����������
// ϵͳ����N���������̡� ���涨ÿ��������Ҫ����R��ĳ����Դ�� ��ϵͳ�ṩK��N*��R-1��+1��ͬ����Դʱ��
// ���۲��ú��ַ�ʽ����ʹ�ã�һ�����ᷢ������
public class DiningPhilosophersSolver2 {

	public static void main(String[] args) throws InterruptedException {
		int ponder = 5;
		if(args.length > 0) {
			ponder = Integer.parseInt(args[0]);
		}
		
		int size = 5;
		if(args.length > 1) {
			size = Integer.parseInt(args[1]);
		}
		
		final int resourceForEachThread = 2;
		int semaphoreNum = (size - 1)/(resourceForEachThread - 1);
		System.out.println("semaphoreNum: " + semaphoreNum);
		
		final Semaphore semaphore = new Semaphore(semaphoreNum);
		ExecutorService exec = Executors.newCachedThreadPool();
		
		Chopstick[] stick = new Chopstick[size];
		for(int i = 0; i < size; i++) {
			stick[i] = new Chopstick();
		}
		
		for(int i = 0; i < size; i++) {
			PhilosopherForSemaphore p = new PhilosopherForSemaphore(stick[i], stick[(i+1)%size], i, ponder, semaphore);
			exec.execute(p);
		}
		
		TimeUnit.SECONDS.sleep(3);
		exec.shutdown();
	}
	
}
