package com.johnfnash.learn.chapter5.syncContainer;

import java.util.concurrent.ConcurrentHashMap;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
		System.out.println(map);
	}
}