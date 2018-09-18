package com.johnfnash.learn.basic.reentrantReadWriteLock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest1 {

	public static void main(String[] args) {
		// �����˻�
		MyCount myCount = new MyCount("4238920615242830", 10000);
		// �����û�����ָ���˻�
		User user = new User("Tommy", myCount);
		
		// �ֱ�����3������ȡ�˻���Ǯ�����߳� �� 3���������˻���Ǯ�����߳�
		for(int i=0; i<3; i++) {
			user.getCash();
			user.setCash((i+1) * 1000);
		}
	}
	
}

class User {

	private final String name;   //�û��� 
	private MyCount myCount;        //��Ҫ�������˻� 
	private final ReadWriteLock myLock;   //ִ�в��������������
	
	User(String name, MyCount myCount) {
		this.name = name; 
		this.myCount = myCount; 
		this.myLock = new ReentrantReadWriteLock();
	}
	
	public void getCash() {
		new Thread() {
			public void run() {
				myLock.readLock().lock();
				try {
					System.out.println(Thread.currentThread().getName() +" getCash start"); 
					myCount.getCash();
					Thread.sleep(1);
					System.out.println(Thread.currentThread().getName() +" getCash end"); 
				} catch (InterruptedException e) {
				} finally {
					myLock.readLock().unlock();
				}
			}
		}.start();
	}
	
	public void setCash(final int cash) {
		new Thread() {
			public void run() {
				myLock.writeLock().lock(); 
				try {
					System.out.println(Thread.currentThread().getName() +" setCash start"); 
					myCount.setCash(cash);
					Thread.sleep(1);
					System.out.println(Thread.currentThread().getName() +" setCash end"); 
				} catch (InterruptedException e) {
				} finally {
					myLock.writeLock().unlock();
				}
			}
		}.start();
	}
	
	
	@Override
	public String toString() {
		return this.name;
	}
}

class MyCount {

	private String id;  //�˺� 
	private int cash;	//�˻����
	
	MyCount(String id, int cash) {
		this.id = id;
		this.cash = cash;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}
	
}