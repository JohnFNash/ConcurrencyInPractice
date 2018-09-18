package com.johnfnash.learn.scalability;

import java.util.HashSet;
import java.util.Set;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

// ����໥������״̬�����ֱ�ʹ���Լ���������������Բ�����ÿ�����ı�����Ƶ��
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
