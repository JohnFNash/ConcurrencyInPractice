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
public class SynchronizedFactorizer extends HttpServlet {
	private static final long serialVersionUID = 2826411114586571343L;

	@GuardedBy("this") private BigInteger lastNumber;
	@GuardedBy("this") private BigInteger[] lastFactors;

	// ������� synchronized ֮���̰߳�ȫ������synchronized������method�ϣ��������Ӧ�Էǳ���
	@Override
	protected synchronized void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		if(i.equals(lastNumber)) {
			encodeIntoResponse(resp, lastFactors);
		} else {
			BigInteger[] factors = factor(i);
			lastNumber = i;
			lastFactors = factors;
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
