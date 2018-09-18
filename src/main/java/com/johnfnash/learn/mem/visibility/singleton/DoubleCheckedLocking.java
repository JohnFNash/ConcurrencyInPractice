package com.johnfnash.learn.mem.visibility.singleton;

import com.johnfnash.learn.annotation.NotThreadSafe;

// 双重检查加锁 (DCL)
// 线程可能看到一个被部分构造的 Resource
@NotThreadSafe
public class DoubleCheckedLocking {

	private static Resource resource;
	
	public static Resource getInstance() {
		if(resource == null) {
			synchronized (DoubleCheckedLocking.class) {
				if(resource == null) {
					resource = new Resource(); // 不安全的发布
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
