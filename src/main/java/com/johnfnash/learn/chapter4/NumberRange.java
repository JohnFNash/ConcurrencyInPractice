package com.johnfnash.learn.chapter4;

import java.util.concurrent.atomic.AtomicInteger;

import com.johnfnash.learn.annotation.NotThreadSafe;

/**
 * 由于状态变量 lower 和 upper 不是彼此独立的，因此 NumbreRange 不能将线程安全性委托给它的线程安全状态变量 
 */
@NotThreadSafe
public class NumberRange {

	// 不变性条件：lower <= upper
	private final AtomicInteger lower = new AtomicInteger(0);
	private final AtomicInteger upper = new AtomicInteger(0);
	
	public void setLower(int i) {
		// 注意--不安全的“先检查后执行”
		if(i > upper.get()) {
			throw new IllegalArgumentException("can't set lower to " + i + " > upper");
		}
		lower.set(i);
	}
	
	public void setUpper(int i) {
		// 注意--不安全的“先检查后执行”
		if(i < lower.get()) {
			throw new IllegalArgumentException("can't set upper to " + i + " < lower");
		}
		upper.set(i);
	}
	
	public boolean isInRange(int i) {
		return (i >= lower.get() && i <= upper.get());
	}
	
}
