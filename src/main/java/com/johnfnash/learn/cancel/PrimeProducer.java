package com.johnfnash.learn.cancel;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PrimeProducer extends Thread {

	private final BlockingQueue<BigInteger> queue;
	
	public PrimeProducer(BlockingQueue<BigInteger> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		try {
			BigInteger p = BigInteger.ONE;
			while(!Thread.currentThread().isInterrupted()) {
				queue.put(p = p.nextProbablePrime());
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	public void cancel() {
		interrupt();
	}
	
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<>(100);
		PrimeProducer producer = new PrimeProducer(primes);
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
