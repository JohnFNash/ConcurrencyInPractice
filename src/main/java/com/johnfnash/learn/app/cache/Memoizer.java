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
					// ���֮ǰ��δд�룬��д��ɹ������� run ����
					f = ft;
					ft.run();
				}
			}
			
			try {
				return f.get();
			} catch(CancellationException e) {
				// ���������ȡ��
				cache.remove(arg, f);
			} catch (ExecutionException e) {
				throw LaunderThrowable.launderThrowable(e.getCause());
			}
		}
	}

}
