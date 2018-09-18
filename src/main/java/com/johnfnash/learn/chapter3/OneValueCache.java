package com.johnfnash.learn.chapter3;

import java.math.BigInteger;
import java.util.Arrays;

import com.johnfnash.learn.annotation.Immutable;

@Immutable
public class OneValueCache {

	private final BigInteger lastNumber;
	private final BigInteger[] lastFactors;
	
	public OneValueCache(BigInteger i, BigInteger[] factors) {
		this.lastNumber = i;
		this.lastFactors = Arrays.copyOf(factors, factors.length);
	}
	
	public BigInteger[] getFactors(BigInteger i) {
		if(lastNumber == null || !lastNumber.equals(i)) {
			return null;
		} else {
			return Arrays.copyOf(this.lastFactors, this.lastFactors.length);
		}
	}
	
}
