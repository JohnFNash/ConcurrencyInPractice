package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 让每名哲学家先去申请他左手边的筷子 ,然后再申请他右手边的筷子 ,
// 如果右手边的筷子不空闲, 则比较当前哲学家 i 和他右手边的哲学家( i +1)%5 ,看谁取得各自左手边筷子的时间更晚, 令其回退
// ( 即释放左手筷子, 再重新申请左手筷子), 直到此哲学家右手边的筷子空闲为止。
// 通过设置回退机制可以确保每位哲学家都能顺利进餐。
public class DiningPhilosophersSolver3 {

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
		
		ChopstickForRollback[] stick = new ChopstickForRollback[size];
		for(int i = 0; i < size; i++) {
			stick[i] = new ChopstickForRollback(i);
		}
		for(int i = 0; i < size; i++) {
			stick[i].setNext(stick[(i+1)%size]);
		}
		
		for(int i = 0; i < size; i++) {
			PhilosopherForRollback p = new PhilosopherForRollback(stick[i], stick[(i+1)%size], i, ponder);
			exec.execute(p);
		}
		
		System.out.println("-----------------------------------");
		
		TimeUnit.SECONDS.sleep(3);
		exec.shutdown();
	}
	
}
