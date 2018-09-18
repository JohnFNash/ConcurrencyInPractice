package com.johnfnash.learn.synctool.userdefined;

import com.johnfnash.learn.annotation.ThreadSafe;

// 通过轮询与休眠来实现简单的阻塞
// 从调用者的角度看，这种方法能很好地运行，调用者无需处理失败与重试。
@ThreadSafe
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

	private final int SLEEP_GRANULABITY = 100;
	
	protected SleepyBoundedBuffer(int capacity) {
		super(capacity);
	}
	
	public void put(V v) throws InterruptedException {
		while (true) {
			synchronized (this) {
				if(!isFull()) {
					doPut(v);
					return;
				}
			}
			Thread.sleep(SLEEP_GRANULABITY);
		}
	}
	
	public V take() throws InterruptedException {
		while(true) {
			synchronized (this) {
				if(!isEmpty()) {
					return doTake();
				}
			}
			Thread.sleep(SLEEP_GRANULABITY);
		}
	}
	
}
