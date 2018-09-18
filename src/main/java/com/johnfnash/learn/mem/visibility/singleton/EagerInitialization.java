package com.johnfnash.learn.mem.visibility.singleton;

import com.johnfnash.learn.annotation.ThreadSafe;

// ��ǰ��ʼ��
@ThreadSafe
public class EagerInitialization {

	private static Resource resource = new Resource();
	
	public static Resource getInstance() {
		return resource;
	}
	
	static class Resource {
		
		public Resource() {
			// .....
		}
		
	}
	
}
