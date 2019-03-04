package com.lyk.crm.workbench.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lyk.crm.setting.dao.UserDao;
import com.lyk.crm.setting.domain.User;
import com.lyk.crm.util.SqlSessionUtil;
import com.lyk.crm.vo.PaginationVO;
import com.lyk.crm.workbench.dao.ActivityDao;
import com.lyk.crm.workbench.dao.ActivityRemarkDao;
import com.lyk.crm.workbench.domain.Activity;
import com.lyk.crm.workbench.domain.ActivityRemark;
import com.lyk.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
		
	private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class); 
	private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
	private ActivityRemarkDao actremDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class); 
	@Override
	public boolean add(Activity activity) {
		System.out.println("进入serviceImpl");
		int count = activityDao.addActivaty(activity);
		
		boolean flag = true;
		if(count<1){
			flag = false;
		}
				
		return flag;
	}

	@Override
	public PaginationVO<Activity> getActivity(Map<String, Object> map) {
		System.out.println("进入分页serviceImpl");
		//查询   名称	所有者	开始日期	结束日期
		List<Activity> list = activityDao.getActivityList(map);
		//查询记录数
		Integer total = activityDao.getPageTotal(map);
		
		PaginationVO<Activity> vo = new PaginationVO<>();
		vo.setList(list);
		vo.setTotal(total);
		
		return vo;
	}

	@Override
	public Map<String, Object> getUserAndActivity(String id) {
		Map<String, Object> map = new HashMap<>();
		List<User> list = userDao.getUserList();
		
		Activity a = activityDao.getById(id);
		map.put("userList", list);
		map.put("a", a);
		
		
		return map;
	}

	@Override
	public boolean update(Activity activity) {
		System.out.println("進入update");
			boolean flag = true;
			
			int count = activityDao.update(activity);
			
			if(count==0){
				flag = false;
			}
			
		return flag;
	}

	@Override
	public boolean delete(String[] idArr) {
		System.out.println("进入deleteImpl");
		
		boolean flag = true;
		
		int tatlop = actremDao.getTatlopById(idArr);
		
		int deleteCount = actremDao.deleteById(idArr);
		if(tatlop!=deleteCount){
			flag =false;
		}
		
		int count = activityDao.delete(idArr);
		
		if(count!=idArr.length){
			flag = false;
		}
		
		return flag;
	}

	@Override
	public Activity detail(String id) {
		Activity a = activityDao.detail(id);
		System.out.println("destion:"+a.getDescription());
		return a;
	}

	@Override
	public List<ActivityRemark> getActivityRemark(String aid) {
		
		System.out.println("进入ActReaImpl");
		List<ActivityRemark> listar = actremDao.getListActRam(aid);
		return listar;
	}

	@Override
	public boolean saveRemark(ActivityRemark ar) {
		System.out.println("进入impl");
		boolean flag = true;
		int count = actremDao.svaeRemark(ar);
		System.out.println(count);
		if(count!=1){
			flag = false;
		}
		
		return flag;
	}

	@Override
	public boolean deleteRemark(String id) {
		System.out.println("进入deleteRemarkimpl");
		boolean flag = true;
		int count = actremDao.deleteRemark(id);
		
		if(count!=1){
			flag = false;
		}
		
		
		return flag;
	}

	@Override
	public boolean updateRemark(ActivityRemark ar) {
		boolean flag = true;
		
		int count = actremDao.updateRemark(ar);
		
		if(count!=1){
			flag = false;
		}
		
		return flag;
	}

	@Override
	public List<Activity> showActivity(String id) {
			List<Activity> alist = activityDao.showActivity(id);
			
			
		return alist;
	}

	@Override
	public List<Activity> getActivityByNameAndById(Map<String, String> map) {
		List<Activity> alist = activityDao.getActivityByNameAndById(map);
		
		return alist;
	}

	@Override
	public List<Activity> getActivityListByName(Map<String, String> map) {
		List<Activity> alist = activityDao.getActivityListByName(map);
		
		return alist;
	}

	
	
	

}
