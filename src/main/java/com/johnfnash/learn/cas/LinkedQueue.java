package com.johnfnash.learn.cas;

import java.util.concurrent.atomic.AtomicReference;

import com.johnfnash.learn.annotation.ThreadSafe;

@ThreadSafe
public class LinkedQueue <E> {

	private static class Node <E> {
		@SuppressWarnings("unused")
		final E item;
		final AtomicReference<Node<E>> next;
		
		public Node(E item, AtomicReference<Node<E>> next) {
			this.item = item;
			this.next = next;
		}
	}
	
	@SuppressWarnings("unused")
	private final Node<E> dummy = new Node<E>(null, null);
	@SuppressWarnings("unused")
	private final AtomicReference<Node<E>> head = 
			new AtomicReference<Node<E>>();
	private final AtomicReference<Node<E>> tail = 
			new AtomicReference<Node<E>>();
	
	public boolean put(E item) {
		Node<E> newNode = new Node<E>(item, null);
		while(true) {
			Node<E> curTail = tail.get();
			Node<E> tailNext = curTail.next.get();
			if(curTail == tail.get()) {
				if(tailNext != null) {
					// 队列处于中间状态，推进尾节点
					tail.compareAndSet(curTail, tailNext);
				} else {
					// 处于稳定状态，尝试插入新节点
					if(curTail.next.compareAndSet(null, newNode)) {
						// 插入操作成功，尝试插入新节点
						tail.compareAndSet(curTail, newNode);
						return true;
					}
				}
			}
		}
	}
	
}
