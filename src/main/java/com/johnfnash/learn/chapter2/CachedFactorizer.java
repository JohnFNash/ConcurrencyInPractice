package com.johnfnash.learn.chapter2;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.johnfnash.learn.annotation.GuardedBy;
import com.johnfnash.learn.annotation.ThreadSafe;

@ThreadSafe
public class CachedFactorizer extends HttpServlet {

	private static final long serialVersionUID = -3709392852225041663L;

	@GuardedBy("this") private BigInteger lastNumber;
	@GuardedBy("this") private BigInteger[] lastFactors;
	@GuardedBy("this") private long hits;
	@GuardedBy("this") private long cacheHits;
	
	public synchronized long getHits() {
		return hits;
	}
	
	public synchronized double getCacheHitRatio() {
		return hits == 0 ? 0.0 : (double) cacheHits / (double) hits;
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		BigInteger[] factors = null;
		synchronized (this) {
			++hits;
			if(i.equals(lastNumber)) {
				++cacheHits;
				factors = lastFactors.clone();
			}
		}
		
		if(factors == null) {
			factors = factor(i); // 执行时间较长的操作不要持有锁
			synchronized (this) {
				lastNumber = i;
				lastFactors = factors.clone();
			}
		}
		
		encodeIntoResponse(resp, factors);
	}
	
	void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
	}

	BigInteger extractFromRequest(ServletRequest req) {
		return new BigInteger("7");
	}

	BigInteger[] factor(BigInteger i) {
		// Doesn't really factor
		return new BigInteger[] { i };
	}
	
}
