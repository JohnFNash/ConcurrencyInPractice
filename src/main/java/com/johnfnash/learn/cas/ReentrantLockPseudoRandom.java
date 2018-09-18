package com.johnfnash.learn.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.johnfnash.learn.annotation.ThreadSafe;

// ReentrantLockPseudoRandom
@ThreadSafe
public class ReentrantLockPseudoRandom extends PseudoRandom {

	private final Lock lock = new ReentrantLock(false);
	private int seed;
	
	ReentrantLockPseudoRandom(int seed) {
		this.seed = seed;
	}
	
	public int nextInt(int n) {
		lock.lock();
		try {
			int s = seed;
			seed = calculateNext(s);
			
			int remainder = s % n;
			return remainder > 0 ? remainder : remainder + n;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {		
		int num = 1;
		while(num <= 256) {
			long start = System.currentTimeMillis();
			
			final int SEED = 1000;
			ReentrantLockPseudoRandom rnd = new ReentrantLockPseudoRandom(SEED);
			
			ExecutorService exec = Executors.newFixedThreadPool(num);
			for(int i=0; i<num; i++) {
				exec.execute(new Runnable() {
					@Override
					public void run() {
						rnd.calculateNext(SEED);
					}
				});
			}
			exec.shutdown();
			
			System.out.println(num + " cost: " + (System.currentTimeMillis() - start));
			
			num <<= 1;
		}
	}
	
}
