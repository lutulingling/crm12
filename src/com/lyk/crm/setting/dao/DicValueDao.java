package com.lyk.crm.setting.dao;

import java.util.List;

import com.lyk.crm.setting.domain.DicValue;


public interface DicValueDao {

	List<DicValue> getListByCode(String code);

}
