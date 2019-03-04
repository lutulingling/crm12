package com.lyk.crm.workbench.dao;

import com.lyk.crm.workbench.domain.Tran;

public interface TranDao {

	int save(Tran tran);

	Tran getTran(String id);

}
