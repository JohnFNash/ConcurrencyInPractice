package com.johnfnash.learn.chapter4;

public class PrivateLock {
	// lock������Ϊfinal�ģ��������Է�ֹ��С�ĸı��������ݣ����ƻ�������
	// ˽��������ģʽֻ���������������̰߳�ȫ�ࣨʵ���ǿɱ�ģ�������������㹻���ڲ�ͬ���������κ��ⲿͬ�����ϣ����������̰߳�ȫ�ࣨ���ַ�����ȫ������Ӧ������ģʽ
	// ˽��������ģʽ�ر�������ר��Ϊ�̳ж���Ƶ��࣬��Ȼ�Ļ�����͸���ܿ����໥��ס�Է�
	private final Object myLock = new Object();
	
	public void foo() {
		synchronized (myLock) {
			
		}
	}
	
}
