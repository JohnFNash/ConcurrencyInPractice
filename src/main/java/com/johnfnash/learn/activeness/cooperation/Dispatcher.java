package com.johnfnash.learn.activeness.cooperation;

import java.util.HashSet;
import java.util.Set;

import com.johnfnash.learn.annotation.GuardedBy;

public class Dispatcher {
	@GuardedBy("this")
	private final Set<Taxi> taxis;
	
	@GuardedBy("this")
	private final Set<Taxi> availableTaxis;
	
	public Dispatcher() {
		taxis = new HashSet<Taxi>();
		availableTaxis = new HashSet<Taxi>();
	}
	
	public synchronized void addTaxi(Taxi taxi) {
		this.taxis.add(taxi);
	}
	
	public synchronized void notifyAvailable(Taxi taxi) {
		System.out.println("notify");
		availableTaxis.add(taxi);
	}
	
	public synchronized int getAvailables() {
		return availableTaxis.size();
	}
	
	public synchronized void printLocations() {
		for(Taxi t : taxis) {
			System.out.println("to print location...");
			System.out.println(t.getLocation());
		}
	}
	
}
