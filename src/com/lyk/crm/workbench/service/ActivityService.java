package com.lyk.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.lyk.crm.setting.domain.User;
import com.lyk.crm.vo.PaginationVO;
import com.lyk.crm.workbench.domain.Activity;
import com.lyk.crm.workbench.domain.ActivityRemark;

public interface ActivityService {

	boolean add(Activity activity);

	PaginationVO<Activity> getActivity(Map<String, Object> map);

	Map<String, Object> getUserAndActivity(String id);

	boolean update(Activity activity);

	boolean delete(String[] idArr);

	Activity detail(String id);

	List<ActivityRemark> getActivityRemark(String aid);

	boolean saveRemark(ActivityRemark ar);

	boolean deleteRemark(String id);

	boolean updateRemark(ActivityRemark ar);

	List<Activity> showActivity(String id);

	List<Activity> getActivityByNameAndById(Map<String, String> map);

	List<Activity> getActivityListByName(Map<String, String> map);


	

}
