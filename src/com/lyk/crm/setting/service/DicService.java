package com.lyk.crm.setting.service;

import java.util.List;
import java.util.Map;

import com.lyk.crm.setting.domain.DicValue;
import com.lyk.crm.workbench.domain.Clue;


public interface DicService {

	Map<String, List<DicValue>> getAll();

}
