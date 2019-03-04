package com.lyk.crm.workbench.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lyk.crm.setting.domain.User;
import com.lyk.crm.setting.service.UserService;
import com.lyk.crm.setting.service.impl.UserServiceImpl;
import com.lyk.crm.util.DateTimeUtil;
import com.lyk.crm.util.PrintJson;
import com.lyk.crm.util.ServiceFactory;
import com.lyk.crm.util.UUIDUtil;
import com.lyk.crm.vo.PaginationVO;
import com.lyk.crm.workbench.domain.Activity;
import com.lyk.crm.workbench.domain.ActivityRemark;
import com.lyk.crm.workbench.service.ActivityService;
import com.lyk.crm.workbench.service.impl.ActivityServiceImpl;

public class ActivityServlet extends HttpServlet{
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String path = request.getServletPath();
		if("/workbench/user/finduser.do".equals(path)){
			finduser(request,response);
			
		}else if ("/workbench/activity/add.do".equals(path)) {
			add(request,response);
		}else if ("/workbench/activity/pageList.do".equals(path)) {
			pageList(request,response);
		}else if ("/workbench/activity/finduUserAndActivity.do".equals(path)) {
			finduUserAndActivity(request,response);
		}else if ("/wrokbench/activity/update.do".equals(path)) {
			update(request,response);
		}else if ("/workbench/activity/delete.do".equals(path)) {
			delete(request,response);
		}else if ("/workbench/activity/detail.do".equals(path)) {
			detail(request,response);
		}else if ("/workbench/activityRemark/findRemark.do".equals(path)) {
			findRemark(request,response);
		}else if ("/workbench/activityRemark/svaeRemark.do".equals(path)) {
			saveRemark(request,response);
		}else if("/workbench/activityRemark/deleteRemark.do".equals(path)){
			deleteRemark(request,response);
		}else if("/workbench/activityRemark/updateRemark.do".equals(path)){
			updateRemark(request,response);
		}
		
		
	}



	private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入updataRemark.do");
		
		String id  = request.getParameter("id");
		System.out.println(id);
		String noteContent = request.getParameter("noteContent");
		String editTime = DateTimeUtil.getSysTime();
		String editBy = ((User)request.getSession().getAttribute("user")).getName();
		String editFlag = "1";
		ActivityRemark ar = new ActivityRemark();
		ar.setId(id);
		ar.setNoteContent(noteContent);
		ar.setEditTime(editTime);
		ar.setEditBy(editBy);
		ar.setEditFlag(editFlag);
		
		ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		
		boolean flag = service.updateRemark(ar);
		Map<String, Object> map = new HashMap<>();
		map.put("success", flag);
		map.put("ar", ar);
		PrintJson.printJsonObj(response, map);
		
	}



	private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入deleteRemark");
		String id = request.getParameter("id");
		System.out.println(id);
		ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		boolean flag = service.deleteRemark(id);
		
		PrintJson.printJsonFlag(response, flag);
		
		
	}

	private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入svaeRemark");
		String noteContent = request.getParameter("noteContent");
		String activityId = request.getParameter("activityId");
		String id = UUIDUtil.getUUID();
		String createTime = DateTimeUtil.getSysTime();
		String createBy = ((User)request.getSession().getAttribute("user")).getName();
		String editFlag = "0";
		
		ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		
		ActivityRemark ar = new ActivityRemark();
		ar.setId(id);
		ar.setNoteContent(noteContent);
		ar.setCreateTime(createTime);
		ar.setCreateBy(createBy);
		ar.setEditFlag(editFlag);
		ar.setActivityId(activityId);
		boolean flag = service.saveRemark(ar);
		
		Map<String, Object> map = new HashMap<>();
		map.put("success",flag);
		map.put("ar", ar);
		PrintJson.printJsonObj(response, map);
		
		
	}

	private void findRemark(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入findRemark.do");
		String aid = request.getParameter("aid");
		
		ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		List<ActivityRemark> listar = service.getActivityRemark(aid);
		
		PrintJson.printJsonObj(response,listar);
		
		
	}

	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("进入到详细页");
		String id = request.getParameter("id");
		ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		
		Activity a = service.detail(id);
		
		request.setAttribute("a", a);
		System.out.println(a);
		request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
		
		
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入delete.do");
		String idArr[] = request.getParameterValues("id");
		
		ActivityService service = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
		
		System.out.println(idArr);
		boolean flag = service.delete(idArr);
		
		
		PrintJson.printJsonFlag(response, flag);
		
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入update.do");
		
		String id = request.getParameter("id");
		String owner          =request.getParameter("owner");
		String name           =request.getParameter("name");
		String startDate      =request.getParameter("startDate");
		String endDate        =request.getParameter("endDate");
		String cost           =request.getParameter("cost");
		String description    =request.getParameter("description");
		String editTime = DateTimeUtil.getSysTime();
		String editBy = ((User)request.getSession().getAttribute("user")).getName();
		
		Activity activity = new Activity();
		activity.setId(id);
		activity.setOwner(owner);
		activity.setName(name);
		activity.setStartDate(startDate);
		activity.setEndDate(endDate);
		activity.setCost(cost);
		activity.setDescription(description);
		activity.setEditTime(editTime);
		activity.setEditBy(editBy);
		
		ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		boolean flag = service.update(activity);
		PrintJson.printJsonFlag(response, flag);
		
		
		
	}

	private void finduUserAndActivity(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入findUserAndActivity");
		String id = request.getParameter("id");
		//需要返回一个userlist，一个activity
		
		ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		
		Map<String, Object> map = service.getUserAndActivity(id);
		
		PrintJson.printJsonObj(response, map);
		
		
	}

	private void pageList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入pageList.do");
		String pageNoStr = request.getParameter("pageNo");
		String pageSizeStr = request.getParameter("pageSize");
		
		String name = request.getParameter("name");
		String owner = request.getParameter("owner");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		Integer pageNo = Integer.valueOf(pageNoStr);
		Integer pageSize = Integer.valueOf(pageSizeStr);
		
		int skipCount = (pageNo-1)*pageSize;
		
		ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("owner", owner);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("skipCount", skipCount);
		map.put("pageSize", pageSize);
		
		PaginationVO<Activity> vo = service.getActivity(map);
		
		PrintJson.printJsonObj(response, vo);
		
		
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入activity/add");
		String id = UUIDUtil.getUUID();
		String owner= request.getParameter("owner");
		String name=request.getParameter("name");
		String startDate= request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String cost = request.getParameter("cost");
		String description = request.getParameter("description");
		String createTime = DateTimeUtil.getSysTime();
		String createBy = ((User)request.getSession().getAttribute("user")).getName();
		
		Activity activity = new Activity();
		activity.setId(id);
		activity.setOwner(owner);
		activity.setName(name);
		activity.setStartDate(startDate);
		activity.setEndDate(endDate);
		activity.setCost(cost);
		activity.setDescription(description);
		activity.setCreateTime(createTime);
		activity.setCreateBy(createBy);

		ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		
		boolean flag = service.add(activity);
		
		PrintJson.printJsonFlag(response, flag);
		
		
	}

	private void finduser(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("进入finduser");
		// TODO Auto-generated method stub
		UserService us =  (UserService) ServiceFactory.getService(new UserServiceImpl());
		
		List<User> user = us.getUserList();
		
		PrintJson.printJsonObj(response, user);
		
		
	}

}
