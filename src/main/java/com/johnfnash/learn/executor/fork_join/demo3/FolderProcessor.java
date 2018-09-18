package com.johnfnash.learn.executor.fork_join.demo3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderProcessor extends RecursiveTask<List<String>> {

	private static final long serialVersionUID = -262672612634384157L;

	// 将要处理的文件夹的全路径
	private String path;
	
	// 将要查找的文件的扩展名
	private String extension;

	public FolderProcessor(String path, String extension) {
		this.path = path;
		this.extension = extension;
	}

	@Override
	protected List<String> compute() {
		List<String> list = new ArrayList<>();
		List<FolderProcessor> tasks = new ArrayList<FolderProcessor>();
		File file = new File(path);
		File content[] = file.listFiles();
		if(content != null) {
			FolderProcessor task;
			String absolutePath;
			for (int i = 0; i < content.length; i++) {
				absolutePath = content[i].getAbsolutePath();
				if(content[i].isDirectory()) {
					task = new FolderProcessor(absolutePath, extension);
					task.fork();
					
					tasks.add(task);
				} else {
					if(checkFile(absolutePath)) {
						list.add(absolutePath);
					}
				}
			}
			
			if (tasks.size() > 50) {
				System.out.printf("%s: %d tasks ran.\n",file.getAbsolutePath(),tasks.size());
			}
			
			addResultsFromTasks(list, tasks);
		}
		return list;
	}

	private void addResultsFromTasks(List<String> list, List<FolderProcessor> tasks) {
		for (FolderProcessor item : tasks) {
			list.addAll(item.join());
		}
	}
	
	private boolean checkFile(String name) {
		return name.endsWith(extension);
	}
	
}
