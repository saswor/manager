package com.manage.project.system.job;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.manage.common.utils.spring.SpringUtils;
import com.manage.project.system.index.IndexWsHandler;
import com.manage.project.system.util.BussinessCacheService;

/**
 * 首页推送service
 * @author xufeng
 *
 */
@Service
@EnableScheduling
public class IndexPushService {
	private Logger log=LoggerFactory.getLogger(IndexPushService.class);
	
	@Autowired
	private IndexWsHandler indexWsHandler;
	
	@Autowired
	private StoredProcedureService storedProcedureService;
	
    /**
     *  每2秒通过websocket更新首页内容 
     */
    @Scheduled(cron ="0/2 * * * * ?")
    public void pushDataToIndex(){
        log.debug("--------------开始推送首页数据------------");
        try {
        	indexWsHandler.sendIndex();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
    }
    
    /**
     *  每3分钟更新首页汇总和
     */
    @Scheduled(cron ="3 0/3 * * * ?")
    //@Scheduled(cron ="0/5 * * * * ?")
    public void initHomePageCache() {
    	//存储过程生成相应报表
    	storedProcedureService.genOrderBase();
    	storedProcedureService.genReportBoard();
    	BussinessCacheService bussinessCacheService = (BussinessCacheService)SpringUtils.getBean("bussinessCacheService");
    	bussinessCacheService.initIndexCache();
    }
}
