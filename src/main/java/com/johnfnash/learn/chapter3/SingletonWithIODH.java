package com.johnfnash.learn.chapter3;

/**
 *   饿汉式单例类不能实现延迟加载，不管将来用不用始终占据内存；
 *   懒汉式单例类线程安全控制烦琐，而且性能受影响。
 *   Initialization Demand Holder (IoDH)的技术，能够将两种单例的缺点都克服，而将两者的优点合二为一
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
