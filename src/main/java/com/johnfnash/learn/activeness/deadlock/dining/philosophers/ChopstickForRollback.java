package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

import java.util.concurrent.ConcurrentHashMap;

//筷子类
public class ChopstickForRollback {

	private boolean taken = false; // 判断是此筷子是否被拿起
	
	private final static ConcurrentHashMap<Integer, Long> leftTimeMap = new ConcurrentHashMap<Integer, Long>();
	private PhilosopherForRollback owner;
	private final int id;
	private ChopstickForRollback next;
	
	public ChopstickForRollback(int id) {
		this.id = id;
	}

	public synchronized void take(PhilosopherForRollback owner) throws InterruptedException {
		while(taken) {
			// 如果已被拿起，则等待
			wait();
		}
		
		// 如果没有被拿起，则可以被拿起，并设置 taken 为 true
		taken = true;
		this.owner = owner;
		Long now = System.nanoTime();
		leftTimeMap.put(this.owner.getId(), now);
		System.out.println(now + ": " + this.owner.getId() + " " + "拿起筷子 " + this.id);
	}
	
	public synchronized void drop() {
		// 放下筷子之后设置taken为false，并通知其他筷子
		taken = false;
		if(this.owner != null) {
			System.out.println(this.owner.getId() + " " + "放下筷子 " + this.id);
			leftTimeMap.remove(this.owner.getId());
			this.owner = null;
		}		
		notifyAll();
	}

	public synchronized PhilosopherForRollback getOwner() {
		return owner;
	}

	public synchronized void setOwner(PhilosopherForRollback owner) {
		this.owner = owner;
	}
	
	public Long getOccupyTime() {
		return leftTimeMap.get(this.owner.getId());
	}

	public int getId() {
		return id;
	}

	public ChopstickForRollback getNext() {
		return next;
	}

	public void setNext(ChopstickForRollback next) {
		this.next = next;
	}
	
}
