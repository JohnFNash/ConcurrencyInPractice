package com.johnfnash.learn.mem.visibility.singleton;

import com.johnfnash.learn.annotation.ThreadSafe;

// 线程安全的延迟初始化
@ThreadSafe
public class UnsafeLazyInitialization {

	private static Resource resource;
	
	public synchronized static Resource getInstance() {
		if(resource == null) {
			resource = new Resource(); // 不安全的发布
		}
		return resource;
	}
	
	static class Resource {
		
		public Resource() {
			// .....
		}
		
	}
	
}
