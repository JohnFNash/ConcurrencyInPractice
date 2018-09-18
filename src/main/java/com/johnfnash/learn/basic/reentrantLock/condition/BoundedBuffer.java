package com.johnfnash.learn.basic.reentrantLock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

	final Lock lock = new ReentrantLock(); //锁对象
	final Condition notFull = lock.newCondition(); //写线程条件
	final Condition notEmpty = lock.newCondition(); //读线程条件
	
	final Object[] items = new Object[100]; //缓存队列
	int putptr;	//写索引
	int takptr; //读索引
	int count;  //队列中存在的数据个数
	
	public void put(Object x) throws InterruptedException {
		System .out.println("put wait lock");
		lock.lock();
		System.out.println("put get lock");
		
		try {
			while(count == items.length) {//如果队列满了 
				notFull.await();//阻塞写线程
			}
			
			items[putptr] = x; //赋值
			if(++putptr == items.length) {
				//如果写索引写到队列的最后一个位置了，那么置为0
				putptr = 0;
			}
			
			++count;
			notEmpty.signal(); //唤醒读线程
		} finally {
			lock.unlock();
		}
	}
	
	public Object take() throws InterruptedException {
		System.out.println("take wait lock");
		lock.lock();
		System.out.println("take get lock");
		try {
			while(count == 0) {//如果队列为空
				notEmpty.await();//阻塞读线程
			}
			
			Object x = items[takptr]; //取值
			if(++takptr == items.length) {
				//如果读索引读到队列的最后一个位置了，那么置为0
				takptr = 0;
			}
			
			--count; //个数
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}
	
	
}
