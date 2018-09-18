package com.johnfnash.learn.chapter3;

public class Singleton {

	private volatile static Singleton instance;
	
	public static Singleton getInstance() {
		if(instance == null) {
			synchronized (Singleton.class) {
				if(instance == null) {
					instance = new Singleton();
				}
			}
		}
		
		return instance;
	}
	
	public static void main(String[] args) {
		Singleton a = Singleton.getInstance();
		Singleton b = Singleton.getInstance();
		System.out.println(a == b);
	}
	
}
