package com.johnfnash.learn.blockqueue.workstealing;

import java.util.concurrent.BlockingDeque;

public interface WorkStealingEnableChannel<P> extends Channel<P> {

	P take(BlockingDeque<P> preferredQueue) throws InterruptedException;
	
}
