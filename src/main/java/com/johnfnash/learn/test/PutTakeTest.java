package com.johnfnash.learn.test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PutTakeTest {

	private static final ExecutorService pool 
			= Executors.newCachedThreadPool();
	private final AtomicInteger putSum = new AtomicInteger(0);
	private final AtomicInteger takeSum = new AtomicInteger(0);
	private final CyclicBarrier barrier;
	private final BoundedBuffer<Integer> bb;
	private final int nTrails, nPairs;
	
	public PutTakeTest(int capacity, int npairs, int ntrails) {
		this.bb = new BoundedBuffer<Integer>(capacity);
		this.nTrails = ntrails;
		this.nPairs = npairs;
		this.barrier = new CyclicBarrier(npairs * 2 + 1);
	}
	
	void test() {
		try {
			for(int i=0; i<nPairs; i++) {
				pool.execute(new Producer());
				pool.execute(new Consumer());
			}
			barrier.await(); // 等待所有的线程就绪
			barrier.await(); // 等待所有的线程执行完成
			System.out.println(putSum.get() + " , " + takeSum.get());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		new PutTakeTest(10, 10, 10000).test(); // 示例参数
		pool.shutdown();
	}
	
	class Producer implements Runnable {
		@Override
		public void run() {
			try {
				int seed = (this.hashCode() ^ (int)System.nanoTime());
				int sum = 0;
				barrier.await();
				for(int i=nTrails; i>0; --i) {
					bb.put(seed);
					System.out.println("put: " + seed);
					sum += seed;
					seed = xorShift(seed);
				}
				
				putSum.getAndAdd(sum);
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	class Consumer implements Runnable {
		@Override
		public void run() {
			try {
				barrier.await();
				int sum = 0;
				Integer tmp;
				for(int i=nTrails; i>0; --i) {
					tmp = bb.take();
					System.out.println("take: " + tmp);
					//sum += tmp==null ? 0 : tmp;
					sum += tmp;
				}
				takeSum.getAndAdd(sum);
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	int xorShift(int y) {
		y ^= (y << 6);
		y ^= (y >>> 21);
		y ^= (y << 7);
		return y;
	}
	
}
