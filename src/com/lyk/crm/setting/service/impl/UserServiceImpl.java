package com.lyk.crm.setting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.apache.ibatis.session.SqlSession;

import com.lyk.crm.LoginException;
import com.lyk.crm.setting.dao.UserDao;
import com.lyk.crm.setting.domain.User;
import com.lyk.crm.setting.service.UserService;
import com.lyk.crm.util.DateTimeUtil;
import com.lyk.crm.util.SqlSessionUtil;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

	@Override
	public User getUser(String loginAct, String loginPwd, String ip) throws LoginException {
			
		Map<String, String> map = new HashMap<>();
		map.put("loginAct", loginAct);
		map.put("loginPwd", loginPwd);
		
		User user = userDao.getLogin(map);
		
		if(user==null){
			throw new LoginException("用户名或密码错误");
		}
		
		String expireTime = user.getExpireTime();//失效时间 
		if(expireTime.compareTo(DateTimeUtil.getSysTime())<0){
			throw new LoginException("账户失效");
		}
		
		String lockState = user.getLockState();//锁定状态  0锁定  1启用
		if ("0".equals(lockState)) {
			throw new LoginException("账户被锁定");
		}
		
		String allowIps  = user.getAllowIps();	//运行访问的ip地址
		if(!allowIps.contains(ip)){
			throw new LoginException("ip受限");
		}
		
		
		return user;
	}

	@Override
	public List<User> getUserList() {
		System.out.println("进入getUserListimpl");
		List<User> usList = userDao.getUserList();
		
		return usList;
	}

}
