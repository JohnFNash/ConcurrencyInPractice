package com.johnfnash.learn.blockqueue.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

// ����ʵ��Delayed�ӿ�
public class Student implements Delayed {

	private String name;
	private long submitTime; // ����ʱ��(��ʼ����ʱ��Ĭ��Ϊʵ����ʼ��ʱ��)
	private long workTime;	 // ����ʱ��
	
	public Student(String name, long workTime) {
		this.name = name;
		this.workTime = workTime;
		this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.MILLISECONDS) + System.nanoTime();
		
		System.out.println(this.name + " ������ʱ" + workTime);
	}

	public String getName() {
        return this.name + " ������ʱ" + workTime;
    }
	
	@Override
	public int compareTo(Delayed o) {
		Student that = (Student) o;
		return submitTime > that.submitTime ? 1 : (submitTime < that.submitTime ? -1 : 0);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		//����һ���ӳ�ʱ��
		return unit.convert(submitTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

}
