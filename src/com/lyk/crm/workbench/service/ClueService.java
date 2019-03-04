package com.lyk.crm.workbench.service;

import java.util.Map;

import com.lyk.crm.vo.PaginationVO;
import com.lyk.crm.workbench.domain.Clue;
import com.lyk.crm.workbench.domain.ClueActivityRelation;
import com.lyk.crm.workbench.domain.Tran;

public interface ClueService {

	boolean svaeClue(Clue clue);

	Clue getClue(String id);

	boolean removeById(String id);

	boolean saveRelevance(String cid, String[] idarr);

	boolean cont(String clueId, Tran tran, String createBy);

	PaginationVO<Clue> getPageClue(Map<String, Object> map);



}
