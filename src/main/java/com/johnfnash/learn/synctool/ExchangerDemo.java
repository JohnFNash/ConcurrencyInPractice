package com.johnfnash.learn.synctool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
* �����̼߳�����ݽ���
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
    * �ڲ��࣬����������
    */
    class DataProducer implements Runnable {
    	private List<String> list = new ArrayList<String>();
    	
		@Override
		public void run() {
			System.out.println("�����߿�ʼ��������");
			for (int i = 1; i <= 5; i++) {
                System.out.println("�����˵�" + i + "�����ݣ���ʱ1��");
                list.add("������" + i);
                sleep(1000);
            }
			
			System.out.println("�������ݽ���");
            System.out.println("��ʼ�������߽�������");
            
            try {
            	//������׼�����ڽ����������������ߵ�����
				list = ex.exchange(list);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
            System.out.println("�����������߽�������");
           
            System.out.println("\n���������߽����������");
            for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
                System.out.println(iterator.next());
            }
            
		}

    }
    
    
    /**
     * �ڲ��࣬����������
     */
	class DataConsumer implements Runnable {
		private List<String> list = new ArrayList<String>();
		
		@Override
		public void run() {
			System.out.println("�����߿�ʼ��������");
            for (int i = 1; i <= 5; i++) {
                System.out.println("�����˵�" + i + "������");
                // �����߲������ݣ����潻����ʱ���������
                list.add("������" + i);
             }
            
            System.out.println("�������ݽ���");
            System.out.println("��ʼ�������߽�������");
            
            try {
                // �������ݽ��������������ߵ�����
                list = (List<String>) ex.exchange(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                       
            sleep(1000);
            System.out.println("\n��ʼ���������߽����������");
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
