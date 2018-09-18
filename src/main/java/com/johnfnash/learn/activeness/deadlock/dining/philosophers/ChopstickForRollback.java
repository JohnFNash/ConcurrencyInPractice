package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

import java.util.concurrent.ConcurrentHashMap;

//������
public class ChopstickForRollback {

	private boolean taken = false; // �ж��Ǵ˿����Ƿ�����
	
	private final static ConcurrentHashMap<Integer, Long> leftTimeMap = new ConcurrentHashMap<Integer, Long>();
	private PhilosopherForRollback owner;
	private final int id;
	private ChopstickForRollback next;
	
	public ChopstickForRollback(int id) {
		this.id = id;
	}

	public synchronized void take(PhilosopherForRollback owner) throws InterruptedException {
		while(taken) {
			// ����ѱ�������ȴ�
			wait();
		}
		
		// ���û�б���������Ա����𣬲����� taken Ϊ true
		taken = true;
		this.owner = owner;
		Long now = System.nanoTime();
		leftTimeMap.put(this.owner.getId(), now);
		System.out.println(now + ": " + this.owner.getId() + " " + "������� " + this.id);
	}
	
	public synchronized void drop() {
		// ���¿���֮������takenΪfalse����֪ͨ��������
		taken = false;
		if(this.owner != null) {
			System.out.println(this.owner.getId() + " " + "���¿��� " + this.id);
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
