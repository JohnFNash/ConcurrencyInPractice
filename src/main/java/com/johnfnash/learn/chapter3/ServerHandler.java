package com.johnfnash.learn.chapter3;

public class ServerHandler {

	private volatile boolean isopen = true;

	public void run() {
		if(isopen) {
			//促销逻辑
			System.out.println("促销开始......");
		} else {
			//正常逻辑
			System.out.println("正常销售......");
		}
	}
	
	public void setIsOpen(boolean isopen) {
		this.isopen = isopen;
	}
	
	public static void main(String[] args) throws InterruptedException {
		ServerHandler handler = new ServerHandler();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(handler.isopen) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.run();
				}
			}
		});
		Thread threadB = new Thread(new Runnable() {
			@Override
			public void run() {
				while(handler.isopen) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.run();
				}
			}
		});
		
		thread.start();
		threadB.start();
		
		Thread.sleep(2000);
		handler.setIsOpen(false);
		Thread.sleep(2000);
	}
	
}
