package com.johnfnash.learn.app.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.johnfnash.learn.util.LaunderThrowable;

public class Memoizer<A, V> implements Computable<A, V> {

	private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> c;

	public Memoizer(Computable<A, V> c) {
		this.c = c;
	}

	@Override
	public V compute(A arg) throws InterruptedException {
		while (true) {
			Future<V> f = cache.get(arg);
			if(f == null){
				Callable<V> eval = new Callable<V>() {
					public V call() throws InterruptedException {
						return c.compute(arg);
					}
				};
				
				FutureTask<V> ft = new FutureTask<V> (eval);
				f = cache.putIfAbsent(arg, ft);
				if(f == null) {
					// 如果之前尚未写入，则写入成功，运行 run 方法
					f = ft;
					ft.run();
				}
			}
			
			try {
				return f.get();
			} catch(CancellationException e) {
				// 如果操作被取消
				cache.remove(arg, f);
			} catch (ExecutionException e) {
				throw LaunderThrowable.launderThrowable(e.getCause());
			}
		}
	}

}
