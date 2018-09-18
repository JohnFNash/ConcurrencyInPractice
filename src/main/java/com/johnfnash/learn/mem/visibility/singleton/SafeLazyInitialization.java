package com.johnfnash.learn.mem.visibility.singleton;

import com.johnfnash.learn.annotation.NotThreadSafe;

// 不安全的延迟初始化
@NotThreadSafe
public class SafeLazyInitialization {

	private static Resource resource;
	
	public static Resource getInstance() {
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
