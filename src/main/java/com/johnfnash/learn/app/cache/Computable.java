package com.johnfnash.learn.app.cache;

public interface Computable<A,V> {

	V compute(A arg) throws InterruptedException;
	
}
