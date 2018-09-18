package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

//筷子类
public class Chopstick {

	private boolean taken = false; // 判断是此筷子是否被拿起
	
	public synchronized void take() throws InterruptedException {
		while(taken) {
			// 如果已被拿起，则等待
			wait();
		}
		
		// 如果没有被拿起，则可以被拿起，并设置 taken 为 true
		taken = true;
	}
	
	public synchronized void drop() {
		// 放下筷子之后设置taken为false，并通知其他筷子
		taken = false;
		notifyAll();
	}
	
}
