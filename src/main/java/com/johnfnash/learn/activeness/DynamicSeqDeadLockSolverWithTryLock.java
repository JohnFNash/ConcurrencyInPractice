package com.johnfnash.learn.activeness;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DynamicSeqDeadLockSolverWithTryLock {

	public boolean transfer(Account fromAcct, Account toAcct, int amount,
			long timeout, TimeUnit unit) throws InsufficientFundsException, InterruptedException {
		long fixedDelay = 1000000;
		Random rnd = new Random(System.nanoTime());
		long randMod = 100000;
		long stopTime = System.nanoTime() + unit.toNanos(timeout);
		
		while(true) {
			if(fromAcct.lock.tryLock()) {
				try {
					if(toAcct.lock.tryLock()) {
						try {
							if(fromAcct.getBalance() < amount) {
								throw new InsufficientFundsException();
							} else {
								fromAcct.debit(amount);
								toAcct.credit(amount);
								System.out.println("transfer from " + fromAcct + " to " + toAcct + " success");
								return true;
							}
						} finally {
							toAcct.lock.unlock();
						}
					} else {
						System.out.println(toAcct + " lock fail");
					}
				} finally {
					fromAcct.lock.unlock();
				}
			}
			
			// 超时之后直接退出
			if(System.nanoTime() > stopTime) {
				return false;
			}
			
			// 选择随机时间过后再进行重试
			TimeUnit.NANOSECONDS.sleep(fixedDelay + rnd.nextLong()%randMod);
		}
	}

	private class Account {

		private int balance;
		ReentrantLock lock = new ReentrantLock();

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

	public static void main(String[] args) {
		DynamicSeqDeadLockSolverWithTryLock lock = new DynamicSeqDeadLockSolverWithTryLock();
		Account fromAccount = lock.new Account(1000);
		Account toAccount = lock.new Account(2000);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lock.transfer(fromAccount, toAccount, 500, 2000, TimeUnit.MILLISECONDS);
				} catch (InsufficientFundsException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lock.transfer(toAccount, fromAccount, 600, 2000, TimeUnit.MILLISECONDS);
				} catch (InsufficientFundsException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
