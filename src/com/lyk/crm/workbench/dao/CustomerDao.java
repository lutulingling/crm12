package com.lyk.crm.workbench.dao;

import com.lyk.crm.workbench.domain.Customer;

public interface CustomerDao {

	Customer getCustomer(String company);

	int saveCustomer(Customer cus);

}
