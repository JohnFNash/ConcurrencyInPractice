package com.johnfnash.learn.mem.visibility.singleton;

import com.johnfnash.learn.annotation.ThreadSafe;

// �̰߳�ȫ���ӳٳ�ʼ��
@ThreadSafe
public class UnsafeLazyInitialization {

	private static Resource resource;
	
	public synchronized static Resource getInstance() {
		if(resource == null) {
			resource = new Resource(); // ����ȫ�ķ���
		}
		return resource;
	}
	
	static class Resource {
		
		public Resource() {
			// .....
		}
		
	}
	
}
