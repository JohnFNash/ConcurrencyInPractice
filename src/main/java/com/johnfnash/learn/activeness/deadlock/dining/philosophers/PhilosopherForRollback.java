package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

//哲学家类
public class PhilosopherForRollback implements Runnable {
	private final ChopstickForRollback left; //左筷子
	private final ChopstickForRollback right; //右筷子
		
	private final int id; //哲学家编号
	private final int ponderFactor; //根据这个属性设置思考时间
	
	private Random rand = new Random(47);
	
	public PhilosopherForRollback(ChopstickForRollback left, ChopstickForRollback right, int ident, int ponder) {
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
			retry1:
			while(!Thread.interrupted()) {
				System.out.println(this + " " + "thinking");
				pause();
				left.take(this);
				
				PhilosopherForRollback leftOwner = left.getOwner();
				PhilosopherForRollback rightOwner = right.getOwner();
				if(leftOwner == this && rightOwner!=null && rightOwner!= this) {
					if(leftOwner != this) {
						continue retry1;
					}
					
					// 如果下一个哲学家已经集齐了两支筷子，则当前哲学家放弃自己持有的左手筷子，重新开始
					if(right.getOwner() == rightOwner && right.getNext().getOwner() == rightOwner) {
						left.drop();
						continue retry1;
					}
					
					// 判断哪个哲学家先获取的左边的筷子，如果时间相同，则比较哲学家的序号大小，最终确定谁该放在左手的筷子
					int del = left.getOccupyTime().compareTo(right.getOccupyTime());
					if(del < 0) {
						right.drop();
					} else if(del > 0) {
						left.drop();
						continue retry1;
					} else {
						if(leftOwner.getId() < rightOwner.getId()) {
							right.drop();
						} else if(leftOwner.getId() > rightOwner.getId()) {
							left.drop();
							continue retry1;
						}
					}
				} else if(leftOwner != this) {
					continue retry1;
				}
				
				// 如果左手的筷子被其它进程放下了，则重新开始
				if(left.getOwner() != this) {
					continue retry1;
				}
				
				right.take(this);
				pause();
				System.out.println(this + " " + "吃");
				left.drop();
				right.drop();
			}
		} catch (InterruptedException e) {
			System.out.println(this + " 退出   ");
		}
	}

	@Override
	public String toString() {
		return "Phiosopher: " + id;
	}

	public int getId() {
		return id;
	}
	
}
