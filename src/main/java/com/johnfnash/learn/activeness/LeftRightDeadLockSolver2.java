package com.johnfnash.learn.activeness;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

// 使用 tryLock 来解决死锁
public class LeftRightDeadLockSolver2 {

	private final ReentrantLock left = new ReentrantLock();
	private final ReentrantLock right = new ReentrantLock();
	
	public void leftRight() {
		left.lock();
		if(left.isHeldByCurrentThread()) {
			System.out.println(Thread.currentThread().getName() + "获得了锁1");
		}
		try {
			Thread.sleep(300);
			right.tryLock(200, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(right.isHeldByCurrentThread()) {
				System.out.println(Thread.currentThread().getName() + "获得了锁2");
				right.unlock();
			}
		}
		
		if(left.isHeldByCurrentThread()) {
			left.unlock();
		}
	}
	
	public void rightLeft() {
		right.lock();
		if(right.isHeldByCurrentThread()) {
			System.out.println(Thread.currentThread().getName() + "获得了锁2");
		}
		try {
			Thread.sleep(300);
			left.tryLock(300, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(left.isHeldByCurrentThread()) {
				System.out.println(Thread.currentThread().getName() + "获得了锁1");
				left.unlock();
			}
		}
		
		if(right.isHeldByCurrentThread()) {
			right.unlock();
		}
	}
	
	public static void main(String[] args) {
		final LeftRightDeadLockSolver2 lock = new LeftRightDeadLockSolver2();
		
		Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				lock.leftRight();
			}
		});
		
		Thread threadB = new Thread(new Runnable() {
			@Override
			public void run() {
				lock.rightLeft();
			}
		});
		
		threadA.start();
		threadB.start();
	}
	
}
