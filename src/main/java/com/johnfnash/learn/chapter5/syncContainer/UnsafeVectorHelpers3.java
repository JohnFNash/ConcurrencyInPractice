package com.johnfnash.learn.chapter5.syncContainer;

import java.util.Vector;

public class UnsafeVectorHelpers3 {
	
	public static Object deleteLast(Vector<?> list) {
		int lastIndex = list.size() - 1;
		return list.remove(lastIndex);
	}

	public static void main(String[] args) throws InterruptedException {
		Vector<Object> vector = new Vector<Object>();
		for(int i=1; i<=1000; i++) {
			vector.add(i);
		}
		
		Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!vector.isEmpty()) {
					System.out.println("delete: " + deleteLast(vector));
				}
			}
		});
		threadA.start();
		
		// 可能抛出 ConcurrentModificationException 异常
		for (Object obj : vector) {
			System.out.println(obj);
		}
	}
	
}
