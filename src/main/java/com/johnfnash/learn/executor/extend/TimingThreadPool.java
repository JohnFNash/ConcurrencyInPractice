package com.johnfnash.learn.executor.extend;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class TimingThreadPool extends ThreadPoolExecutor {
	private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();	
	private final Logger log = Logger.getLogger("TimingThreadPool");
	private AtomicLong numTasks = new AtomicLong();
	private AtomicLong totalTime = new AtomicLong();
	
	public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		log.info(String.format("Thread %s: start %s", t, r));
		startTime.set(System.nanoTime());
	}
	
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		try {
			long endTime = System.nanoTime();
			long taskTime = endTime - startTime.get();
			numTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			log.info(String.format("Thread %s: end, time=%dns", r, taskTime));
		} finally {
			super.afterExecute(r, t);
		}
	}
	
	@Override
	protected void terminated() {
		try {
			log.info(String.format("Terminated: avg time=%dns", totalTime.get() / numTasks.get()));
		} finally {
			super.terminated();
		}
	}
	
	public static void main(String[] args) {
		TimingThreadPool pool = new TimingThreadPool(2, 2, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
		pool.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println(1);
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println(2);
			}
		});
		pool.shutdown();
	}

}
