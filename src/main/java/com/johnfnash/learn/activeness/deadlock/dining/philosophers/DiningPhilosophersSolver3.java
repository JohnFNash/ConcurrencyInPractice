package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// ��ÿ����ѧ����ȥ���������ֱߵĿ��� ,Ȼ�������������ֱߵĿ��� ,
// ������ֱߵĿ��Ӳ�����, ��Ƚϵ�ǰ��ѧ�� i �������ֱߵ���ѧ��( i +1)%5 ,��˭ȡ�ø������ֱ߿��ӵ�ʱ�����, �������
// ( ���ͷ����ֿ���, �������������ֿ���), ֱ������ѧ�����ֱߵĿ��ӿ���Ϊֹ��
// ͨ�����û��˻��ƿ���ȷ��ÿλ��ѧ�Ҷ���˳�����͡�
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
