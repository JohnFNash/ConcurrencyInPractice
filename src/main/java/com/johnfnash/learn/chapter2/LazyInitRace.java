package com.johnfnash.learn.chapter2;

import com.johnfnash.learn.annotation.NotThreadSafe;

@NotThreadSafe
public class LazyInitRace {
	private ExpensiveObject instance = null;
	
	public ExpensiveObject getInstance() {
		// 先检查后执行
		if(instance == null) {
			instance = new ExpensiveObject();
		}
		return instance;
	}
	
}
