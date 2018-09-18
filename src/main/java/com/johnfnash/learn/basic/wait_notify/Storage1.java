package com.johnfnash.learn.basic.wait_notify;

import java.util.LinkedList;

/**
 * �����ߺ������ߵ����� wait��notify/notifyAll() ʵ��
 */
public class Storage1 implements AbstractStorage {

	// �ֿ��������
	private final int MAX_SIZE = 100;

	// �ֿ�洢������
	private LinkedList<Object> list = new LinkedList<Object>();

	@Override
	public void consume(int num) {
		synchronized (list) {
			// ��������������
			while (num > list.size()) {
				System.out.println("��Ҫ���ѵĲ�Ʒ������:" + num + "\t���������:" + list.size() + "\t��ʱ����ִ����������!");
				try {
					list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// �����������㣬��ʼ����
			for (int i = 0; i < num; i++) {
				list.remove();
			}

			System.out.println("���Ѿ����Ѳ�Ʒ����:" + num + "\t���ֲִ���Ϊ��:" + list.size());

			// ֪ͨ�����ֿ߲����������ı�
			list.notifyAll();
		}
	}

	// //������Ʒ
	@Override
	public void produce(int num) {
		synchronized (list) {
			// �ֿ�ʣ������������Դ�ż���Ҫ��������������ͣ����
			while (list.size() + num > MAX_SIZE) {
				System.out.println("��Ҫ�����Ĳ�Ʒ������:" + num + "\t���������:" + list.size() + "\t��ʱ����ִ����������!");
				try {
					// ���������㣬��������
					list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
			
			for (int i = 0; i < num; i++) {
				list.add(new Object());
			}

			System.out.println("���Ѿ�������Ʒ����:" + num + "\t���ֲִ���Ϊ��:" + list.size());

			// ֪ͨ�����ߣ������Ѿ������ı�
			list.notifyAll();
		}
	}

}
