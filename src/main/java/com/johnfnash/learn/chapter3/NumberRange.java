package com.johnfnash.learn.chapter3;

public class NumberRange {

	private volatile int lower = 0;
	private volatile int upper = 10;
	
	public int getLower() {
		return lower;
	}
	
	public int getUpper() {
		return upper;
	}
	
	public void setLower(int value) {
		if(lower > upper) {
			throw new IllegalArgumentException("lower can not be larger than upper!");
		}
		lower = value;
	}
	
	public void setUpper(int value) {
		if(value < lower) {
			throw new IllegalArgumentException("upper can not be smaller than lower!");
		}
		upper = value;
	}
	
	public static void main(String[] args) {
		NumberRange range = new NumberRange();
		range.setLower(0);
		range.setUpper(10);
		
		Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				range.setLower(8);
			}
		});
		
		Thread threadB = new Thread(new Runnable() {
			@Override
			public void run() {
				range.setUpper(5);
			}
		});
		
		threadA.start();
		threadB.start();
		
		System.out.println(range);
	}

	@Override
	public String toString() {
		return "NumberRange [lower=" + lower + ", upper=" + upper + "]";
	}
	
}
