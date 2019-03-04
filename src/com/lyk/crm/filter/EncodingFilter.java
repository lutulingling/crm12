package com.lyk.crm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		chain.doFilter(req, res);
		
		
	}

}
