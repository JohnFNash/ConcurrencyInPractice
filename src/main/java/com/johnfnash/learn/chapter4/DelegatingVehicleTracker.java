package com.johnfnash.learn.chapter4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.johnfnash.learn.annotation.ThreadSafe;
import com.johnfnash.learn.chapter4.entity.Point;

/**
 * 通过不可变类以及将线程安全性委托给线程安全容器来实现线程安全
 */
@ThreadSafe
public class DelegatingVehicleTracker {

	private final ConcurrentMap<String, Point> locations;
	private final Map<String, Point> unmodifiableMap;
	
	public DelegatingVehicleTracker(Map<String, Point> points) {
		this.locations = new ConcurrentHashMap<String, Point>(points);
		// 如果为 this.unmodifiableMap = Collections.unmodifiableMap(points);
		// 则当 points 改变时，unmodifiableMap 也会变
		this.unmodifiableMap = Collections.unmodifiableMap(locations);
	}
	
	// 返回locations的静态拷贝而非实时拷贝
	public Map<String, Point> getLocationsAsStatic() {
		// 这里使用的 不是  Collections.unmodifiableMap(locations）， 得到的 unmodifableMap
		// 执行新 new 的 HashMap，而不是 locations, locations 发生变化时不会受影响
		return Collections.unmodifiableMap(new HashMap<String, Point>(locations));
	}
	
	public Point getLocation(String id) {
		return locations.get(id);
	}
	
	public void setLocation(String id, int x, int y) {
		if(locations.replace(id, new Point(x, y)) == null) {
			throw new IllegalArgumentException("invalid vehicle name: " + id);
		}
	}
	
	// 返回locations的实时拷贝
    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }
	
    public static void main(String[] args) {
		Map<String, Point> locations = new HashMap<String, Point>();
		locations.put("1", new Point(1, 2));
		locations.put("2", new Point(2, 3));
		locations.put("3", new Point(3, 4));
		
		DelegatingVehicleTracker tracker = new DelegatingVehicleTracker(locations);
		System.out.println("before update....");
		System.out.println("------------ copy -----------");
		Map<String, Point> locations2 = tracker.getLocations();
		for (String id : locations2.keySet()) {
			System.out.println(locations2.get(id));
		}
		// 不支持的操作
		try {
			locations2.put("1", new Point(11, 22));
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
		
		System.out.println("before update");
		Map<String, Point> locations3 = tracker.getLocationsAsStatic();
		System.out.println("------------ static copy -----------");
		for (String id : locations3.keySet()) {
			System.out.println(locations3.get(id));
		}
		
		System.out.println("change 1");
		tracker.setLocation("1", 11, 22);
		System.out.println(tracker.getLocation("1"));
		
		System.out.println("after update");
		System.out.println("------------ result of static copy after update -----------");
		for (String id : locations3.keySet()) {
			System.out.println(locations3.get(id));
		}
		
		System.out.println("------------ copy of copy after update -----------");
		for (String id : locations2.keySet()) {
			System.out.println(locations2.get(id));
		}
		
	}
    
}
