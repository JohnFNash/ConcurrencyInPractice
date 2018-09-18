package com.johnfnash.learn.synctool.userdefined;

import com.johnfnash.learn.annotation.ThreadSafe;

// 使用条件队列实现的有界缓存
// 简单易用，而且实现了明确的状态依赖性管理
@ThreadSafe
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

	// 条件谓词：not-full (!isFull())
	// 条件谓词：not-empty (!isEmpty())
	
	protected BoundedBuffer(int capacity) {
		super(capacity);
	}
	
	// 阻塞并直到：not-full
	public synchronized void put(V v) throws InterruptedException {
		while(isFull()) {
			wait();
		}
		
		boolean wasEmpty = isEmpty();
		doPut(v);
		if(wasEmpty) {
			notifyAll();
		}
	}
	
	// 阻塞并直到：not-empty
	public synchronized V take() throws InterruptedException {
		while(isEmpty()) {
			wait();
		}
		
		boolean wasFull = isFull();
		V v = doTake();
		if(wasFull) {
			//仅当移除元素之前队列是满的时候才进行通知
			notifyAll();
		}
		return v;
	}

}
