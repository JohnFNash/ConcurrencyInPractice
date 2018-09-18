package com.johnfnash.learn.basic.wait_notify;

public class Producer extends Thread {

	//ÿ������������
	private int num ;
	
	//�����Ĳֿ�
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
	
	// ���òֿ�Storage����������
	private void produce(int num) {
		abstractStorage.produce(num);
	}
	
}
