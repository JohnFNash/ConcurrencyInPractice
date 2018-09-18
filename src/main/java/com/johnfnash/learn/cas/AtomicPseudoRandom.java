package com.johnfnash.learn.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.johnfnash.learn.annotation.ThreadSafe;

// Random number generator using AtomicInteger
@ThreadSafe
public class AtomicPseudoRandom extends PseudoRandom {

	private AtomicInteger seed;
	
	public AtomicPseudoRandom(int seed) {
		this.seed = new AtomicInteger(seed);
	}
	
	public int nextInt(int n) {
		while(true) {
			int s = seed.get();
			int nextSeed = calculateNext(s);
			
			if(seed.compareAndSet(s, nextSeed)) {
				int remainder = s % n;
				return remainder > 0 ? remainder : remainder + n;
			}
		}
	}
	
	public static void main(String[] args) {
		int num = 1;
		while(num <= 256) {
			long start = System.currentTimeMillis();
			
			final int SEED = 1000;
			AtomicPseudoRandom rnd = new AtomicPseudoRandom(SEED);
			
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
