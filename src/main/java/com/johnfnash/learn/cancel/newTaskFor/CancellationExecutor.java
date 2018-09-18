package com.johnfnash.learn.cancel.newTaskFor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.johnfnash.learn.annotation.ThreadSafe;

@ThreadSafe
public class CancellationExecutor extends ThreadPoolExecutor {

	public CancellationExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
		if(callable instanceof CancellableTask) {
			return ((CancellableTask<T>)callable).newTask();
		} else {
			return super.newTaskFor(callable);
		}		
	}
	
}
