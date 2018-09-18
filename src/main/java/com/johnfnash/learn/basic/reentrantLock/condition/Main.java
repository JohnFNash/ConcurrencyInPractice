package com.johnfnash.learn.basic.reentrantLock.condition;

public class Main {

	public static void main(String[] args) {
		final BoundedBuffer boundedBuffer = new BoundedBuffer();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("t1 run");
				for(int i=0; i<10; i++) {
					try {
						System.out.println("putting..");
						boundedBuffer.put(Integer.valueOf(i));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i=0;i<10;i++) {
					try {
						Object val = boundedBuffer.take();
						System.out.println(val);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		
		t1.start();
		t2.start();
	}

}
