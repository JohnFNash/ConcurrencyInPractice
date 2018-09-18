package com.johnfnash.learn.executor.app.insanity;

public class Move {

	private int xDel;
	private int yDel;
	
	public Move(int xDel, int yDel) {
		this.xDel = xDel;
		this.yDel = yDel;
	}

	public int getxDel() {
		return xDel;
	}

	public void setxDel(int xDel) {
		this.xDel = xDel;
	}

	public int getyDel() {
		return yDel;
	}

	public void setyDel(int yDel) {
		this.yDel = yDel;
	}
	
	@Override
	public String toString() {
		return "[x=" + xDel + ", y=" + yDel + "]";
	}
	
}
