package com.johnfnash.learn.synctool;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWorker implements Runnable {

	private int id;
    private CyclicBarrier cyclicBarrier;
	
    public CyclicBarrierWorker(int id, CyclicBarrier cyclicBarrier) {
        this.id = id;
        this.cyclicBarrier = cyclicBarrier;
    }
    
	@Override
	public void run() {
		try {
			System.out.println(id + "th people wait, waiting " + cyclicBarrier.getNumberWaiting());
			int returnIndex = cyclicBarrier.await(); // 大家等待最后一个线程到达
			System.out.println(id + " th people go, returnIndex:" + returnIndex);
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		final int NUM = 10;
		CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM,new Runnable() {
			@Override
			public void run() {
				System.out.println("go on together!");
			}
		});
		
		for (int i=1; i<=NUM; i++) {
			new Thread(new CyclicBarrierWorker(i, cyclicBarrier)).start();
		}
	}

}
