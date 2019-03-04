package com.lyk.crm.setting.service;

import java.util.List;

import com.lyk.crm.LoginException;
import com.lyk.crm.setting.domain.User;

public interface UserService {

	User getUser(String loginAct, String loginPwd, String ip) throws LoginException;

	List<User> getUserList();

}
