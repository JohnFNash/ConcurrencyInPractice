package com.johnfnash.learn.app.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.johnfnash.learn.util.LaunderThrowable;

/**
 * ����Ȼ���������̼߳������ֵͬ��©�������©���ķ�������ҪԶС��Memoizer2�з����ĸ��ʣ�������compute�����е�if�������Ȼ�Ƿ�ԭ�ӵġ��ȼ����ִ�С���������������߳���Ȼ�п���ͬһʱ���ڵ���compute��������ͬ��ֵ��
 * �����߶�û���ڻ������ҵ�������ֵ����˶���ʼ����
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
		
			ft.run(); //���ｫ����c.compute
		}
		try {
			return f.get();
		} catch (ExecutionException e) {
			throw LaunderThrowable.launderThrowable(e);
		}
	}

}
