package com.johnfnash.learn.cancel.thread;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LogWriter {

	private final BlockingQueue<String> queue;
	private final LoggerThread logger;
    private static final int CAPACITY = 1000;
	
	public LogWriter(Writer writer) {
		this.queue = new LinkedBlockingDeque<String>(CAPACITY);
		this.logger = new LoggerThread(writer);
	}
	
	public void start() {
		logger.start();
	}
	
	public void log(String msg) throws InterruptedException {
		queue.put(msg);
	}
	
	private class LoggerThread extends Thread {
		
		private final PrintWriter writer;

        public LoggerThread(Writer writer) {
            this.writer = new PrintWriter(writer, true); // autoflush
        }

		@Override
		public void run() {
			try {
				//如果只是使日志线程退出，那么并不是一种完备的关闭机制。这种直接关闭的做法会
				//丢失那些正在等待被写入到日志的消息。
				while(!isInterrupted()) {
					writer.println(queue.take());
				}
			} catch (InterruptedException ignored) {
			} finally {
				writer.close();
			}
		}
		
	}
	
}
