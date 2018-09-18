package com.johnfnash.learn.cancel;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// һЩ�Զ����ȡ�������޷���������Ŀ⺯��ʵ�����ý���
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
			// �������ߵ��ٶȴ���������ʱ����������߶����������ˣ��������˳��������߾ͻ�������һֱ�ȴ��ڿ�������
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
