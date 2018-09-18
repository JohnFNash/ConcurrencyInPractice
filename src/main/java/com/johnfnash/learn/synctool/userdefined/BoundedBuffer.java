package com.johnfnash.learn.synctool.userdefined;

import com.johnfnash.learn.annotation.ThreadSafe;

// ʹ����������ʵ�ֵ��н绺��
// �����ã�����ʵ������ȷ��״̬�����Թ���
@ThreadSafe
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

	// ����ν�ʣ�not-full (!isFull())
	// ����ν�ʣ�not-empty (!isEmpty())
	
	protected BoundedBuffer(int capacity) {
		super(capacity);
	}
	
	// ������ֱ����not-full
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
	
	// ������ֱ����not-empty
	public synchronized V take() throws InterruptedException {
		while(isEmpty()) {
			wait();
		}
		
		boolean wasFull = isFull();
		V v = doTake();
		if(wasFull) {
			//�����Ƴ�Ԫ��֮ǰ����������ʱ��Ž���֪ͨ
			notifyAll();
		}
		return v;
	}

}
