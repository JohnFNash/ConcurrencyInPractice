package com.johnfnash.learn.blockqueue.workstealing;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkStealingExample {

	private final WorkStealingEnableChannel<String> channel;
	private final BlockingDeque<String>[] managedQueues;
	private final int nCPU;
	private final int consumerCount;
	private Consumer[] consumers;
	private Producer[] producers;
	private volatile AtomicInteger count = new AtomicInteger();
	
	@SuppressWarnings("unchecked")
	public WorkStealingExample() {
		this.nCPU = Runtime.getRuntime().availableProcessors();
		this.consumerCount = this.nCPU/2 + 1;
		System.out.println(nCPU + ", " + consumerCount);
		
		managedQueues = new LinkedBlockingDeque[consumerCount];
		channel = new WorkStealingChannel<String>(managedQueues);
		
		consumers = new Consumer[consumerCount];
		for(int i=0; i<consumerCount; i++){
            managedQueues[i] = new LinkedBlockingDeque<String>();
            consumers[i] = new Consumer(managedQueues[i]);
        }
		
		producers = new Producer[nCPU];
		for(int i=0; i<nCPU; i++){
			producers[i] = new Producer();
        }
	}
	
	private class Producer extends Thread {
        private boolean stopFlag = false;

        @Override
        public void run() {
        	String value;
        	while(!stopFlag) {
        		try {
        			value = String.valueOf(count.incrementAndGet());
    				channel.put(value);
    				
    				System.out.println(Thread.currentThread().getName() + " produce product: " + value);
    				
    				Thread.sleep(new Random().nextInt(85));
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
        	}
        }
        
        public void stopWork() {
        	this.stopFlag = true;
        }
	}
	
	private class Consumer extends Thread {
		private volatile boolean stopFlag = false;
		private final BlockingDeque<String> workQueue;

        public Consumer(BlockingDeque<String> workQueue) {
            this.workQueue = workQueue;
        }
		
		@Override
		public void run() {
			 /**
             * 实现了工作窃取算法
             */
			while(!stopFlag) {
				try {
					channel.take(workQueue);
					
					Thread.sleep(new Random().nextInt(50));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void stopWork() {
        	this.stopFlag = true;
        }
	}
	
	public void startAll() {
		for(int i=0; i<consumers.length; i++){
            consumers[i].start();
        }
		
		for(int i=0; i<producers.length; i++){
			producers[i].start();
        }
	}
	
	public void stopAll() {
		for(int i=0; i<producers.length; i++){
			producers[i].stopWork();
        }
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<consumers.length; i++){
            consumers[i].stopWork();
        }
	}
	
	public static void main(String[] args) {
		WorkStealingExample example = new WorkStealingExample();
		example.startAll();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		example.stopAll();
	}
	
}
