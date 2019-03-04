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
import com.lyk.crm.workbench.domain.Clue;
import com.lyk.crm.workbench.domain.Tran;
import com.lyk.crm.workbench.service.ActivityService;
import com.lyk.crm.workbench.service.ClueService;
import com.lyk.crm.workbench.service.impl.ActivityServiceImpl;
import com.lyk.crm.workbench.service.impl.ClueServiceImpl;

public class ClueController extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		if("/workbench/clue/finduserList.do".equals(path)){
			finduserList(request,response);
		}else if("/workbench/clue/svaeClue.do".equals(path)){
			svaeClue(request,response);
		}else if("/workbench/clue/showPage.do".equals(path)){
			showPage(request,response);
		}else if("/workbench/clue/detail.do".equals(path)){
			detail(request,response);
		}else if("/workbench/clue/showActivity.do".equals(path)){
			showActivity(request,response);
		}else if("/workbench/clue/removeBtn.do".equals(path)){
			removeBtn(request,response);
		}else if("/workbench/clue/saveRelevance.do".equals(path)){
			saveRelevance(request,response);
		}else if("/workbench/clue/selectName.do".equals(path)){
			selectName(request,response);
		}else if("/workbench/clue/createTran.do".equals(path)){
			createTran(request,response);
		}else if("/workbench/clue/getActivityListByName.do".equals(path)){
			getActivityListByName(request,response);
		}
		
	}

	private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入getActivityListByName.do");
		
		String name = request.getParameter("name");
		String clueId = request.getParameter("clueId");
		
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("clueId", clueId);
		
		ActivityService as= (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
		List<Activity> alist = as.getActivityListByName(map);
		
		PrintJson.printJsonObj(response, alist);
		
		
	}

	private void createTran(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("进入crateTran.do");
		
		String flag1 = request.getParameter("flag");
		String clueId = request.getParameter("clueId");
		System.out.println(clueId);
		String createBy = ((User)(request.getSession().getAttribute("user"))).getName();
		
		Tran tran = null;
		
		if("a".equals(flag1)){
			
			System.out.println("进入创建交易的servlet");
			tran = new Tran();
			String money = request.getParameter("money");
			String name = request.getParameter("name");
			String expectedDate = request.getParameter("expectedDate");
			String stage = request.getParameter("stage");
			String activityId = request.getParameter("activityId");
			
			//补充信息
			String id  = UUIDUtil.getUUID();
			String createTime = DateTimeUtil.getSysTime();
			
			tran.setId(id);
			tran.setMoney(money);
			tran.setName(name);
			tran.setExpectedDate(expectedDate);
			tran.setStage(stage);
			tran.setActivityId(activityId);
		}
		ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
		System.out.println(clueId);
		
		boolean flag = cs.cont(clueId,tran,createBy);
		
		if(flag){
			response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");
		}
		

	}


	private void selectName(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入selectName.do");
		String name = request.getParameter("name");
		String clueId = request.getParameter("clueId");
		System.out.println(name);
		System.out.println(clueId);
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("String", clueId);
		
		ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		
		List<Activity> alist =  service.getActivityByNameAndById(map);
		
		PrintJson.printJsonObj(response, alist);
		
	}

	private void saveRelevance(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入saveRelevance.do");
		String cid = request.getParameter("cid");
		String[] idarr = request.getParameterValues("id");
		
		ClueService cs = (ClueService)ServiceFactory.getService(new ClueServiceImpl());
		boolean flag = cs.saveRelevance(cid,idarr);
		
		PrintJson.printJsonObj(response, flag);
		
	}

	private void removeBtn(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入removeBtn.do");
		String id = request.getParameter("id");
		
		ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
		
		boolean flag = cs.removeById(id);
		
		PrintJson.printJsonFlag(response, flag);
		
	}

	private void showActivity(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入showActivity.do");
		
		String id = request.getParameter("clueId");
		System.out.println(id);
		System.out.println("执行了");
		
		ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		List<Activity> alist = as.showActivity(id);
		PrintJson.printJsonObj(response, alist);
		
	}

	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("进入detail.do");
		String id = request.getParameter("id");
		System.out.println(id);
		ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
		
		Clue c = service.getClue(id);
		
		request.setAttribute("c", c);
		
		request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);
		
	}

	private void showPage(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入分页");
		
		String pageNoStr = request.getParameter("pageNo");
		String pageSizeStr = request.getParameter("pageSize");
		String fullname = request.getParameter("fullname");
		String appellation = request.getParameter("appellation");
		String company = request.getParameter("company");
		String phone = request.getParameter("phone");
		String mphone = request.getParameter("mphone");
		String source = request.getParameter("source");
		String owner = request.getParameter("owner");
		String state = request.getParameter("state");
		
		Integer pageNo = Integer.valueOf(pageNoStr);
		Integer pageSize = Integer.valueOf(pageSizeStr);
		
		int skipCount = (pageNo-1)*pageSize;
		
		Map<String, Object> map = new HashMap<>();
		map.put("skipCount", skipCount);
		map.put("pageSize", pageSize);
		map.put("fullname", fullname);
		map.put("appellation", appellation);
		map.put("company", company);
		map.put("phone", phone);
		map.put("mphone", mphone);
		map.put("source", source);
		map.put("owner", owner);
		map.put("state", state);
		
		ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
		PaginationVO<Clue> cvo = cs.getPageClue(map);
		
		PrintJson.printJsonObj(response, cvo);
	}

	private void svaeClue(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入saveClue.do");
		
		String id = UUIDUtil.getUUID();
		String fullname = request.getParameter("fullname");
		String appellation = request.getParameter("appellation");
		String owner = request.getParameter("owner");
		String company = request.getParameter("company");
		String job = request.getParameter("job");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String website = request.getParameter("website");
		String mphone = request.getParameter("mphone");
		String state = request.getParameter("state");
		String source = request.getParameter("source");
		String createBy = ((User)request.getSession().getAttribute("user")).getName();
		String createTime = DateTimeUtil.getSysTime();
		String description = request.getParameter("description");
		String contactSummary = request.getParameter("contactSummary");
		String nextContactTime = request.getParameter("nextContactTime");
		String address = request.getParameter("address");
		
		System.out.println(website);
		System.out.println(mphone);
		System.out.println(state);
		
		Clue clue = new Clue();
		clue.setId(id);
		clue.setFullname(fullname);
		clue.setAppellation(appellation);
		clue.setOwner(owner);
		clue.setCompany(company);
		clue.setJob(job);
		clue.setEmail(email);
		clue.setPhone(phone);
		clue.setMphone(mphone);
		clue.setWebsite(website);
		clue.setState(state);
		clue.setSource(source);
		clue.setCreateTime(createTime);
		clue.setCreateBy(createBy);
		clue.setDescription(description);
		clue.setContactSummary(contactSummary);
		clue.setNextContactTime(nextContactTime);
		clue.setAddress(address);
		
		ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
		
		boolean flag = cs.svaeClue(clue);
		
		PrintJson.printJsonFlag(response, flag);
		
		
	}

	private void finduserList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入finduserList1.do");
		
		UserService us =  (UserService) ServiceFactory.getService(new UserServiceImpl());
		
		List<User> user = us.getUserList();
		
		PrintJson.printJsonObj(response, user);
		
	}

}
