package com.johnfnash.learn.mem.visibility.singleton;

import com.johnfnash.learn.annotation.ThreadSafe;

// �ӳٳ�ʼ��ռλ��ģʽ
// JVM ���Ƴ� ResourceHolder �ĳ�ʼ��������ֱ����ʼʹ�������ʱ�ų�ʼ��
@ThreadSafe
public class ResourceFactory {

	private static class ResourceHolder {
		public static Resource resource = new Resource();
	}
	
	public static Resource getInstance() {
		return ResourceHolder.resource;
	}
	
	static class Resource {
		
		public Resource() {
			// .....
		}
		
	}
	
}
