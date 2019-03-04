package com.lyk.crm.setting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.lyk.crm.setting.dao.DicTypeDao;
import com.lyk.crm.setting.dao.DicValueDao;
import com.lyk.crm.setting.domain.DicType;
import com.lyk.crm.setting.domain.DicValue;
import com.lyk.crm.setting.service.DicService;
import com.lyk.crm.util.SqlSessionUtil;
import com.lyk.crm.workbench.dao.ClueDao;
import com.lyk.crm.workbench.domain.Clue;



public class DicServiceImpl implements DicService {
	
	private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
	private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);
	
	@Override
	public Map<String, List<DicValue>> getAll() {
		
		Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();
		
		//根据字典类型表取得所有的类型
		List<DicType> dtList = dicTypeDao.getAll();
		
		//遍历所有的字典类型
		for(DicType dt:dtList){
			
			//取得每一个code
			String code = dt.getCode();
			
			//根据每一个code，按照code取字典值列表
			List<DicValue> dvList = dicValueDao.getListByCode(code);
			
			map.put(code+"List", dvList);
			
		}
		
		return map;
	}
	
	
}






















