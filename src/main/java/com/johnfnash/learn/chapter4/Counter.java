package com.johnfnash.learn.chapter4;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

@ThreadSafe
public class Counter {
	@GuardedBy("this")
	private long value = 0;
	
	public synchronized long getValue() {
		return value;
	}
	
	public synchronized long increment() {
		if(value == Long.MAX_VALUE) {
			throw new IllegalStateException("counter overflow");
		}
		
		return ++value;
	}
	
}
