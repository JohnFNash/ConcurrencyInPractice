package com.johnfnash.learn.blockqueue.delayqueue;

import java.util.concurrent.DelayQueue;

public class DelayQueueTest {

	public static void main(String[] args) throws InterruptedException {
		// 新建一个等待队列
		final DelayQueue<Student> bq = new DelayQueue<Student>();
		Student stu;
		for (int i = 0; i < 5; i++) {
			stu = new Student("学生" + i, Math.round(Math.random() * 1000 + i));
			bq.put(stu); // 将数据存到队列里！
		}	
		
		//获取但不移除此队列的头部；如果此队列为空，则返回 null
		long start = System.currentTimeMillis();
		System.out.println(bq.take().getName());
		System.out.println(System.currentTimeMillis() - start);
	}

}
