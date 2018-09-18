package com.johnfnash.learn.chapter4.entity;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

@ThreadSafe
public class SafePoint {
	@GuardedBy("this")
	private int x, y;
	
	private SafePoint(int[] a) {
		this(a[0], a[1]);
	}
	
	public SafePoint(SafePoint p) {
		// 注意：如果使用 this(p.x, p.y) ，会产生竞态条件
		this(p.get());
	}
	
	public SafePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public synchronized int[] get() {
		return new int[] { x, y };
	}
	
	public synchronized void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
}
