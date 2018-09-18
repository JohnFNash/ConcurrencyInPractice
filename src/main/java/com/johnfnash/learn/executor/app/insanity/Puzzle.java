package com.johnfnash.learn.executor.app.insanity;

import java.util.Set;

// ��ʾ"������"֮������ĳ�����
public interface Puzzle<P,M> {

	P initialPosition();
	
	boolean isGoal(P position);
	
	Set<M> legalMoves(P position);

	P move(P position, M move);
	
}
