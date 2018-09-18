package com.johnfnash.learn.basic;

public class InterruptTest {

	public static void main(String[] args) {
		InterruptTest test = new InterruptTest();
        MyThread thread = test.new MyThread();
        thread.start();
        try {
        	Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
        thread.interrupt();
	}
	
	class MyThread extends Thread {

		@Override
		public void run() {
			try {
				System.out.println("进入睡眠状态");
				Thread.sleep(10000); // 进入阻塞状态
				System.out.println("睡眠完毕");
			} catch (InterruptedException e) {
				System.out.println("得到中断异常");
			}
			System.out.println("run方法执行完毕");
		}
		
	}
	
}
