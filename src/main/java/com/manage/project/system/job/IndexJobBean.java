package com.manage.project.system.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.manage.common.utils.DateUtils;
import com.manage.project.system.util.BussinessCacheService;

/**
 * 更新所有缓存
 * @author xufeng
 *
 */
public class IndexJobBean extends QuartzJobBean {
	private static final Logger log = LoggerFactory.getLogger(IndexJobBean.class);
	@Autowired
	private BussinessCacheService bussinessCacheService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		log.info("job refreshCache :" + DateUtils.getTime());
		bussinessCacheService.initAllCache();
	}

}
