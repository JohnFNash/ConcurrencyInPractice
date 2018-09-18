package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//��ѧ����
public class PhilosopherForSemaphore implements Runnable {
	private Chopstick left; //�����
	private Chopstick right; //�ҿ���
		
	private final int id; //��ѧ�ұ��
	private final int ponderFactor; //���������������˼��ʱ��
	
	private Random rand = new Random(47);
	
	private Semaphore semaphore;
	
	public PhilosopherForSemaphore(Chopstick left, Chopstick right, int ident, int ponder, Semaphore semaphore) {
		this.left = left;
		this.right = right;
		this.id = ident;
		this.ponderFactor = ponder;
		this.semaphore = semaphore;
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
			while(!Thread.interrupted()) {
				semaphore.acquire();
				System.out.println(this + " " + "thinking");
				pause();
				right.take();
				System.out.println(this + " " + "���ҿ���");
				left.take();
				System.out.println(this + " " + "�������");
				pause();
				System.out.println(this + " " + "��");
				right.drop();
				System.out.println(this + " " + "�����ҿ���");
				left.drop();
				System.out.println(this + " " + "���������");
				semaphore.release();
			}
		} catch (InterruptedException e) {
			System.out.println(this + " �˳�   ");
		}
	}

	@Override
	public String toString() {
		return "Phiosopher: " + id;
	}
	
}
