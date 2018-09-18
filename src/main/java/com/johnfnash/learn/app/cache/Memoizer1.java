package com.johnfnash.learn.app.cache;

import java.util.HashMap;
import java.util.Map;

import com.johnfnash.learn.annotation.GuardedBy;

/**
 * 同一时刻只有一个线程能够知晓 compute，效率低 
 */
public class Memoizer1<A,V> implements Computable<A, V> {
	@GuardedBy("this")
	private final Map<A, V> cache = new HashMap<A,V>();
	
	private final Computable<A, V> c;
	
	public Memoizer1(Computable<A, V> c) {
		this.c = c;
	}
	
	@Override
	public synchronized V compute(A arg) throws InterruptedException {
		V result = cache.get(arg);
		if(result == null) {
			result = c.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}

}
