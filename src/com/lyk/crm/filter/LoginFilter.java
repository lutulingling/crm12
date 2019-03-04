package com.lyk.crm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lyk.crm.setting.domain.User;

public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		HttpServletResponse response = (HttpServletResponse) res;
		
		String path = request.getServletPath();
		System.out.println(path);
		
		if("/login.jsp".equals(path) || "/setting/user/getLogin.do".equals(path)){
			
			chain.doFilter(req, res);
		}else {
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			if(user!=null){
				chain.doFilter(req, res);
			}else {
				response.sendRedirect(request.getContextPath()+"/login.jsp");
			}
			
			
		}
		
		
	}

}
