package com.johnfnash.learn.activeness.deadlock.dining.philosophers;

//������
public class Chopstick {

	private boolean taken = false; // �ж��Ǵ˿����Ƿ�����
	
	public synchronized void take() throws InterruptedException {
		while(taken) {
			// ����ѱ�������ȴ�
			wait();
		}
		
		// ���û�б���������Ա����𣬲����� taken Ϊ true
		taken = true;
	}
	
	public synchronized void drop() {
		// ���¿���֮������takenΪfalse����֪ͨ��������
		taken = false;
		notifyAll();
	}
	
}
