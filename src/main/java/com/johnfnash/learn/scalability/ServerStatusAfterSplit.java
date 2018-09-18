package com.johnfnash.learn.scalability;

import java.util.HashSet;
import java.util.Set;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

// 多个相互独立的状态变量分别使用自己的锁，提高伸缩性并降低每个锁的被访问频率
@ThreadSafe
public class ServerStatusAfterSplit {
	@GuardedBy("this")
	public final Set<String> users;
	
	@GuardedBy("this")
	public final Set<String> queries;
	
	public ServerStatusAfterSplit() {
		users = new HashSet<String>();
		queries = new HashSet<String>();
	}
	
	public void addUser(String u) {
		synchronized (users) {
			users.add(u);
		}
    }
	
	public synchronized void addQuery(String q) {
        synchronized (queries) {
        	queries.add(q);
		}
    }

    public synchronized void removeUser(String u) {
        synchronized (users) {
        	users.remove(u);
		}
    }

    public synchronized void removeQuery(String q) {
        synchronized (queries) {
        	queries.remove(q);
		}
    }
	
}
