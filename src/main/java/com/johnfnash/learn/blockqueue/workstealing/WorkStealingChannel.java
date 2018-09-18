package com.johnfnash.learn.blockqueue.workstealing;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class WorkStealingChannel<P> implements WorkStealingEnableChannel<P> {

	// ˫�˶��У����Դ����˲���ֵ���ȡֵ���̳���BlockingQueue
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
		// ���ȴ�ָ���Ķ��л�ȡֵ
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
			//��ͼ�������ܹܶ��еĶ�β����ȡ������Ʒ��
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
			//�����ȡ �����ܹܶ��еĲ�Ʒ
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
