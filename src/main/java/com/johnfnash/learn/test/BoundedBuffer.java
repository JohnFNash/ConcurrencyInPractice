package com.johnfnash.learn.test;

import java.util.concurrent.Semaphore;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

@ThreadSafe
public class BoundedBuffer<E> {

	private final Semaphore availbleItems, availableSpaces;
	
	@GuardedBy("this")
	private final E[] items;
	
	@GuardedBy("this")
	private int putPosition = 0, takePosition = 0;
	
	@SuppressWarnings("unchecked")
	public BoundedBuffer(int capacity) {
		availbleItems = new Semaphore(0);
		availableSpaces = new Semaphore(capacity);
		items = (E[]) new Object[capacity];
	}
	
	public boolean isEmpty() {
		return availbleItems.availablePermits() == 0;
	}
	
	public boolean isFull() {
		return availableSpaces.availablePermits() == 0;
	}
	
	public void put(E x) throws InterruptedException {
		availableSpaces.acquire();
		doInsert(x);
		availbleItems.release();
	}
	
	public E take() throws InterruptedException {
		availbleItems.acquire();
		E item = doExtract();
		availableSpaces.release();
		return item;
	}
	
	public synchronized void doInsert(E x) {
		int i = putPosition;
		items[i] = x;
		putPosition = (++i == items.length) ? 0 : i;
	}
	
	public synchronized E doExtract() {
		int i = takePosition;
		E x = items[i];
		items[i] = null;
		takePosition = (++i == items.length) ? 0 : i;
		return x;
	}
	
}
