package com.johnfnash.learn.chapter4.entity;

import com.johnfnash.learn.annotation.Immutable;

@Immutable
public class Point {

	public final int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
}
