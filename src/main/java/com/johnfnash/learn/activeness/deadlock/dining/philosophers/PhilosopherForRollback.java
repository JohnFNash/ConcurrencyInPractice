package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

//��ѧ����
public class PhilosopherForRollback implements Runnable {
	private final ChopstickForRollback left; //�����
	private final ChopstickForRollback right; //�ҿ���
		
	private final int id; //��ѧ�ұ��
	private final int ponderFactor; //���������������˼��ʱ��
	
	private Random rand = new Random(47);
	
	public PhilosopherForRollback(ChopstickForRollback left, ChopstickForRollback right, int ident, int ponder) {
		this.left = left;
		this.right = right;
		this.id = ident;
		this.ponderFactor = ponder;
	}
	
	// ��ѧ��˼��
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
					
					// �����һ����ѧ���Ѿ���������֧���ӣ���ǰ��ѧ�ҷ����Լ����е����ֿ��ӣ����¿�ʼ
					if(right.getOwner() == rightOwner && right.getNext().getOwner() == rightOwner) {
						left.drop();
						continue retry1;
					}
					
					// �ж��ĸ���ѧ���Ȼ�ȡ����ߵĿ��ӣ����ʱ����ͬ����Ƚ���ѧ�ҵ���Ŵ�С������ȷ��˭�÷������ֵĿ���
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
				
				// ������ֵĿ��ӱ��������̷����ˣ������¿�ʼ
				if(left.getOwner() != this) {
					continue retry1;
				}
				
				right.take(this);
				pause();
				System.out.println(this + " " + "��");
				left.drop();
				right.drop();
			}
		} catch (InterruptedException e) {
			System.out.println(this + " �˳�   ");
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
