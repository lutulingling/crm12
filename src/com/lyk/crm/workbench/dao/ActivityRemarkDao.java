package com.lyk.crm.workbench.dao;

import java.util.List;

import com.lyk.crm.workbench.domain.ActivityRemark;

public interface ActivityRemarkDao {

	int getTatlopById(String[] idArr);

	int deleteById(String[] idArr);

	List<ActivityRemark> getListActRam(String aid);

	int svaeRemark(ActivityRemark ar);

	int deleteRemark(String id);

	int updateRemark(ActivityRemark ar);


}
