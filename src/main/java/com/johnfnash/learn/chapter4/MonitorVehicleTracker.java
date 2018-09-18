package com.johnfnash.learn.chapter4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;
import com.johnfnash.learn.chapter4.entity.MutabePoint;

/**
 * 通过实例封闭与加锁等机制来确保线程安全
 * 不过，调用 getLocations ，可能复制完成 返回之前，point 发生了改变，这时获取到的就不是最新的
 * 若要尽可能取到最新的，可能得频繁调用
 */
@ThreadSafe
public class MonitorVehicleTracker {

	@GuardedBy("this")
	private final Map<String, MutabePoint> locations;

	public MonitorVehicleTracker(Map<String, MutabePoint> locations) {
		this.locations = deepCopy(locations); // 注意这里是 deepCopy，而不是locations
	}
	
	public synchronized Map<String, MutabePoint> getLocations() {
		return deepCopy(locations);
	}
	
	public synchronized MutabePoint getLocation(String id) {
		MutabePoint loc = locations.get(id);
		return loc == null ? null : new MutabePoint(loc);
	}
	
	public synchronized void setLocation(String id, int x, int y) {
		MutabePoint loc = locations.get(id);
		if(loc == null) {
			throw new IllegalArgumentException("No such ID: " + id);
		}
		loc.x = x;
		loc.y = y;
	}
	
	private static Map<String, MutabePoint> deepCopy(Map<String, MutabePoint> m) {
		Map<String, MutabePoint> result = new HashMap<String, MutabePoint>();
		for(String id : m.keySet()) {
			result.put(id, new MutabePoint(m.get(id))); // 注意：这里是重新new 一个新的对象，放入 result 中
		}
		
		return Collections.unmodifiableMap(result);
	}
	
}
