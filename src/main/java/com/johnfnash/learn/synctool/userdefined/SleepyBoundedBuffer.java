package com.johnfnash.learn.synctool.userdefined;

import com.johnfnash.learn.annotation.ThreadSafe;

// ͨ����ѯ��������ʵ�ּ򵥵�����
// �ӵ����ߵĽǶȿ������ַ����ܺܺõ����У����������账��ʧ�������ԡ�
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
