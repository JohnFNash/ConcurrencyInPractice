package com.johnfnash.learn.chapter5.syncContainer;

import java.util.Vector;

/**
 * SafeVectorHelpers3
 */
public class SafeVectorHelpers3 {
	
	public static Object deleteLast(Vector<?> list) {
		synchronized (list) {
			int lastIndex = list.size() - 1;
			return list.remove(lastIndex);
		}
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
		
//		synchronized (vector) {
//			for (Object obj : vector) {
//				System.out.println(obj);
//			}
//		}
		
		// Лђеп
		Thread.sleep(10);
		Object[] arr;
		synchronized (vector) {
			arr = new Object[vector.size()];
			vector.toArray(arr);
		}
		if(null != arr) {
			for (int i = 0; i < arr.length; i++) {
				System.out.println("query: " + arr[i]);
			}
		}
		
	}
	
}
