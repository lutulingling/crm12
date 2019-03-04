package com.lyk.crm.listener;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lyk.crm.setting.domain.DicValue;
import com.lyk.crm.setting.service.DicService;
import com.lyk.crm.setting.service.impl.DicServiceImpl;
import com.lyk.crm.util.ServiceFactory;


public class SysInitListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent event)  { 
         System.out.println("加载application");
         
         ServletContext application = event.getServletContext();
         
         DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());
         
         Map<String,List<DicValue>> map = ds.getAll();
 		
 		//将map中的键值对转换为上下文域对象中的键值对
 		Set<String> set = map.keySet();
 		for(String key : set){
 			
 			application.setAttribute(key, map.get(key));
 			
 		}
         
    }
	
}
