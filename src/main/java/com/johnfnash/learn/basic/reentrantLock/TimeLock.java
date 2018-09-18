package com.johnfnash.learn.basic.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TimeLock implements Runnable {

	public final static ReentrantLock lock = new ReentrantLock();

	@Override
	public void run() {
		try {
			if (lock.tryLock(5, TimeUnit.SECONDS)) {
				Thread.sleep(6 * 1000);
			} else {
				System.out.println(Thread.currentThread().getName() + " get Lock Failed");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// ��ѯ��ǰ�߳��Ƿ񱣳ִ���
			if (lock.isHeldByCurrentThread()) {
				System.out.println(Thread.currentThread().getName() + " release lock");
				lock.unlock();
			}
		}
	}

	/**
	 * �ڱ����У�����ռ�������̻߳����������6�룬����һ���߳��޷���5��ĵȴ�ʱ���ڻ�����������������ʧ�ܡ�
	 */
	public static void main(String[] args) {
		TimeLock timeLock = new TimeLock();
		Thread t1 = new Thread(timeLock,"�߳�1");
		Thread t2 = new Thread(timeLock,"�߳�2");
		t1.start();
		t2.start();
	}

}
