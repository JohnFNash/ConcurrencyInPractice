package com.johnfnash.learn.blockqueue.workstealing;

public interface Channel<P> {

	public P take() throws InterruptedException;
	
	public void put(P product) throws InterruptedException;
	
}
