package com.johnfnash.learn.basic;

public class SleepTest {

	private int i;
	private Object object = new Object();

	public static void main(String[] args) {
		SleepTest test = new SleepTest();
		MyThread thread1 = test.new MyThread();
		MyThread thread2 = test.new MyThread();
		thread1.start();
		thread2.start();
	}

	class MyThread extends Thread {

		@Override
		public void run() {
			synchronized (object) {
				i++;
				System.out.println("i:" + i);
				try {
					System.out.println("线程" + Thread.currentThread().getName() + "进入睡眠状态");
					// sleep方法不会释放锁
					Thread.sleep(10000);
				} catch (InterruptedException e) {
				}
				System.out.println("线程" + Thread.currentThread().getName() + "睡眠结束");
				i++;
				System.out.println("i:" + i);
			}
		}

	}

}
