package com.johnfnash.learn.future;

public class CommonCook {

	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		// ��һ�� ��������
        OnlineShopping thread = new OnlineShopping();
        thread.start();
        thread.join();  // ��֤�����͵�
        
        // �ڶ��� ȥ���й���ʳ��
        Thread.sleep(2000);  // ģ�⹺��ʳ��ʱ��
        Shicai shicai = new Shicai();
        System.out.println("�ڶ�����ʳ�ĵ�λ");
        // ������ �ó������ʳ��
        System.out.println("����������ʼչ�ֳ���");
        cook(thread.chuju, shicai);
        
        System.out.println("�ܹ���ʱ" + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	
	static class OnlineShopping extends Thread {
		
		private Chuju chuju;
		
		@Override
		public void run() {
			System.out.println("��һ�����µ�");
            System.out.println("��һ�����ȴ��ͻ�");
            try {
                Thread.sleep(5000);  // ģ���ͻ�ʱ��
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("��һ��������͵�");
			chuju = new Chuju();
		}
		
	}
	
	//  �ó������ʳ��
    static void cook(Chuju chuju, Shicai shicai) {}
	
	// ������
    static class Chuju {}
    
    // ʳ����
    static class Shicai {}
	
}
