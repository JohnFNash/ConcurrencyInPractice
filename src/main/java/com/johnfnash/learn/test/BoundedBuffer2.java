package com.johnfnash.learn.test;

import com.johnfnash.learn.annotation.ThreadSafe;

// Bounded buffer using condition queues
@ThreadSafe
public class BoundedBuffer2 <V> extends BaseBoundedBuffer<V> {

	// CONDITION PREDICATE: not-full (!isFull())
    // CONDITION PREDICATE: not-empty (!isEmpty())
	
	protected BoundedBuffer2() {
		this(1000);
	}
	
	protected BoundedBuffer2(int capacity) {
		super(capacity);
	}

	// BLOCKS-UNTIL: not-full
	public synchronized void put(V v) throws InterruptedException {
		while(isFull()) {
			wait();
		}
		
		doPut(v);
		notifyAll();
	}
	
	// BLOCKS-UNTIL: not-empty
    public synchronized V take() throws InterruptedException {
    	while (isEmpty()) {
            wait();
    	}
    	
    	V v = doTake();
    	notifyAll();
    	return v;
    }
	
    // BLOCKS-UNTIL: not-full
    // Alternate form of put() using conditional notification
    public synchronized void alternatePut(V v) throws InterruptedException {
    	while (isFull()) {
            wait();
    	}
    	
    	boolean wasEmpty = isEmpty();
        doPut(v);
        if (wasEmpty) {
            notifyAll();
        }
    }
    
}
