package com.johnfnash.learn.basic.wait_notify;

public class Producer extends Thread {

	//每次生产的数量
	private int num ;
	
	//所属的仓库
	private AbstractStorage abstractStorage;

	public Producer(AbstractStorage abstractStorage) {
		super();
		this.abstractStorage = abstractStorage;
	}
	
	public void setNum(int num){
		this.num = num;
	}

	@Override
	public void run() {
		produce(num);
	}
	
	// 调用仓库Storage的生产函数
	private void produce(int num) {
		abstractStorage.produce(num);
	}
	
}
