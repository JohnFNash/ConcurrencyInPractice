package com.johnfnash.learn.synctool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
* 两个线程间的数据交换
*/
public class ExchangerDemo {

	private static final Exchanger<List<String>> ex = new Exchanger<List<String>>();
	
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
    * 内部类，数据生成者
    */
    class DataProducer implements Runnable {
    	private List<String> list = new ArrayList<String>();
    	
		@Override
		public void run() {
			System.out.println("生产者开始生产数据");
			for (int i = 1; i <= 5; i++) {
                System.out.println("生产了第" + i + "个数据，耗时1秒");
                list.add("生产者" + i);
                sleep(1000);
            }
			
			System.out.println("生产数据结束");
            System.out.println("开始与消费者交换数据");
            
            try {
            	//将数据准备用于交换，并返回消费者的数据
				list = ex.exchange(list);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
            System.out.println("结束与消费者交换数据");
           
            System.out.println("\n遍历生产者交换后的数据");
            for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
                System.out.println(iterator.next());
            }
            
		}

    }
    
    
    /**
     * 内部类，数据消费者
     */
	class DataConsumer implements Runnable {
		private List<String> list = new ArrayList<String>();
		
		@Override
		public void run() {
			System.out.println("消费者开始消费数据");
            for (int i = 1; i <= 5; i++) {
                System.out.println("消费了第" + i + "个数据");
                // 消费者产生数据，后面交换的时候给生产者
                list.add("消费者" + i);
             }
            
            System.out.println("消费数据结束");
            System.out.println("开始与生产者交换数据");
            
            try {
                // 进行数据交换，返回生产者的数据
                list = (List<String>) ex.exchange(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                       
            sleep(1000);
            System.out.println("\n开始遍历消费者交换后的数据");
            for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
                System.out.println(iterator.next());
            }
		}
	 
	}
    
	
	public static void main(String[] args) {
		 ExchangerDemo et = new ExchangerDemo();
		 new Thread(et.new DataProducer()).start();
		 new Thread(et.new DataConsumer()).start();
	}
	
}
