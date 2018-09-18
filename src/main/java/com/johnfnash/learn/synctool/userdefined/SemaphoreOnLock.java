package com.johnfnash.learn.synctool.userdefined;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

// 使用 Lock 构造 Semaphore
@ThreadSafe
public class SemaphoreOnLock {

	private final Lock lock = new ReentrantLock();
	
	// 条件谓词：permitsAvailable (permits > 0)
	private final Condition permitsAvailable = lock.newCondition();
	
	@GuardedBy("this")
	private int permits;
	
	public SemaphoreOnLock(int permits) {
		lock.lock();
		try {
			this.permits = permits;
		} finally {
			lock.unlock();
		}
	}
	
	// 阻塞并直到：permitsAvailable
	public void acquire() throws InterruptedException {
		lock.lock();
		try {
			while(permits <= 0) {
				permitsAvailable.await();
			}
			
			--permits;
		} finally {
			lock.unlock();
		}
	}
	
	public void release() {
		lock.lock();
		try {
			++permits;
			permitsAvailable.signal();
		} finally {
			lock.unlock();
		}
	}
	
}
