package com.johnfnash.learn.executor.fork_join.demo2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class DocumentTask extends RecursiveTask<Integer> {

	private static final long serialVersionUID = -1305420950128616944L;

	private String document[][];
	private int start, end;
	private String word;
	
	public DocumentTask(String[][] document, int start, int end, String word) {
		this.document = document;
		this.start = start;
		this.end = end;
		this.word = word;
	}

	@Override
	protected Integer compute() {
		int result = 0;
		if(end - start < 10) {
			result = processLines(document, start, end, word);
		} else {
			int mid = (start + end)/2;
			DocumentTask task1 = new DocumentTask(document,start,mid,word);
			DocumentTask task2 = new DocumentTask(document,mid,end,word);
			invokeAll(task1,task2);
			
			try {
				result = groupResults(task1.get(), task2.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Integer processLines(String[][] document, int start, int end,String word) {
		List<LineTask> tasks = new ArrayList<LineTask>();
		LineTask task;
		for(int i=start; i<end; i++) {
			task = new LineTask(document[i], 0, document[i].length, word);
			tasks.add(task);
		}
		invokeAll(tasks);
		
		int result = 0;
		for (int i=0; i<tasks.size(); i++) {
			try {
				result += tasks.get(i).get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		return new Integer(result);
	}
	
	private Integer groupResults(Integer number1, Integer number2) {
		return number1 + number2;
	}

}
