package com.johnfnash.learn.blockqueue.delayqueue;

import java.util.concurrent.DelayQueue;

public class DelayQueueTest {

	public static void main(String[] args) throws InterruptedException {
		// �½�һ���ȴ�����
		final DelayQueue<Student> bq = new DelayQueue<Student>();
		Student stu;
		for (int i = 0; i < 5; i++) {
			stu = new Student("ѧ��" + i, Math.round(Math.random() * 1000 + i));
			bq.put(stu); // �����ݴ浽�����
		}	
		
		//��ȡ�����Ƴ��˶��е�ͷ��������˶���Ϊ�գ��򷵻� null
		long start = System.currentTimeMillis();
		System.out.println(bq.take().getName());
		System.out.println(System.currentTimeMillis() - start);
	}

}
