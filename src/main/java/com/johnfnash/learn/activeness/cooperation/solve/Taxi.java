package com.johnfnash.learn.activeness.cooperation.solve;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

// 通过公开调用来避免在相互协作的对象之间产生死锁
@ThreadSafe
public class Taxi {
	@GuardedBy("this")
	private Point location, destination;
	
	private final Dispatcher dispatcher;

	public Taxi(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public synchronized Point getLocation() {
		return location;
	}
	
	public void setLocation(Point location) {
		boolean reachedDestination;
		synchronized (this) {
			this.location = location;
			System.out.println("location: [" + location.getX() + "," + location.getY() + "]");
			reachedDestination = location.equals(destination);
		}
		if(reachedDestination) {
			System.out.println("desitination");
			dispatcher.notifyAvailable(this);
		}
	}

	public synchronized Point getDestination() {
		return destination;
	}

	public synchronized void setDestination(Point destination) {
		this.destination = destination;
	}
	
}
