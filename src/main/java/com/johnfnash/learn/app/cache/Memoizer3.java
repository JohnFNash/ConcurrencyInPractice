package com.johnfnash.learn.app.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.johnfnash.learn.util.LaunderThrowable;

/**
 * 即仍然存在两个线程计算出相同值得漏洞，这个漏洞的发生概率要远小于Memoizer2中发生的概率，但由于compute方法中的if代码块仍然是非原子的“先检查再执行”操作，因此两个线程仍然有可能同一时间内调用compute来计算相同的值，
 * 即二者都没有在缓存中找到期望的值，因此都开始计算
 */
public class Memoizer3<A, V> implements Computable<A, V> {

	private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A,V> c;
	
	public Memoizer3(Computable<A, V> c) {
		this.c = c;
	}

	@Override
	public V compute(A arg) throws InterruptedException {
		Future<V> f = cache.get(arg);
		if(f == null) {
			Callable<V> eval = new Callable<V>() {
				public V call() throws InterruptedException {
					return c.compute(arg);
				}
			};
			FutureTask<V> ft = new FutureTask<>(eval);
			f = ft;
			cache.put(arg, ft);
		
			ft.run(); //这里将调用c.compute
		}
		try {
			return f.get();
		} catch (ExecutionException e) {
			throw LaunderThrowable.launderThrowable(e);
		}
	}

}
