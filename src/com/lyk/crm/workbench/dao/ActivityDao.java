package com.lyk.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.lyk.crm.workbench.domain.Activity;

public interface ActivityDao {
	
	int addActivaty(Activity activity);

	List<Activity> getActivityList(Map<String, Object> map);

	Integer getPageTotal(Map<String, Object> map);

	Activity getById(String id);

	int update(Activity activity);

	int delete(String[] idArr);

	Activity detail(String id);

	List<Activity> showActivity(String id);

	List<Activity> getActivityByNameAndById(Map<String, String> map);

	List<Activity> getActivityListByName(Map<String, String> map);

}
