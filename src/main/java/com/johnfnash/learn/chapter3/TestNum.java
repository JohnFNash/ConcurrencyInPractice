package com.johnfnash.learn.chapter3;

public class TestNum {

	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
		public Integer initialValue() {
			return 0;
		}
	};
	
	public int getNextNum() {
		seqNum.set(seqNum.get() + 1);
		return seqNum.get();
	}
	
	public static void main(String[] args) {
		TestNum sn  = new TestNum();
		
		//三个线程共享SN 产生序列号
		ThreadClient t1 = new ThreadClient(sn);
        ThreadClient t2 = new ThreadClient(sn);
        ThreadClient t3 = new ThreadClient(sn);
        t1.start();
        t2.start();
        t3.start();
	}
	
}

class ThreadClient extends Thread {
	private TestNum sn;
	
	public ThreadClient(TestNum sn){
        this.sn = sn;
    }
	
	public void run() {
		for(int i=0; i<3; i++) {
			System.out.println("Thread: "+ Thread.currentThread().getName()
					+ " sn: " + sn.getNextNum());
		}
	}
}
