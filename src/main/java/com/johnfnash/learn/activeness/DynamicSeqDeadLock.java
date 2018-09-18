package com.johnfnash.learn.activeness;

public class DynamicSeqDeadLock {

	public void transfer(Account fromAccount,Account toAccount, int amount) throws InsufficientFundsException {
		synchronized (fromAccount) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (toAccount) {
				if(fromAccount.getBalance() < amount) {
					throw new InsufficientFundsException();
				} else {
					fromAccount.debit(amount);
					toAccount.credit(amount);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		DynamicSeqDeadLock lock = new DynamicSeqDeadLock();
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

class Account {
	
	private int balance;
	
	public Account(int balance) {
		this.balance = balance;
	}

	public int getBalance() {
		return balance;
	}
	
	public void debit(int amount) {
		this.balance -= amount;
	}
	
	public void credit(int amount) {
		this.balance += amount;
	}
	
}

class InsufficientFundsException extends Exception {
	private static final long serialVersionUID = -5225130977930613682L;
	
	public InsufficientFundsException() {
		super("Transfering fails. Funds not sufficient.");
	}
	
}
