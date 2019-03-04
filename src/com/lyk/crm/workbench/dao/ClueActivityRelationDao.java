package com.lyk.crm.workbench.dao;

import java.util.List;

import com.lyk.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {

	int removeById(String id);

	int saveRelevance(ClueActivityRelation car);

	List<ClueActivityRelation> getByClueId(String clueId);

	int delete(ClueActivityRelation clueActivityRelation);

	

}
