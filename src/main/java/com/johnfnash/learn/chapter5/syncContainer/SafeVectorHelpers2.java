package com.johnfnash.learn.chapter5.syncContainer;

import java.util.Vector;

/**
 * SafeVectorHelpers2
 * <p/>
 * Compound actions on a Vector that may produce confusing results
 *
 * @author Brian Goetz and Tim Peierls
 */
public class SafeVectorHelpers2 {
	
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
		
		Thread.sleep(10);
		
		synchronized (vector) {
			for(int i=vector.size()-1; i>=0; i--) {
				System.out.println(vector.get(i));
			}
		}
	}
	
}
