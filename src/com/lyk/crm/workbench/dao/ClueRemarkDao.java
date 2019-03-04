package com.lyk.crm.workbench.dao;

import java.util.List;

import com.lyk.crm.workbench.domain.ClueRemark;

public interface ClueRemarkDao {

	List<ClueRemark> getRemarkListByClueId(String clueId);

	int delete(ClueRemark clueRemark);

}
