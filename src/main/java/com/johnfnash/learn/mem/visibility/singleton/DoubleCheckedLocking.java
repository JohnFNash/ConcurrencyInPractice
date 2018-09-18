package com.johnfnash.learn.mem.visibility.singleton;

import com.johnfnash.learn.annotation.NotThreadSafe;

// ˫�ؼ����� (DCL)
// �߳̿��ܿ���һ�������ֹ���� Resource
@NotThreadSafe
public class DoubleCheckedLocking {

	private static Resource resource;
	
	public static Resource getInstance() {
		if(resource == null) {
			synchronized (DoubleCheckedLocking.class) {
				if(resource == null) {
					resource = new Resource(); // ����ȫ�ķ���
				}
			}
		}
		return resource;
	}
	
	static class Resource {
		
		public Resource() {
			// .....
		}
		
	}
	
}
