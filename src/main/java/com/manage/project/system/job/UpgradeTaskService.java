package com.manage.project.system.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.manage.common.utils.DateUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.vending.domain.VendingUpgrade;
import com.manage.project.system.vending.domain.VendingUpgradeTask;
import com.manage.project.system.vending.service.IVendingUpgradeService;
import com.manage.project.system.vending.service.IVendingUpgradeTaskService;

/**
 * 升级任务推送
 * 
 * @author zhangjiawei
 *
 */
@Service
@EnableScheduling
public class UpgradeTaskService {
	
	@Autowired
	private IVendingUpgradeService vendingUpgradeService;
	
	@Autowired
	private IVendingUpgradeTaskService vendingUpgradeTaskService;
	
	private Logger log = LoggerFactory.getLogger(UpgradeTaskService.class);

	/**
	 * 推送升级任务
	 */
	@Scheduled(cron ="0 0/1 * * * ?")
	public void pushCmd() {
		log.info("推送升级任务:"+DateUtils.getTime());
		VendingUpgrade vendingUpgrade = new VendingUpgrade();
		vendingUpgrade.setIsPush(Constant.IS_PUSH_FALSE);
		vendingUpgrade.setPlanTime(DateUtils.getTime());
		List<VendingUpgrade> list = vendingUpgradeService.selectNotPushVendingUpgradeList(vendingUpgrade);
		for (VendingUpgrade upgrade : list) {
			VendingUpgradeTask vendingUpgradeTask = new VendingUpgradeTask();
			vendingUpgradeTask.setUpgradeId(upgrade.getUpgradeId());
			List<VendingUpgradeTask> vendingUpgradeTaskList = vendingUpgradeTaskService.selectVendingUpgradeTaskAndVendingUpgradeList(vendingUpgradeTask);
			upgrade.setVendingUpgradeTaskList(vendingUpgradeTaskList);
			vendingUpgradeService.insertVendingCmd(upgrade);
		}
	}
}
