package com.johnfnash.learn.chapter2;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.johnfnash.learn.annotation.ThreadSafe;

@ThreadSafe
public class StatelessFactorizer extends HttpServlet {

	private static final long serialVersionUID = -2428126086086102147L;

	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		BigInteger[] factors = factor(i);
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
