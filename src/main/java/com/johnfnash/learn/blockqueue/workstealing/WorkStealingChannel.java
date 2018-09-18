package com.johnfnash.learn.blockqueue.workstealing;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class WorkStealingChannel<P> implements WorkStealingEnableChannel<P> {

	// 双端队列，可以从两端插入值或获取值，继承了BlockingQueue
	private final BlockingDeque<P>[] managedQueues;
	
	public WorkStealingChannel(BlockingDeque<P>[] managedQueues) {
		super();
		this.managedQueues = managedQueues;
	}
	
	@Override
	public P take() throws InterruptedException {
		return take(null);
	}
	
	@Override
	public void put(P product) throws InterruptedException {
		int targetIndex = (product.hashCode() % managedQueues.length);
		BlockingQueue<P> targetQueue = managedQueues[targetIndex];
		targetQueue.put(product);
	}	
	
	@Override
	public P take(BlockingDeque<P> preferredQueue) throws InterruptedException {
		BlockingDeque<P> targetQueue = preferredQueue;
		P product = null;

		int targetIndex = 0;
		// 优先从指定的队列获取值
		if(null != targetQueue) {
			product = targetQueue.poll();
			for (int i=0; i<managedQueues.length; i++) {
				if(targetQueue == managedQueues[i] ) {
					targetIndex = i;
					break;
				}
			}
			if(product != null) {
				System.out.println(targetIndex + " processing " + product);
			}
		}
		
		int queueIndex = -1;
		/*while(null != product) {
			queueIndex = (queueIndex + 1) % managedQueues.length;
			targetQueue = managedQueues[queueIndex];
			//试图从其他受管队列的队尾“窃取”“产品”
			product = targetQueue.pollLast();
			
			if(product != null) {
				if(preferredQueue != targetQueue) {
					System.out.println(targetIndex + " stealed from " + queueIndex + ": " + product);
				} else {
					System.out.println(targetIndex + " processing : " + product);
				}
			}
			
			if(preferredQueue == targetQueue) {
				break;
			}
		}*/
		
		if(null == product) {
			//随机窃取 其他受管队列的产品
			queueIndex = (int) (System.currentTimeMillis() % managedQueues.length);
			targetQueue = managedQueues[queueIndex];
			product = targetQueue.pollLast();
			if(product != null && preferredQueue != targetQueue) {
				System.out.println(targetIndex + " stealed from " + queueIndex + ": " + product);
			} else if(product != null) {
				System.out.println(targetIndex + " processing " + product);
			}
		}
		
		return product;
	}
	
}
