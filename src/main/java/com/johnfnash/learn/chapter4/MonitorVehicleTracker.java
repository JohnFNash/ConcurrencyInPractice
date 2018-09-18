package com.johnfnash.learn.chapter4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;
import com.johnfnash.learn.chapter4.entity.MutabePoint;

/**
 * ͨ��ʵ�����������Ȼ�����ȷ���̰߳�ȫ
 * ���������� getLocations �����ܸ������ ����֮ǰ��point �����˸ı䣬��ʱ��ȡ���ľͲ������µ�
 * ��Ҫ������ȡ�����µģ����ܵ�Ƶ������
 */
@ThreadSafe
public class MonitorVehicleTracker {

	@GuardedBy("this")
	private final Map<String, MutabePoint> locations;

	public MonitorVehicleTracker(Map<String, MutabePoint> locations) {
		this.locations = deepCopy(locations); // ע�������� deepCopy��������locations
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
			result.put(id, new MutabePoint(m.get(id))); // ע�⣺����������new һ���µĶ��󣬷��� result ��
		}
		
		return Collections.unmodifiableMap(result);
	}
	
}
