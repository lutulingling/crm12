package com.lyk.crm.workbench.service.impl;

import java.util.List;
import java.util.Map;

import com.lyk.crm.util.DateTimeUtil;
import com.lyk.crm.util.SqlSessionUtil;
import com.lyk.crm.util.UUIDUtil;
import com.lyk.crm.vo.PaginationVO;
import com.lyk.crm.workbench.dao.ClueActivityRelationDao;
import com.lyk.crm.workbench.dao.ClueDao;
import com.lyk.crm.workbench.dao.ClueRemarkDao;
import com.lyk.crm.workbench.dao.ContactsActivityRelationDao;
import com.lyk.crm.workbench.dao.ContactsDao;
import com.lyk.crm.workbench.dao.ContactsRemarkDao;
import com.lyk.crm.workbench.dao.CustomerDao;
import com.lyk.crm.workbench.dao.CustomerRemarkDao;
import com.lyk.crm.workbench.dao.TranDao;
import com.lyk.crm.workbench.dao.TranHistoryDao;
import com.lyk.crm.workbench.domain.Clue;
import com.lyk.crm.workbench.domain.ClueActivityRelation;
import com.lyk.crm.workbench.domain.ClueRemark;
import com.lyk.crm.workbench.domain.Contacts;
import com.lyk.crm.workbench.domain.ContactsActivityRelation;
import com.lyk.crm.workbench.domain.ContactsRemark;
import com.lyk.crm.workbench.domain.Customer;
import com.lyk.crm.workbench.domain.CustomerRemark;
import com.lyk.crm.workbench.domain.Tran;
import com.lyk.crm.workbench.domain.TranHistory;
import com.lyk.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {
	
	private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class); 
	private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
	
	private CustomerDao customerDao =SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
	
	private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
	
	private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
	private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
	private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
	
	private ContactsActivityRelationDao contactsActivityRelationDao =  SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);
	
	private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
	
	private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
	
	
	@Override
	public boolean svaeClue(Clue clue) {
		System.out.println("进入impl");
		boolean flag = true;
		int count = clueDao.saveClue(clue);
		if(count!=1){
			flag=false;
		}
		
		return flag;
	}

	@Override
	public Clue getClue(String id) {
		Clue c = clueDao.getClue(id);
		
		return c;
	}

	@Override
	public boolean removeById(String id) {
		boolean flag = true;
		int count = clueActivityRelationDao.removeById(id);
		if(count!=1){
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean saveRelevance(String cid, String[] idarr) {
		boolean flag = true;
		for(String aid:idarr){
			
			ClueActivityRelation  car = new ClueActivityRelation();
			car.setId(UUIDUtil.getUUID());
			car.setClueId(cid);
			car.setActivityId(aid);
			
			int count = clueActivityRelationDao.saveRelevance(car);
			if(count!=1){
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public boolean cont(String clueId, Tran tran, String createBy) {
		String createTime = DateTimeUtil.getSysTime();
		
		System.out.println(clueId);
		System.out.println(tran);
		System.out.println(createBy);
		System.out.println(createTime);
		boolean flag = true;
		//根据clueId获取clue的信息
		
		Clue c = clueDao.getClue(clueId);
		
		System.out.println(c);
		//获取公司的名称
		String company = c.getCompany();
		System.out.println(company);
		//到客户表中查询公司名称存不存在
		Customer cus = customerDao.getCustomer(company);
		//如果公司名称不存在就创建一个
		if(cus==null){
			cus = new Customer();
			cus.setId(UUIDUtil.getUUID());
			cus.setOwner(c.getOwner());
			cus.setName(company);
			cus.setWebsite(c.getWebsite());
			cus.setPhone(c.getPhone());
			cus.setCreateTime(c.getCreateTime());
			cus.setCreateBy(c.getCreateBy());
			cus.setContactSummary(c.getContactSummary());
			cus.setNextContactTime(c.getNextContactTime());
			cus.setAddress(c.getAddress());
			cus.setDescription(c.getDescription());
			int count = customerDao.saveCustomer(cus);
			
			if(count!=1){
				flag =false;
			}
			
		}
		System.out.println("该保存联系人");
		//保存联系人
		Contacts con = new Contacts();
		
		con.setId(UUIDUtil.getUUID());
		con.setAddress(c.getAddress());
		con.setAppellation(c.getAppellation());
		con.setContactSummary(c.getContactSummary());
		con.setCreateBy(c.getCreateBy());
		con.setCreateTime(c.getCreateTime());
		con.setCustomerId(cus.getId());
		con.setDescription(c.getDescription());
		con.setEmail(c.getEmail());
		con.setFullname(c.getFullname());
		con.setJob(c.getJob());
		con.setMphone(c.getMphone());
		con.setNextContactTime(c.getNextContactTime());
		con.setOwner(c.getOwner());
		con.setSource(c.getSource());
		
		int count2 = contactsDao.saveContacts(con);
		
		if(count2!=1){
			flag = false;
		}
		System.out.println("该第四步了，转换线索备注");
		//(4)线索备注转换到客户备注以及联系人备注
			List<ClueRemark> clueRemarkList = clueRemarkDao.getRemarkListByClueId(clueId);
			for(ClueRemark clueRemark : clueRemarkList){
				
				//将遍历出来的每一条线索关联的备注转换为  客户备注以及联系人备注
				
				//添加客户备注部分
				CustomerRemark customerRemark = new CustomerRemark();
				customerRemark.setId(UUIDUtil.getUUID());
				customerRemark.setCreateBy(createBy);
				customerRemark.setCreateTime(createTime);
				customerRemark.setCustomerId(cus.getId());
				customerRemark.setEditFlag("0");
				customerRemark.setNoteContent(clueRemark.getNoteContent());
				//添加客户备注
				int count3 = customerRemarkDao.save(customerRemark);
				if(count3!=1){
					flag = false;
				}
				
				//添加联系人备注部分
				ContactsRemark contactsRemark = new ContactsRemark();
				contactsRemark.setId(UUIDUtil.getUUID());
				contactsRemark.setCreateBy(createBy);
				contactsRemark.setCreateTime(createTime);
				contactsRemark.setContactsId(con.getId());
				contactsRemark.setEditFlag("0");
				contactsRemark.setNoteContent(clueRemark.getNoteContent());
				int count4 = contactsRemarkDao.save(contactsRemark);
				if(count4!=1){
					flag = false;
				}
				
			}
			System.out.println("第五步");
			//(5)“线索和市场活动”的关系转换到“联系人和市场活动”的关系
			//查询出该线索关联市场活动的   关联关系列表
			List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getByClueId(clueId);
			//遍历关联关系列表，取出所有与该线索关联的市场活动，将市场活动从新与联系人做关联
			for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){
				
				ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
				contactsActivityRelation.setId(UUIDUtil.getUUID());
				contactsActivityRelation.setContactsId(con.getId());
				contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
				//添加联系人市场活动的关联关系
				int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
				if(count5!=1){
					flag = false;
				}
				
			}
			System.out.println("第六步创建交易");
			//(6) 如果有创建交易需求，创建一条交易
			if(tran!=null){
				
				//tran对象已经有一些基本信息了  id money name expectedDate stage activityId createBy createTime
				
				tran.setContactsId(con.getId());
				tran.setContactSummary(c.getContactSummary());
				tran.setCustomerId(cus.getId());
				tran.setDescription(c.getDescription());
				tran.setNextContactTime(c.getNextContactTime());
				tran.setOwner(c.getOwner());
				tran.setSource(c.getSource());
				//添加交易
				int count6 = tranDao.save(tran);
				if(count6!=1){
					flag = false;
				}
				
				System.out.println("第七步，");
				//(7)如果创建了交易，则创建一条该交易下的交易历史
				TranHistory th = new TranHistory();
				th.setId(UUIDUtil.getUUID());
				th.setCreateBy(createBy);
				th.setCreateTime(createTime);
				th.setExpectedDate(tran.getExpectedDate());
				th.setMoney(tran.getMoney());
				th.setStage(tran.getStage());
				th.setTranId(tran.getId());
				int count7 = tranHistoryDao.save(th);
				if(count7!=1){
					flag = false;
				}
				
			}
			System.out.println("第八步，删除线索备注");
			for(ClueRemark clueRemark : clueRemarkList){
				
				int count8 = clueRemarkDao.delete(clueRemark);
				if(count8!=1){
					flag = false;
				}
				
			}
			System.out.println("第九步，删除线索和活动的关系");
			for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){
				
				int count9 = clueActivityRelationDao.delete(clueActivityRelation);
				if(count9!=1){
					flag = false;
				}
			}
			System.out.println("第十步，删除线索");
			int count10 = clueDao.delete(clueId);
			if(count10!=1){
				flag = false;
			}
		
		
		
		return flag;
	}

	@Override
	public PaginationVO<Clue> getPageClue(Map<String, Object> map) {
		System.out.println("进入分页");
		
		List<Clue> clist = clueDao.getClueList(map);
		
		System.out.println(clist);
		
		Integer total = clueDao.getPageToatal(map);
		
		System.out.println(total);
		
		PaginationVO<Clue> cvo = new PaginationVO<>();
		
		cvo.setList(clist);
		
		cvo.setTotal(total);
		
		return cvo;
	}

	

}
