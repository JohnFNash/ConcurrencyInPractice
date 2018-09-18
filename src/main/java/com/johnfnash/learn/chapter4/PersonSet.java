package com.johnfnash.learn.chapter4;

import java.util.HashSet;
import java.util.Set;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

/**
 * PersonSet
 * <p/>
 * Using confinement to ensure thread safety
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class PersonSet {
	@GuardedBy("this")
	private final Set<Person> mySet = new HashSet<Person>();
	
	public synchronized void addPerson(Person person) {
		mySet.add(person);
	}
	
	public synchronized boolean containsPerson(Person p) {
		return mySet.contains(p);
	}
	
	interface Person {
	}
	
}
