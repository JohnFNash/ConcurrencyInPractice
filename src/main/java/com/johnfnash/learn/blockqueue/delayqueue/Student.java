package com.johnfnash.learn.blockqueue.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

// 必须实现Delayed接口
public class Student implements Delayed {

	private String name;
	private long submitTime; // 交卷时间(开始考试时间默认为实例初始化时间)
	private long workTime;	 // 考试时长
	
	public Student(String name, long workTime) {
		this.name = name;
		this.workTime = workTime;
		this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.MILLISECONDS) + System.nanoTime();
		
		System.out.println(this.name + " 交卷，用时" + workTime);
	}

	public String getName() {
        return this.name + " 交卷，用时" + workTime;
    }
	
	@Override
	public int compareTo(Delayed o) {
		Student that = (Student) o;
		return submitTime > that.submitTime ? 1 : (submitTime < that.submitTime ? -1 : 0);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		//返回一个延迟时间
		return unit.convert(submitTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

}
