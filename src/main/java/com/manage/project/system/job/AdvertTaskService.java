package com.manage.project.system.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.manage.common.utils.DateUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.advert.domain.AdvertConfig;
import com.manage.project.system.advert.domain.AdvertDevice;
import com.manage.project.system.advert.domain.AdvertStrategy;
import com.manage.project.system.advert.mapper.AdvertConfigMapper;
import com.manage.project.system.advert.mapper.AdvertDeviceMapper;
import com.manage.project.system.advert.service.IAdvertConfigService;
import com.manage.project.system.advert.service.IAdvertDeviceService;
import com.manage.project.system.advert.service.IAdvertStrategyService;
import com.manage.project.system.advert.vo.EditAdvertDeviceVo;
import com.manage.project.system.vending.domain.VendingUpgrade;
import com.manage.project.system.vending.domain.VendingUpgradeTask;
import com.manage.project.system.vending.service.IVendingUpgradeService;
import com.manage.project.system.vending.service.IVendingUpgradeTaskService;

/**
 * 广告任务推送
 * 
 * @author zhangjiawei
 *
 */
@Service
@EnableScheduling
public class AdvertTaskService {
	
	@Autowired
	private IAdvertConfigService advertConfigService;
	
	@Autowired
	private AdvertConfigMapper advertConfigMapper;
	
	@Autowired
	private IAdvertDeviceService advertDeviceService;
	
	@Autowired
	private AdvertDeviceMapper advertDeviceMapper; 
	
	@Autowired
	private IAdvertStrategyService advertStrategyService;
	
	
	private Logger log = LoggerFactory.getLogger(AdvertTaskService.class);

	/**
	 * 推送升级任务
	 */
	@Scheduled(cron ="0 0/1 * * * ?")
	public void pushCmd() {
		log.info("推送广告任务:"+DateUtils.getTime());
		//查询时间到了的延迟升级任务
		AdvertConfig advertConfig = new AdvertConfig();
		advertConfig.setDeliveryType(Constant.ADVERT_DILIVERYTYPE_LATER);
		advertConfig.setLazyTime(DateUtils.getTime());
		advertConfig.setCurState(Constant.ADVERT_CUS_STATE_WAIT);
		List<AdvertConfig> list = advertConfigService.selectNotExcutingAdvertConfigList(advertConfig);
		//查询等待执行的立即执行任务
		advertConfig.setDeliveryType(Constant.ADVERT_DILIVERYTYPE_NOW);
		advertConfig.setCurState(Constant.ADVERT_CUS_STATE_WAIT);
		List<AdvertConfig> advertConfigList = advertConfigService.selectAdvertConfigList(advertConfig);
		list.addAll(advertConfigList);
		for (AdvertConfig config : list) {
			List<EditAdvertDeviceVo> advertDeviceList= advertDeviceService.selectAdvertDeviceByAdvertId(config.getAdvertId());
			for (EditAdvertDeviceVo editAdvertDeviceVo : advertDeviceList) {
				//如果是全量下发,失效执行中的广告
				if(Constant.ADVERT_OPER_TYPE_FULL.equals(config.getOperType())) {
					advertConfigService.invalidAdvertDeviceCoverExist(editAdvertDeviceVo.getDeviceId(), config.getAdvertId());
				}
				advertConfigService.insertVendingCmd(editAdvertDeviceVo.getDeviceId(),config.getAdvertId(),editAdvertDeviceVo.getAdvDeviceId(),config.getCorpId());
			}
		}
	}
	
	/**
	 * 每半个小时定时失效对应广告
	 * 
	 */
	@Scheduled(cron ="0 0/5 * * * ?")
	public void invalidAdvertByTime() {
		log.info("失效广告任务:"+DateUtils.getTime());
		//查询未失效的广告
		AdvertConfig advertConfigSelect = new AdvertConfig();
		advertConfigSelect.setCurState(Constant.ADVERT_CUS_STATE_EXCUTING);
		List<AdvertConfig> advertConfigList = advertConfigService.selectNotInvalidAdvertConfigList(advertConfigSelect);
		List<AdvertConfig> invalidAdvertList = new ArrayList<AdvertConfig>();
		for (AdvertConfig advertConfig : advertConfigList) {
			//查询广告配置的所有广告策略
			String advertId = advertConfig.getAdvertId();
			//设备全部失效标志
			boolean invalidDeviceflag=true;
			//广告策略全部超时
			boolean invalidStrategyflag=true;
			//查询所有下发的售货机是否已经全部失效了
			List<EditAdvertDeviceVo> advertDeviceList = advertDeviceService.selectAdvertDeviceByAdvertId(advertId);
			for (EditAdvertDeviceVo advertDeviceVo : advertDeviceList) {
				String curState = advertDeviceVo.getCurState();
				if(!Constant.ADVERT_CUS_STATE_INVALID.equals(curState)&&!Constant.ADVERT_CUS_STATE_DELETE.equals(curState)) {
					invalidDeviceflag=false;
					break;
				}
			}
			if(invalidDeviceflag) {
				invalidAdvertList.add(advertConfig);
				continue;
			}
			List<AdvertStrategy> advertStrategyList = advertStrategyService.selectAdvertStrategyByAdvertId(advertId);
			//只有当所有广告策略都是特定时间,并且时间超出的情况下,才可以失效广告
			for (AdvertStrategy advertStrategy : advertStrategyList) {
				if("1".equals(advertStrategy.getStrategyType())) {
					invalidStrategyflag=false;
					break;
				}else {
					String endTime = advertStrategy.getPlayEtime();
					if(endTime.compareTo(DateUtils.getTime())>0) {
						invalidStrategyflag=false;
						break;
					}
				}
			}
			if(invalidStrategyflag) {
				invalidAdvertList.add(advertConfig);
				continue;
			}
		}
		//对需要失效的广告失效
		for (AdvertConfig advertConfig : invalidAdvertList) {
			advertConfig.setCurState(Constant.ADVERT_CUS_STATE_INVALID);
			advertConfigMapper.updateAdvertConfig(advertConfig);
			advertDeviceMapper.invalidAdvertDeviceByAdvertIdWithFlag(advertConfig.getAdvertId());
		}
	}
}
