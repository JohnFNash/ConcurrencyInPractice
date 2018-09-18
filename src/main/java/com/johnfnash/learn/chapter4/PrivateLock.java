package com.johnfnash.learn.chapter4;

public class PrivateLock {
	// lock域被声明为final的，这样可以防止不小心改变它的内容，而破坏锁对象
	// 私有锁对象模式只能用在无条件的线程安全类（实例是可变的，但是这个类有足够的内部同步，无需任何外部同步）上，有条件的线程安全类（部分方法安全）不能应用这种模式
	// 私有锁对象模式特别适用于专门为继承而设计的类，不然的话子类和父类很可能相互锁住对方
	private final Object myLock = new Object();
	
	public void foo() {
		synchronized (myLock) {
			
		}
	}
	
}
