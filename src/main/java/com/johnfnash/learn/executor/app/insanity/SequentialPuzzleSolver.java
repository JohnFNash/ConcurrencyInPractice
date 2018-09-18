package com.johnfnash.learn.executor.app.insanity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 执行一个深度优先搜索，当找到解答方案后结束搜索
public class SequentialPuzzleSolver<P, M> {
	private final Puzzle<P, M> puzzle;
	// 用于存放已经遍历的位置，防止无限搜索
	private final Set<P> seen = new HashSet<P>();
	
	public SequentialPuzzleSolver(Puzzle<P, M> puzzle) {
		this.puzzle = puzzle;
	}
	
	public List<M> solve() {
        P pos = puzzle.initialPosition();
        return search(new PuzzleNode<P, M>(pos, null, null));
    }
	
	private List<M> search(PuzzleNode<P, M> node) {
		if(!seen.contains(node.pos)) {
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			seen.add(node.pos);
			if(puzzle.isGoal(node.pos)) {
				System.out.println("find: " + node.pos);
				return node.asMoveList();
			}
			
			for (M move : puzzle.legalMoves(node.pos)) {
				P pos = puzzle.move(node.pos, move);
				PuzzleNode<P, M> child = new PuzzleNode<P, M>(pos, move, node);
				List<M> result = search(child);
				if(result != null) {
					return result;
				}
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		SequentialPuzzleSolver<Point, Move> solver = new SequentialPuzzleSolver<Point, Move>(new PointPuzzle(50));
		List<Move> moves = solver.solve();
		System.out.println(System.currentTimeMillis() -  startTime);
		System.out.println("result: --------------");
		for (Move move : moves) {
			System.out.println(move);
		}
	}
}
