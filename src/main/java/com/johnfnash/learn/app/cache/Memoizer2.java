package com.johnfnash.learn.app.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 由于if相关的不是原子操作，如果某个线程启动了一个开销很大的计算，而其他线程并不知道这个计算正在进行，那么狠可能会重复这个计算
 */
public class Memoizer2<A, V> implements Computable<A, V> {

	private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
	private final Computable<A, V> c;
	
	public Memoizer2(Computable<A, V> c) {
		this.c = c;
	}
	
	@Override
	public V compute(A arg) throws InterruptedException {
		V result = cache.get(arg);
		if(result == null) {
			result = c.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}

}
