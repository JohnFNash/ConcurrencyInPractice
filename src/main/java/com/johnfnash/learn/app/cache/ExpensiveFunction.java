package com.johnfnash.learn.app.cache;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger> {

	@Override
	public BigInteger compute(String arg) throws InterruptedException {
		//在经过长时间的计算后
		Thread.sleep(1000);
		return new BigInteger(arg);
	}

}
