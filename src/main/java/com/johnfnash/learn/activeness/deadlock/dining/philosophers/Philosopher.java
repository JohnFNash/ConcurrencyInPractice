package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

//哲学家类
public class Philosopher implements Runnable {
	private Chopstick left; //左筷子
	private Chopstick right; //右筷子
		
	private final int id; //哲学家编号
	private final int ponderFactor; //根据这个属性设置思考时间
	
	private Random rand = new Random(47);
	
	public Philosopher(Chopstick left, Chopstick right, int ident, int ponder) {
		this.left = left;
		this.right = right;
		this.id = ident;
		this.ponderFactor = ponder;
	}
	
	// 哲学家思考
	private void pause() throws InterruptedException {
		if(ponderFactor == 0) {
			return;
		}
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				System.out.println(this + " " + "thinking");
				pause();
				right.take();
				System.out.println(this + " " + "拿右筷子");
				left.take();
				System.out.println(this + " " + "拿左筷子");
				pause();
				System.out.println(this + " " + "吃");
				right.drop();
				System.out.println(this + " " + "放下右筷子");
				left.drop();
				System.out.println(this + " " + "放下左筷子");
			}
		} catch (InterruptedException e) {
			System.out.println(this + " 退出   ");
		}
	}

	@Override
	public String toString() {
		return "Phiosopher: " + id;
	}
	
}
