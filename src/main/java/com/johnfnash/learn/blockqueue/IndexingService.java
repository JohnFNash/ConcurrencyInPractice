package com.johnfnash.learn.blockqueue;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IndexingService {

	private static final int CAPACITY = 1000;
	private static final File POSITION = new File(""); // use this as flag of ending
	private final BlockingQueue<File> queue;
	private final FileFilter fileFilter;
	private File root;
	
	private IndexerThread consumer = new IndexerThread();
	private CrawlerThread producer = new CrawlerThread();
	
	public IndexingService(File root, final FileFilter fileFilter) {
		this.root = root;
		this.queue = new LinkedBlockingQueue<File>(CAPACITY);
		this.fileFilter = new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || fileFilter.accept(f);
			}
		};
	}
	
	private boolean alreadyIndexed(File f) {
        return false;
    }
	
	class CrawlerThread extends Thread {
		
		public void run() {
			try {
				crawl(root);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				 /* fall through */
			} finally {
				while(true) {
					try {
						queue.put(POSITION);
						break;
					} catch (InterruptedException e) { /* retry */
					}
				}
			}
		}
		
		 private void crawl(File root) throws InterruptedException {
            File[] entries = root.listFiles(fileFilter);
            if (entries != null) {
                for (File entry : entries) {
                    if (entry.isDirectory())
                        crawl(entry);
                    else if (!alreadyIndexed(entry))
                        queue.put(entry);
                }
            }
        }
		
	}
	
	class IndexerThread extends Thread {
		
		public void run() {
			try {
				while(true) {
					File file = queue.take();
					if(file == POSITION) {
						break;
					} else {
						indexFile(queue.take());
					}					
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		
		public void indexFile(File file) {
			/* ... */
			System.out.println("indexing: " + file.getAbsolutePath());
		}
		
	}
	
	public void start() {
		producer.start();
		consumer.start();
	}
	
	public void stop() {
		producer.interrupt();
	}
	
	public void awaitTermination() throws InterruptedException {
		consumer.join();
	}
	
	public static void main(String[] args) throws InterruptedException {
		final FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getAbsolutePath().endsWith(".java");
			}
		};
		IndexingService service = new IndexingService(new File("D:\\workspace\\code\\javaee\\springboot"), filter);
		service.start();
		service.awaitTermination();
	}
	
}
