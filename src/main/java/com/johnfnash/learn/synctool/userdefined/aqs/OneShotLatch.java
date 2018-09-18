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
	
	// ��������У�AQS ״̬������ʾ����״̬ -- �رգ�0�����ߴ򿪣�1��
	private class Sync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = 7516598266541324384L;

		protected int tryAcquireShared(int ignored) {
			// ��������ǿ��� (state==1)����ô��������óɹ�������ʧ��
			return (getState() == 1) ? 1: -1;
		}
		
		protected boolean tryReleaseShared(int ignored) {
			setState(1);	// ���ڴ򿪱���
			System.out.println("release");
			return true;	// �����������߳̿��Ի�ȡ�ñ���
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
