package com.johnfnash.learn.basic.wait_notify;

public class Test {

	public static void main(String[] args) {
		// �ֿ����
		AbstractStorage abstractStorage = new Storage1();

		// �����߶���
		Producer p1 = new Producer(abstractStorage);
		Producer p2 = new Producer(abstractStorage);
		Producer p3 = new Producer(abstractStorage);
		Producer p4 = new Producer(abstractStorage);
		Producer p5 = new Producer(abstractStorage);
		Producer p6 = new Producer(abstractStorage);
		Producer p7 = new Producer(abstractStorage);

		// �����߶���
		Consumer c1 = new Consumer(abstractStorage);
		Consumer c2 = new Consumer(abstractStorage);
		Consumer c3 = new Consumer(abstractStorage);

		// ���������߲�Ʒ��������
		p1.setNum(10);
		p2.setNum(10);
		p3.setNum(10);
		p4.setNum(10);
		p5.setNum(10);
		p6.setNum(10);
		p7.setNum(80);

		// ���������߲�Ʒ��������
		c1.setNum(50);
		c2.setNum(20);
		c3.setNum(30);

		// �߳̿�ʼִ��
		c1.start();
		c2.start();
		c3.start();

		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
		p6.start();
		p7.start();
	}

}
