package com.johnfnash.learn.chapter5.copyonwrite;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CopyOnWriteMap<K,V> extends AbstractMap<K, V> implements Cloneable {

	private volatile Map<K, V> internalMap;
	
	public CopyOnWriteMap() {
        internalMap = new HashMap<K, V>();
    }
	
	public CopyOnWriteMap(int initialSize) {
        internalMap = new HashMap<K, V>(initialSize);
    }
	
	@Override
	public V get(Object key) {
		return internalMap.get(key);
	}

	@Override
	public V put(K key, V value) {
		synchronized (this) {
			Map<K, V> newMap = new HashMap<K, V>(internalMap);
			V val = newMap.put(key, value);
			internalMap = newMap;
			
			return val;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		synchronized (this) {
			Map<K, V> newMap = new HashMap<K, V>(internalMap);
			newMap.putAll(m);
			internalMap = newMap;
		}
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return internalMap.entrySet();
	}
	
}
