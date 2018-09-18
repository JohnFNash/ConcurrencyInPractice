package com.johnfnash.learn.synctool.userdefined.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import com.johnfnash.learn.annotation.ThreadSafe;

@ThreadSafe
public class OneShotLatch {
	
	private final Sync sync = new Sync();
	
	public void signal() {
		sync.releaseShared(0);
	}
	
	public void await() throws InterruptedException {
		sync.acquireSharedInterruptibly(0);
	}
	
	// 这个例子中，AQS 状态用来表示闭锁状态 -- 关闭（0）或者打开（1）
	private class Sync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = 7516598266541324384L;

		protected int tryAcquireShared(int ignored) {
			// 如果闭锁是开的 (state==1)，那么这个操作得成功，否则将失败
			return (getState() == 1) ? 1: -1;
		}
		
		protected boolean tryReleaseShared(int ignored) {
			setState(1);	// 现在打开闭锁
			System.out.println("release");
			return true;	// 现在其他的线程可以获取该闭锁
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		OneShotLatch latch = new OneShotLatch();
		new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("wait");
					latch.await();
					System.out.println("start");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(111);
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("wait");
					latch.await();
					System.out.println("start");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(222);
			}
		}.start();
		
		Thread.sleep(2000);
		latch.signal();
	}
	
	
}
