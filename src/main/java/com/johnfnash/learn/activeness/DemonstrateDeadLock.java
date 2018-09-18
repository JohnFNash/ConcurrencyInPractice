package com.johnfnash.learn.activeness;

import java.util.Random;

public class DemonstrateDeadLock {

	private static final int NUM_THREADS = 20;
	private static final int NUM_ACCOUNTS = 5;
	private static final int NUM_ITERATIONS = 10000;
	
	public static void main(String[] args) {
		final Random rnd = new Random();
		final Account[] accounts = new Account[NUM_ACCOUNTS];
		
		for (int i = 0; i < accounts.length; i++) {
			accounts[i] = new Account(1000000);
		}
		
		class TransferThread extends Thread {
			@Override
			public void run() {
				for (int i = 0; i < NUM_ITERATIONS; i++) {
					int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
					int toAcct = rnd.nextInt(NUM_ACCOUNTS);
					try {
						transfer(accounts[fromAcct], accounts[toAcct], rnd.nextInt(10));
					} catch (InsufficientFundsException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		for (int i = 0; i < NUM_THREADS; i++) {
			new TransferThread().start();
		}
	}
	
	public static void transfer(Account fromAccount, Account toAccount, int amount) throws InsufficientFundsException {
		synchronized (fromAccount) {
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
	
}
