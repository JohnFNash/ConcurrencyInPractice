package com.johnfnash.learn.basic.reentrantReadWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

// ����������ִ������������������
public class CachedData {

	Object data;
	volatile boolean cacheValid; //�����Ƿ���Ч
	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	
	void processCachedData() {
		rwl.readLock().lock();   // ��ȡ����
		
		// ���������Ч������ cache������ֱ��ʹ�� data
		if(!cacheValid) {
			// Must release read lock before acquiring write lock
			// ��ȡд��ǰ�����ͷŶ���
			rwl.readLock().unlock();
			rwl.writeLock().lock();
			
			// Recheck state because another thread might have acquired
			//   write lock and changed state before we did.
			if(!cacheValid) {
				//data = .;
				cacheValid = true;
			}
			
			// Downgrade by acquiring read lock before releasing write lock
			//�����������ͷ�д��ǰ��ȡ����
			rwl.readLock().lock();
			rwl.writeLock().unlock();
		}
		
		use(data);
		rwl.readLock().unlock(); 	// �ͷŶ���
	}
	
	void use(Object data) {
	}
	
}
