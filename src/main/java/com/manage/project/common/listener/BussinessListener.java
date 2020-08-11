package com.manage.project.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.manage.project.system.util.BussinessCacheService;

/**
 * 系统启动时进行业务初始化初始化
 * @author olina
 *
 */
@WebListener
public class BussinessListener implements ServletContextListener {
	
	@Autowired
	private BussinessCacheService bussinessCacheService;
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		bussinessCacheService.initAllCache();// 初始化所有缓存
	}
	
	
	
	
}
	
