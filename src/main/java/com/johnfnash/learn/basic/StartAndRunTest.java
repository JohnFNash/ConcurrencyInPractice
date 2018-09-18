package com.johnfnash.learn.basic;

public class StartAndRunTest {

	public static void main(String[] args) {
		StartAndRunTest test = new StartAndRunTest();
		
		Runner1 runner1 = test.new Runner1();  
        Runner2 runner2 = test.new Runner2();  
        Thread thread1 = new Thread(runner1);  
        Thread thread2 = new Thread(runner2); 
        
//        thread1.start();
//        thread2.start();
        thread1.run();  
        thread2.run();
	}
	
	class Runner1 implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {  
	            System.out.println("进入Runner1运行状态——————————" + i);  
	        }  
		}
		
	}
	
	class Runner2 implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {  
	            System.out.println("进入Runner2运行状态——————————" + i);  
	        }  
		}
		
	}

}
