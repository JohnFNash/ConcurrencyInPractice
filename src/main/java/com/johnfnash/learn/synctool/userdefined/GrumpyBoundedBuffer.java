package com.johnfnash.learn.synctool.userdefined;

// ��ǰ��������ʧ�ܴ��ݸ�������
// �������ַ�ʽʵ�������ܼ򵥣���ʹ�������������
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
				// ���߻��������ȴ���ֱ�����µ��� take ������
				Thread.sleep(SLEEP_GRANULABITY);
			}
		}
		
		// put ����֮����
	}
	
}

class BufferFullException extends Exception {

	private static final long serialVersionUID = 6789214121306165422L;

}

class BufferEmptyException extends Exception {

	private static final long serialVersionUID = 8159436949749707619L;
	
}