package com.johnfnash.learn.app.cache;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger> {

	@Override
	public BigInteger compute(String arg) throws InterruptedException {
		//�ھ�����ʱ��ļ����
		Thread.sleep(1000);
		return new BigInteger(arg);
	}

}
