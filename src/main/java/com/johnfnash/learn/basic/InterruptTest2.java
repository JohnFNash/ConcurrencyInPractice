package com.johnfnash.learn.basic;

public class InterruptTest2 {

	public static void main(String[] args) {
		InterruptTest2 test = new InterruptTest2();
        MyThread thread = test.new MyThread();
        thread.start();
        try {
        	Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
        //thread.interrupt();
        thread.setStop(true);
	}
	
	class MyThread extends Thread {
		private volatile boolean isStop = false;

		@Override
		public void run() {
			int i = 0;
			// ���Ƽ�ʹ�� isInterrupted() ���ж��߳�
//			while(i < Integer.MAX_VALUE && ! isInterrupted()) {
//				System.out.println(i + " while ѭ��");
//				i++;
//			}
			while(i < Integer.MAX_VALUE && !isStop) {
				System.out.println(i + " while ѭ��");
				i++;
			}
		}
		
		public void setStop(boolean stop) {
			this.isStop = stop;
		}
	}
	
}
