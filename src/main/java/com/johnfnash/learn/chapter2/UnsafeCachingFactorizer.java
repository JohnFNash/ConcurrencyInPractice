package com.johnfnash.learn.chapter2;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.johnfnash.learn.annotation.NotThreadSafe;

@NotThreadSafe
public class UnsafeCachingFactorizer extends HttpServlet {

	private static final long serialVersionUID = 8533068719175617540L;

	private final AtomicReference<BigInteger> lastNumber = 
			new AtomicReference<BigInteger>();
	
	private final AtomicReference<BigInteger[]> lastFactors = 
			new AtomicReference<BigInteger[]>();

	// ����lastNumber �� lastFactors �����̰߳�ȫ�ģ����� lastFactors �л��������֮��Ӧ�õ����� lastNumber �л������ֵ�������޷���֤
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		if(i.equals(lastNumber.get())) {
			encodeIntoResponse(resp, lastFactors.get());
		} else {
			BigInteger[] factors = factor(i);
			lastNumber.set(i);
			lastFactors.set(factors);
			encodeIntoResponse(resp, factors);
		}
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
