package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 通过阻止循环等待这个死锁的条件来解决死锁
public class DiningPhilosopherSolver1 {

	public static void main(String[] args) throws InterruptedException {
		int ponder = 5;
		if(args.length > 0) {
			ponder = Integer.parseInt(args[0]);
		}
		
		int size = 5;
		if(args.length > 1) {
			size = Integer.parseInt(args[1]);
		}
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		Chopstick[] stick = new Chopstick[size];
		for(int i = 0; i < size; i++) {
			stick[i] = new Chopstick();
		}
		
		for(int i = 0; i < size; i++) {
//			Philosopher p = new Philosopher(stick[i], stick[(i+1)%size], i, ponder);
//			exec.execute(p);
			
			// 死锁解决方案1
			if(i < size - 1) {
				Philosopher p = new Philosopher(stick[i], stick[(i+1)%size], i, ponder);
				exec.execute(p);
			} else {
				// 改成 最后一个哲学家拿筷子的顺序是先拿左，再拿右，来阻止循环等待这个死锁的条件
				Philosopher p = new Philosopher(stick[0], stick[i], i, ponder);
				exec.execute(p);
			}
		}
		
		TimeUnit.SECONDS.sleep(3);
		exec.shutdown();
	}
	
}
