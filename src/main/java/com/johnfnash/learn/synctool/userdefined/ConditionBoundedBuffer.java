package com.johnfnash.learn.synctool.userdefined;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

// ʹ����ʾ���������Ļ���
@ThreadSafe
public class ConditionBoundedBuffer<T> {

	protected final Lock lock = new ReentrantLock();
	
	// ����ν�ʣ�notFull (count < items.length)
	private final Condition notFull = lock.newCondition();
	
	// ����ν�ʣ�notEmpty (count > 0)
	private final Condition notEmpty = lock.newCondition();
	
	private final int BUFFER_SIZE = 1000;
	
	@SuppressWarnings("unchecked")
	@GuardedBy("this")
	private final T[] items = (T[]) new Object[BUFFER_SIZE];
	
	@GuardedBy("this")
	private int tail, head, count;
	
	// ������ֱ���� notFull
	public void put(T x) throws InterruptedException {
		lock.lock();
		try {
			while(count == items.length) {
				notFull.await();
			}
			
			items[tail] = x;
			if(++tail == items.length) {
				tail = 0;
			}
			++count;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}
	
	// ������ֱ����notEmpty
	public T take() throws InterruptedException {
		lock.lock();
		try {
			while(count == 0) {
				notEmpty.await();
			}
			
			T x = items[head];
			items[head] = null;
			if(++head == items.length) {
				head = 0;
			}
			--count;
			notFull.signal();
			
			return x;
		} finally {
			lock.unlock();
		}
	}
	
}
