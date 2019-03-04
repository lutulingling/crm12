package com.lyk.crm.workbench.service.impl;

import com.lyk.crm.util.SqlSessionUtil;
import com.lyk.crm.workbench.dao.TranDao;
import com.lyk.crm.workbench.domain.Tran;
import com.lyk.crm.workbench.service.TranService;

public class TranServiceImpl implements TranService {
	private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);

	@Override
	public Tran getTran(String id) {
		Tran t = tranDao.getTran(id);
		return t;
	}

}
