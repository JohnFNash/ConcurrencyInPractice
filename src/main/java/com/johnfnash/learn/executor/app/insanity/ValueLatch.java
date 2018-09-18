package com.johnfnash.learn.executor.app.insanity;

import java.util.concurrent.CountDownLatch;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

// Result-bearing latch used by ConcurrentPuzzleSolver
@ThreadSafe
public class ValueLatch<T> {
	@GuardedBy("this")
	private T value = null;
	
	private final CountDownLatch done = new CountDownLatch(1);
	
	public boolean isSet() {
		return (done.getCount() == 0);
	}
	
	public synchronized void setValue(T newValue) {
		if(!isSet()) {
			value = newValue;
			done.countDown();
		}
	}
	
	public T getValue() throws InterruptedException {
		done.await();
		synchronized (this) {
			return value;
		}
	}
	
}
