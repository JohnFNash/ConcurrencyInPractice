package com.johnfnash.learn.basic.wait_notify;

public class Consumer extends Thread {

	// ÿ�����ѵĲ�Ʒ����
	private int num;

	// ���ڷ��õĲֿ�
	private AbstractStorage abstractStorage1;

	// ���캯�������òֿ�
	public Consumer(AbstractStorage abstractStorage1) {
		this.abstractStorage1 = abstractStorage1;
	}

	// �߳�run����
	@Override
	public void run() {
		consume(num);
	}

	// ���òֿ�Storage����������
	private void consume(int num) {
		abstractStorage1.consume(num);
	}

	public void setNum(int num) {
		this.num = num;
	}

}
