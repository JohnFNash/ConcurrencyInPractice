package com.johnfnash.learn.activeness;

// 保持一致的加锁顺序来解决死锁
public class LeftRightDeadLockSolver1 {

	private final Object left = new Object();
	private final Object right = new Object();
	
	public void leftRight() {
		synchronized (left) {
			System.out.println("get left lock in leftRight");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (right) {
				System.out.println("get right lock in leftRight");
				System.out.println(1);
			}
		}
	}
	
	public void rightLeft() {
		synchronized (left) {
			System.out.println("get left lock in rightLeft");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (right) {
				System.out.println("get right lock in rightLeft");
				System.out.println(2);
			}
		}
	}
	
	public static void main(String[] args) {
		final LeftRightDeadLockSolver1 lock = new LeftRightDeadLockSolver1();
		
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
