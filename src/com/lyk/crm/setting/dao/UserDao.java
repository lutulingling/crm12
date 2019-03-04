package com.lyk.crm.setting.dao;

import java.util.List;
import java.util.Map;

import com.lyk.crm.setting.domain.User;

public interface UserDao {
	
	User getLogin(Map<String, String> map);

	List<User> getUserList();

}
