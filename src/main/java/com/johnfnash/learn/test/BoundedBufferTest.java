package com.johnfnash.learn.test;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

public class BoundedBufferTest {

	@Test
	public void testIsEmptyWhemConstructed() {
		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		Assert.assertTrue(bb.isEmpty());
		Assert.assertFalse(bb.isFull());
	}
	
	@Test
	public void testIsFullAfterPuts() throws InterruptedException {
		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		for(int i=0; i<10; i++) {
			bb.put(i);
		}
		Assert.assertTrue(bb.isFull());
		Assert.assertFalse(bb.isEmpty());
	}
	
	@Test
	public void testTakeBlocksWhenEmpty() {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
		Thread take = new Thread() {
			@Override
			public void run() {
				try {
					bb.take();
					fail(); // 如果执行到这里，那么表示出现了一个错误
				} catch (InterruptedException success) {
				}
			}
		};
		
		try {
			take.start();
			final int LOCK_DETECT_TIMEOUT = 5000;
			Thread.sleep(LOCK_DETECT_TIMEOUT);
			take.interrupt();
			take.join(LOCK_DETECT_TIMEOUT);
			Assert.assertFalse(take.isAlive());
		} catch (Exception unexpected) {
			fail();
		}
	}
	
}
