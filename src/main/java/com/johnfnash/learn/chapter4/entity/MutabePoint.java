package com.johnfnash.learn.chapter4.entity;

import com.johnfnash.learn.annotation.NotThreadSafe;

@NotThreadSafe
public class MutabePoint {

	public int x, y;

	public MutabePoint() {
		x = 0;
		y = 0;
	}
	
	public MutabePoint(MutabePoint p) {
		this.x = p.x;
		this.y = p.y;
	}
	
}
