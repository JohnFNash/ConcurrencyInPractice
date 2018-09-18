package com.johnfnash.learn.activeness;

public class DynamicSeqDeadLockSolver {
	private static final Object tieLock = new Object();

	public void transfer(Account fromAccount, Account toAccount, int amount) throws InsufficientFundsException {
		class Helper {
			public void transfer() throws InsufficientFundsException {
				if(fromAccount.getBalance() < amount) {
					throw new InsufficientFundsException();
				} else {
					fromAccount.debit(amount);
					toAccount.credit(amount);
					System.err.println("Transftering " + amount + " finished!");
				}
			}
		}
		
		int fromHash = System.identityHashCode(fromAccount);
		int toHash = System.identityHashCode(toAccount);
		System.out.println("fromHash: " + fromHash);
		System.out.println("toHash: " + toHash);
		
		if(fromHash < toHash) {
			synchronized (fromAccount) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (toAccount) {
					new Helper().transfer();
				}
			}
		} else if(fromHash > toHash) {
			synchronized (toAccount) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (fromAccount) {
					new Helper().transfer();
				}
			}
		} else {
			// in case of same hash
			synchronized (tieLock) {
				synchronized (fromAccount) {
					synchronized (toAccount) {
						new Helper().transfer();
					}
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		DynamicSeqDeadLockSolver lock = new DynamicSeqDeadLockSolver();
		Account fromAccount = new Account(1000);
		Account toAccount = new Account(2000);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lock.transfer(fromAccount, toAccount, 500);
				} catch (InsufficientFundsException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lock.transfer(toAccount, fromAccount, 600);
				} catch (InsufficientFundsException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
}
