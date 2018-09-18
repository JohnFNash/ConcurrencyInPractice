package com.johnfnash.learn.basic.wait_notify;

public class Test2 {

	public static void main(String[] args) {
		final Object object = new Object();
		
		Thread t1 = new Thread() {
			@Override
			public void run() {
				synchronized (object) {
					System.out.println("T1 start!");
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("T1 end!");
				}
			}
		};
		
		Thread t2 = new Thread() {
            public void run()
            {
                synchronized (object) {
                    System.out.println("T2 start!");
                    object.notify();
                    System.out.println("T2 end!");
                }
            }
        };
        
        t1.start();
        t2.start();
	}

}
