package com.johnfnash.learn.executor.app.insanity;

import java.util.concurrent.atomic.AtomicInteger;

// Solver that recognizes when no solution exists
public class PuzzleSolver <P,M> extends ConcurrentPuzzleSolver<P, M> {

	public PuzzleSolver(Puzzle<P, M> puzzle) {
		super(puzzle);
	}
	
	private final AtomicInteger taskCount = new AtomicInteger(0);
	
	public Runnable newTask(P p, M m, PuzzleNode<P, M> n) {
		return new CountingSolverTask(p, m, n);
	} 
	
	class CountingSolverTask extends SolverTask {

		public CountingSolverTask(P pos, M move, PuzzleNode<P, M> prev) {
			super(pos, move, prev);
			taskCount.incrementAndGet();
		}
		
		@Override
		public void run() {
			try {
				super.run();
			} finally {
				if(taskCount.decrementAndGet() == 0) {
					solution.setValue(null);
				}
			}
		}
		
	}

}
