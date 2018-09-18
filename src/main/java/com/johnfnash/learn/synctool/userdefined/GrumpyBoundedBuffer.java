package com.johnfnash.learn.synctool.userdefined;

// 将前提条件的失败传递给调用者
// 尽管这种方式实现起来很简单，但使用起来并非如此
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

	protected GrumpyBoundedBuffer(int capacity) {
		super(capacity);
	}
	
	public synchronized void put(V v) throws BufferFullException {
		if(isFull()) {
			throw new BufferFullException();
		}
		doPut(v);
	}
	
	public synchronized V take() throws BufferEmptyException {
		if(isEmpty()) {
			throw new BufferEmptyException();
		}
		return doTake();
	}

	public static void main(String[] args) throws InterruptedException {
		final int SLEEP_GRANULABITY = 100;
		GrumpyBoundedBuffer<Integer> buffer = new GrumpyBoundedBuffer<>(10);
		while(true) {
			try {
				Integer item = buffer.take();
				System.out.println(item);
			} catch (BufferEmptyException e) {
				// 休眠或者自旋等待（直接重新调用 take 方法）
				Thread.sleep(SLEEP_GRANULABITY);
			}
		}
		
		// put 的与之类型
	}
	
}

class BufferFullException extends Exception {

	private static final long serialVersionUID = 6789214121306165422L;

}

class BufferEmptyException extends Exception {

	private static final long serialVersionUID = 8159436949749707619L;
	
}