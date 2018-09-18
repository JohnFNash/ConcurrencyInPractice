package com.johnfnash.learn.executor.fork_join.demo2;

import java.util.Random;

public class Document {

	private final String words[] = {"the","hello","goodbye","packt", "java","thread","pool","random","class","main"};

	public String[][] generateDocument(int numLines, int numWords, String word){
		int counter=0;
		int wordCount = words.length;
		String document[][] = new String[numLines][numWords];
		Random random = new Random();
		int index;
		for (int i = 0; i < numLines; i++) {
			for(int j=0; j<numWords; j++) {
				index = random.nextInt(wordCount);
				document[i][j] = words[index];
				if(document[i][j].equals(word)) {
					counter++;
				}
			}
		}
		
		System.out.println("DocumentMock: The word appears "+counter+" times in the document");
		return document;
	}
	
}
