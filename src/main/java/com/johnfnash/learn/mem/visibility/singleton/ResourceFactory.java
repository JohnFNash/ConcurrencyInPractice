package com.johnfnash.learn.mem.visibility.singleton;

import com.johnfnash.learn.annotation.ThreadSafe;

// 延迟初始化占位类模式
// JVM 将推迟 ResourceHolder 的初始化操作，直到开始使用这个类时才初始化
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
