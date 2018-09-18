package com.johnfnash.learn.cancel.thread;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.johnfnash.learn.annotation.GuardedBy;

public class LogService {

	private final BlockingQueue<String> queue;
	private final LoggerThread loggerThread;
	private final PrintWriter writer;
	@GuardedBy("this")
	private boolean isShutdown;
	@GuardedBy("this")
	private int reservation = 0;
	private static final int CAPACITY = 1000;
	
	public LogService(Writer writer) {
		this.queue = new LinkedBlockingDeque<String>(CAPACITY);
		this.loggerThread = new LoggerThread();
		this.writer = new PrintWriter(writer, true);
	}
	
	public void start() {
		loggerThread.start();
	}
	
	public void stop() {
		synchronized (this) {
			isShutdown = true;
		}
		loggerThread.interrupt();
	}
	
	public void log(String msg) throws InterruptedException {
		synchronized (this) {
			if(isShutdown) {
				throw new IllegalStateException("thread has been shutdown");
			}
			++reservation;
		}
		queue.put(msg);
	}
	
	private class LoggerThread extends Thread {
		@Override
		public void run() {
			try {
				while(true) {
					try {
						synchronized (LogService.this) {
							if(isShutdown && reservation == 0) {
								break;
							}
						}
						String msg = queue.take();
						synchronized (LogService.this) {
							--reservation;
						}
						writer.println(msg);
					} catch (InterruptedException e) {
						/* retry */
					}
				}
			} finally {
				writer.close();
			}
		}
	}
	
}
