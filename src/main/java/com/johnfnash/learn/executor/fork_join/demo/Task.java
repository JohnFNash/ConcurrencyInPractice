
package com.johnfnash.learn.executor.fork_join.demo;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class Task extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	
	private final int THRESHOLD = 20;

	private List<Product> products;
	private int first;
	private int last;
	private double increment;
	
	public Task(List<Product> products, int first, int last, double increment) {
		this.products = products;
		this.first = first;
		this.last = last;
		this.increment = increment;
	}

	@Override
	protected void compute() {
		if(last - first < THRESHOLD) {
			updatePrices();
		} else {
			int middle = (first + last)/2;
			System.out.printf("Task: Pending tasks:%s\n", getQueuedTaskCount());
			Task t1 = new Task(products, first, middle + 1, increment);
			Task t2 = new Task(products, middle + 1, last, increment);
			invokeAll(t1, t2);
		}
	}

	// 更新产品队列中位于first值和last值之间的产品的价格
	private void updatePrices() {
		Product product;
		for (int i=first; i<last; i++){
			product = products.get(i);
			product.setPrice(product.getPrice() * (1 + increment));
		}
	}

}
