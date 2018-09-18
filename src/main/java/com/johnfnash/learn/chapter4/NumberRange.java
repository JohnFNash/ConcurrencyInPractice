package com.johnfnash.learn.chapter4;

import java.util.concurrent.atomic.AtomicInteger;

import com.johnfnash.learn.annotation.NotThreadSafe;

/**
 * ����״̬���� lower �� upper ���Ǳ˴˶����ģ���� NumbreRange ���ܽ��̰߳�ȫ��ί�и������̰߳�ȫ״̬���� 
 */
@NotThreadSafe
public class NumberRange {

	// ������������lower <= upper
	private final AtomicInteger lower = new AtomicInteger(0);
	private final AtomicInteger upper = new AtomicInteger(0);
	
	public void setLower(int i) {
		// ע��--����ȫ�ġ��ȼ���ִ�С�
		if(i > upper.get()) {
			throw new IllegalArgumentException("can't set lower to " + i + " > upper");
		}
		lower.set(i);
	}
	
	public void setUpper(int i) {
		// ע��--����ȫ�ġ��ȼ���ִ�С�
		if(i < lower.get()) {
			throw new IllegalArgumentException("can't set upper to " + i + " < lower");
		}
		upper.set(i);
	}
	
	public boolean isInRange(int i) {
		return (i >= lower.get() && i <= upper.get());
	}
	
}
