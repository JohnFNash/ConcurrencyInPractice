package com.johnfnash.learn.chapter2;

import com.johnfnash.learn.annotation.NotThreadSafe;

@NotThreadSafe
public class LazyInitRace {
	private ExpensiveObject instance = null;
	
	public ExpensiveObject getInstance() {
		// �ȼ���ִ��
		if(instance == null) {
			instance = new ExpensiveObject();
		}
		return instance;
	}
	
}
