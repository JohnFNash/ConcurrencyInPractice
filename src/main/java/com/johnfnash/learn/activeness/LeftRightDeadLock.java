package com.johnfnash.learn.activeness;

// ¼òµ¥µÄËøË³ÐòËÀËø
public class LeftRightDeadLock {

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
		synchronized (right) {
			System.out.println("get right lock in rightLeft");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (left) {
				System.out.println("get left lock in rightLeft");
				System.out.println(2);
			}
		}
	}
	
	public static void main(String[] args) {
		final LeftRightDeadLock lock = new LeftRightDeadLock();
		
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
