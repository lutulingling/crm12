package com.lyk.crm.setting.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lyk.crm.LoginException;
import com.lyk.crm.setting.domain.User;
import com.lyk.crm.setting.service.UserService;
import com.lyk.crm.setting.service.impl.UserServiceImpl;
import com.lyk.crm.util.MD5Util;
import com.lyk.crm.util.PrintJson;
import com.lyk.crm.util.ServiceFactory;

public class UserController extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		System.out.println(path);
			if(path.equals("/setting/user/getLogin.do")){
				getLogin(request,response);
			}
		
		
	}

	private void getLogin(HttpServletRequest request, HttpServletResponse response) {
		
		// TODO Auto-generated method stub
		String loginAct = request.getParameter("loginAct");
		String loginPwd = request.getParameter("loginPwd");
		String ip = request.getRemoteAddr();
		loginPwd = MD5Util.getMD5(loginPwd);
		
		UserService service = (UserService) ServiceFactory.getService(new UserServiceImpl());
		
		
		try {
			
			System.out.println("servlet1");
			User us = service.getUser(loginAct,loginPwd,ip);
			
			
			request.getSession().setAttribute("user", us);
			
			PrintJson.printJsonFlag(response, true);
			
			
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			
			String msg = e.getMessage();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", false);
			map.put("msg", msg);
			
			PrintJson.printJsonObj(response, map);
			
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
}
