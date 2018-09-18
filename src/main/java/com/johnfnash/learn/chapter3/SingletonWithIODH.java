package com.johnfnash.learn.chapter3;

/**
 *   ����ʽ�����಻��ʵ���ӳټ��أ����ܽ����ò���ʼ��ռ���ڴ棻
 *   ����ʽ�������̰߳�ȫ���Ʒ���������������Ӱ�졣
 *   Initialization Demand Holder (IoDH)�ļ������ܹ������ֵ�����ȱ�㶼�˷����������ߵ��ŵ�϶�Ϊһ
 */
public class SingletonWithIODH {

	private SingletonWithIODH () {
	}

	static class SingletonWithIODHHolder {
		static SingletonWithIODH instance = new SingletonWithIODH();
	}
	
	public static SingletonWithIODH getInstance() {
		return SingletonWithIODHHolder.instance;
	}
	
	public static void main(String[] args) {
		SingletonWithIODH instanceA = SingletonWithIODH.getInstance();
		SingletonWithIODH instanceB = SingletonWithIODH.getInstance();
		System.out.println(instanceA == instanceB);
	}
	
}
