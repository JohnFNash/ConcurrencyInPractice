package com.johnfnash.learn.basic.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class FairLock implements Runnable {

	public final static ReentrantLock fairLock = new ReentrantLock(true);
	
	@Override
	public void run() {
		while(true) {
			try {
				fairLock.lock();
				System.out.println(Thread.currentThread().getName()+"�������!");
			} finally {
				fairLock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		FairLock fairLock = new FairLock();
		Thread t1 = new Thread(fairLock, "�߳�1");
		Thread t2 = new Thread(fairLock, "�߳�2");
		t1.start();
		t2.start();
	}

}
