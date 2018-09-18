package com.johnfnash.learn.cancel;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// 一些自定义的取消机制无法与可阻塞的库函数实现良好交互
public class BrokenPrimeProducer extends Thread {

	private final BlockingQueue<BigInteger> queue;
	private volatile boolean cancelled = false;
	
	public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		try {
			BigInteger p = BigInteger.ONE;
			// 当生产者的速度大于消费者时，如果生产者队列容量满了，消费者退出，生产者就会阻塞，一直等存在可用容量
			while(!cancelled) {
				queue.put(p = p.nextProbablePrime());
			}
		} catch (InterruptedException e) {
			
		}
	}
	
	public void cancel() {
		cancelled = true;
	}
	
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(100);
		BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
		producer.start();
		try {
			int count = 0;
			while(count < 21) {
				System.out.println(primes.take());
				count++;
				Thread.sleep(100);
			}
		} finally {
			producer.cancel();
		}
	}
}
