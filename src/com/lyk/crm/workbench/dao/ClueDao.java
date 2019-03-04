package com.lyk.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.lyk.crm.workbench.domain.Clue;

public interface ClueDao {

	int saveClue(Clue clue);

	Clue getClue(String id);

	int delete(String clueId);

	List<Clue> getClueList(Map<String, Object> map);

	Integer getPageToatal(Map<String, Object> map);

	

}
