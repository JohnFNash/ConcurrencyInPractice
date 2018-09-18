package com.johnfnash.learn.basic.reentrantLock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

	final Lock lock = new ReentrantLock(); //������
	final Condition notFull = lock.newCondition(); //д�߳�����
	final Condition notEmpty = lock.newCondition(); //���߳�����
	
	final Object[] items = new Object[100]; //�������
	int putptr;	//д����
	int takptr; //������
	int count;  //�����д��ڵ����ݸ���
	
	public void put(Object x) throws InterruptedException {
		System .out.println("put wait lock");
		lock.lock();
		System.out.println("put get lock");
		
		try {
			while(count == items.length) {//����������� 
				notFull.await();//����д�߳�
			}
			
			items[putptr] = x; //��ֵ
			if(++putptr == items.length) {
				//���д����д�����е����һ��λ���ˣ���ô��Ϊ0
				putptr = 0;
			}
			
			++count;
			notEmpty.signal(); //���Ѷ��߳�
		} finally {
			lock.unlock();
		}
	}
	
	public Object take() throws InterruptedException {
		System.out.println("take wait lock");
		lock.lock();
		System.out.println("take get lock");
		try {
			while(count == 0) {//�������Ϊ��
				notEmpty.await();//�������߳�
			}
			
			Object x = items[takptr]; //ȡֵ
			if(++takptr == items.length) {
				//����������������е����һ��λ���ˣ���ô��Ϊ0
				takptr = 0;
			}
			
			--count; //����
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}
	
	
}
