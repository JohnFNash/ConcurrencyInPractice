package com.johnfnash.learn.mem.visibility.singleton;

import com.johnfnash.learn.annotation.NotThreadSafe;

// ����ȫ���ӳٳ�ʼ��
@NotThreadSafe
public class SafeLazyInitialization {

	private static Resource resource;
	
	public static Resource getInstance() {
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
