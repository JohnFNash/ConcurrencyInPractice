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
				System.out.println("����˯��״̬");
				Thread.sleep(10000); // ��������״̬
				System.out.println("˯�����");
			} catch (InterruptedException e) {
				System.out.println("�õ��ж��쳣");
			}
			System.out.println("run����ִ�����");
		}
		
	}
	
}
