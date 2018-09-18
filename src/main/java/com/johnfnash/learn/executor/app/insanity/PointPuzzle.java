package com.johnfnash.learn.executor.app.insanity;

import java.util.HashSet;
import java.util.Set;

public class PointPuzzle implements Puzzle<Point, Move> {

	private int n;
	
	public PointPuzzle(int n) {
		this.n = n;
	}

	@Override
	public Point initialPosition() {
		return new Point(1, 1);
	}

	@Override
	public boolean isGoal(Point position) {
		return position.getX()==n && position.getY()==n;
	}

	@Override
	public Set<Move> legalMoves(Point position) {
		if(position.getX()>=n || position.getY()>=n) {
			return new HashSet<Move>();
		}
		
		Set<Move> moves = new HashSet<Move>();
		if(position.getX() < n) {
			moves.add(new Move(0, 1));
		}
		if(position.getY() < n) {
			moves.add(new Move(1, 0));
		}
		if(position.getX() < n && position.getY() < n) {
			moves.add(new Move(1, 1));
		}
		return moves;
	}

	@Override
	public Point move(Point position, Move move) {
		return new Point(position.getX() + move.getxDel(), position.getY() + move.getyDel());
	}

}
