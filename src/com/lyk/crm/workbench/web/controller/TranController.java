package com.lyk.crm.workbench.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lyk.crm.util.ServiceFactory;
import com.lyk.crm.workbench.domain.Tran;
import com.lyk.crm.workbench.service.TranService;
import com.lyk.crm.workbench.service.impl.TranServiceImpl;

public class TranController extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		if("/workbench/transaction/detail.do".equals(path)){
			detail(request,response);
		}
		
	}

	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("进入detail.do");
		
		String id = request.getParameter("id");
		
		TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
		
		Tran t = ts.getTran(id);
		
		request.setAttribute("t", t);
		
		request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request, response);
		
	}

}
