package com.johnfnash.learn.scalability;

import java.util.HashSet;
import java.util.Set;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

// 多个相互独立的状态变量使用同一个锁
@ThreadSafe
public class ServerStatusBeforeSplit {
	@GuardedBy("this")
	public final Set<String> users;
	
	@GuardedBy("this")
	public final Set<String> queries;
	
	public ServerStatusBeforeSplit() {
		users = new HashSet<String>();
		queries = new HashSet<String>();
	}
	
	public synchronized void addUser(String u) {
        users.add(u);
    }
	
	public synchronized void addQuery(String q) {
        queries.add(q);
    }

    public synchronized void removeUser(String u) {
        users.remove(u);
    }

    public synchronized void removeQuery(String q) {
        queries.remove(q);
    }
	
}
