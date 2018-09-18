package com.johnfnash.learn.cancel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.johnfnash.learn.annotation.GuardedBy;

// 使用 volatile 类型的域来保存取消状态(退出过程中需要花费一些时间)
public class PrimeGenerator implements Runnable {
	
	@GuardedBy("this")
	private final List<BigInteger> primes = new ArrayList<BigInteger>();
	
	private volatile boolean cancelled;
	
	@Override
	public void run() {
		BigInteger p = BigInteger.ONE;
		while(!cancelled) {
			p = p.nextProbablePrime();
			synchronized (this) {
				primes.add(p);
			}
		}
	}
	
	public void cancel() {
		cancelled = true;
	}

	public synchronized List<BigInteger> get() {
		return new ArrayList<BigInteger>(primes);
	}
	
	public static void main(String[] args) throws InterruptedException {
		PrimeGenerator generator = new PrimeGenerator();
		new Thread(generator).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} finally {
			generator.cancel();
		}
		System.out.println(generator.get());
	}
	
}
