package com.johnfnash.learn.cancel.newTaskFor;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import com.johnfnash.learn.annotation.GuardedBy;

public class SocketUsingTask<T> implements CancellableTask<T> {
	@GuardedBy("this")
	private Socket socket;
	
	public synchronized void setSocket(Socket s) {
		this.socket = s;
	}
	
	@Override
	public T call() throws Exception {
		// ......
		return null;
	}

	@Override
	public synchronized void cancel() {
		try {
			if(socket != null) {
				socket.close();
			}
		} catch (IOException ignored) {
		}
	}

	@Override
	public RunnableFuture<T> newTask() {
		return new FutureTask<T>(this) {
			@SuppressWarnings("finally")
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				try {
					SocketUsingTask.this.cancel();
				} finally {
					return super.cancel(mayInterruptIfRunning);
				}				
			}
		};
	}

}
