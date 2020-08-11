package com.manage.project.system.advert.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.manage.common.exception.CmdNoticeDeviceFailedException;
import com.manage.common.exception.advert.AdvertNotInvalidException;
import com.manage.common.exception.advert.AdvertPlayTimeIsNull;
import com.manage.common.exception.advert.AdvertPlayTimeLessThanZero;
import com.manage.common.exception.advert.AdvertPlayTimesIsNull;
import com.manage.common.exception.advert.AdvertPlayTimesLessThanZero;
import com.manage.common.exception.advert.AdvertStrategyPlayETimeEmptyException;
import com.manage.common.exception.advert.AdvertStrategyPlaySTimeEarlyThanNowException;
import com.manage.common.exception.advert.AdvertStrategyPlaySTimeEmptyException;
import com.manage.common.exception.advert.AdvertStrategyPlaySTimeLaterThanPlayETimeException;
import com.manage.common.exception.advert.AdvertStrategyPointNameNotExit;
import com.manage.common.exception.advert.AdvertStrategyTypeIsEmptyException;
import com.manage.common.exception.advert.MstrategyNotExitException;
import com.manage.common.exception.advert.StrategyNotExistExceprion;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.bean.BeanUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.config.ManageConfig;
import com.manage.project.common.Constant;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.advert.domain.AdvertConfig;
import com.manage.project.system.advert.domain.AdvertMstrategy;
import com.manage.project.system.advert.domain.AdvertStrategy;
import com.manage.project.system.advert.service.AdvertConfigServiceImpl;
import com.manage.project.system.advert.service.IAdvertConfigService;
import com.manage.project.system.advert.service.IAdvertDeviceService;
import com.manage.project.system.advert.service.IAdvertMstrategyService;
import com.manage.project.system.advert.service.IAdvertStrategyService;
import com.manage.project.system.advert.vo.AdvertEditVo;
import com.manage.project.system.advert.vo.AdvertPlat;
import com.manage.project.system.advert.vo.AdvertSaveVo;
import com.manage.project.system.advert.vo.AdvertViewVo;
import com.manage.project.system.advert.vo.AdvertViewVo.AdvMstrategy;
import com.manage.project.system.advert.vo.AdvertViewVo.AdvStrategy;
import com.manage.project.system.advert.vo.EditAdvertDeviceVo;
import com.manage.project.system.advert.vo.Mstrategy;
import com.manage.project.system.advert.vo.Strategy;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 广告配置 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/advertConfig")
public class AdvertConfigController extends BaseController
{
	
	private Logger log = LoggerFactory.getLogger(AdvertConfigController.class);
	
	@Autowired
	private IAdvertConfigService advertConfigService;
	
	@Autowired
	private IAdvertStrategyService advertStrategyService;
	
	@Autowired
	private IAdvertMstrategyService advertMstrategyService;
	
	@Autowired
	private IAdvertDeviceService advertDeviceService;
	
	@Autowired
	private ManageConfig manageConfig;
	
	/**
	 * 查询广告配置列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(AdvertConfig advertConfig)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			advertConfig.setCorpId("");
		} else {
			advertConfig.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<AdvertConfig> list = advertConfigService.selectAdvertConfigList(advertConfig);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 编辑页面,查询广告信息
	 * @param advertId	广告id
	 * @param type	类型:1.查询 2编辑
	 * @return
	 */
	@GetMapping("/getEditAdv")
	@ResponseBody
	public AjaxResult getEditAdv(String advertId,String type) {
		AdvertEditVo result = new AdvertEditVo();
		AdvertConfig ac = advertConfigService.selectAdvertConfigByAdvertId(advertId);
		if (ac == null) {
			return AjaxResult.error("广告不存在");
		}
		//如果是编辑,判断广告是否已经执行
		if("2".equals(type)) {
			String curState = ac.getCurState();
			if(!Constant.ADVERT_CUS_STATE_WAIT.equals(curState)) {
				return AjaxResult.error("广告已经下发,不能编辑");
			}
		}
		//判断广告是否已经执行,
		BeanUtils.copyBeanProp(result, ac);	// 得到广告基本信息
		
		// 得到广告策略
        List<AdvertStrategy> list = advertStrategyService.selectAdvertStrategyByAdvertId(advertId);
        TreeMap<String, AdvertPlat> treeMap = new TreeMap<String,AdvertPlat>();
        if (list != null && !list.isEmpty()) {    
        	/** 策略  */
        	List<Strategy> strategies = new ArrayList<Strategy>();
        	for (AdvertStrategy as : list) {        		
        		Strategy strategy = new Strategy();
        		BeanUtils.copyBeanProp(strategy, as);	
        		// 组装策略拥有的素材
        		List<AdvertMstrategy> advMstrategies = advertMstrategyService.selectAdvertMstrategyByStrategyId(as.getStrategyId());
        		
        		if (advMstrategies != null && !advMstrategies.isEmpty()) {
        			/** 素材  */
            		List<Mstrategy> mstrategies = new ArrayList<Mstrategy>();
        			for (AdvertMstrategy advertMstrategy : advMstrategies) {
        				Mstrategy mstrategy = new Mstrategy();
        				BeanUtils.copyBeanProp(mstrategy, advertMstrategy);
        				//文本和视频类型统一返回一个地址
        				if(Constant.MEDIA_TYPE_TEXT.equals(advertMstrategy.getMediaType())) {
        					mstrategy.setMediaUrl(manageConfig.getIp()+Constant.TEXT_ICON_ADDRESS);
        				}
        				if(Constant.MEDIA_TYPE_VIDEO.equals(advertMstrategy.getMediaType())) {
        					mstrategy.setMediaUrl(manageConfig.getIp()+Constant.VIDEO_ICON_ADDRESS);
        				}
        				mstrategy.setMediaTypeName(SystemUtil.parseMediaType(advertMstrategy.getMediaType()));
        				mstrategies.add(mstrategy);
        			}
        			strategy.setMstrategies(mstrategies);
        		}
        		String strategyPoint = strategy.getStrategyPoint();
        		if("0101".equals(strategyPoint)) {
        			strategies.add(strategy);
        		}else {
        			AdvertPlat plat = treeMap.get(strategyPoint);
        			if(plat==null) {
            			AdvertPlat advertPlat = new AdvertPlat();
            			advertPlat.setStrategyPoint(strategy.getStrategyPoint());
            			advertPlat.setStrategyPointName(strategy.getStrategyPointName());
            			advertPlat.getStrategies().add(strategy);
            			treeMap.put(strategyPoint, advertPlat);
            		}else {
            			plat.getStrategies().add(strategy);
            		}
            		
        		}
        	}
        	result.setStrategies(strategies);
        }
        for (Entry<String, AdvertPlat> entry: treeMap.entrySet()) {
            AdvertPlat advertPlat = entry.getValue();
            result.getAdvertPlats().add(advertPlat);
        }
        List<EditAdvertDeviceVo> ads = advertDeviceService.selectAdvertDeviceByAdvertId(advertId);
        result.setAdevices(ads);
        return AjaxResult.success(result);
	}
	
	@GetMapping("/get")
	@ResponseBody
	public AjaxResult get(String advertId)
	{
		if (StringUtils.isEmpty(advertId)) {
			return AjaxResult.error("advertId不能为空。");
		}
		// 查询广告基础信息
		AdvertViewVo avv = new AdvertViewVo();
		AdvertConfig advertConfig = advertConfigService.selectAdvertConfigByAdvertId(advertId);
		avv.setName(advertConfig.getName());
		if (StringUtils.isEmpty(advertConfig.getCurState())) {
			avv.setCurStateName("");
		} else {
			if (advertConfig.getCurState().equals("1")) {
				avv.setCurStateName("未投放");
			} else if (advertConfig.getCurState().equals("2")) {
				avv.setCurStateName("正在投放");
			} else if (advertConfig.getCurState().equals("3")) {
				avv.setCurStateName("已投放");
			}
		}
		// 查询广告策略		
        List<AdvertStrategy> list = advertStrategyService.selectAdvertStrategyByAdvertId(advertId);
        if (list != null && !list.isEmpty()) {    
        	List<AdvStrategy> advStrategies = new ArrayList<AdvStrategy>();
        	for (AdvertStrategy as : list) {        		
        		AdvStrategy advStrategy = avv.new AdvStrategy();
        		advStrategy.setStrategyPointName(SystemUtil.parseStrategyPoint(as.getStrategyPoint()));
        		StringBuffer sbf = new StringBuffer();
        		if (as.getStrategyType() != null) {
        			if (as.getStrategyType().equals("1")) {
        				sbf.append("每天 ").append(as.getPlaySTime()).append("-").append(as.getPlayEtime());
        			} else if (as.getStrategyType().equals("2")) {
        			sbf.append("特定时间 ").append(as.getPlaySTime()).append("-").append(as.getPlayEtime());
        			}
        		}
        		advStrategy.setStrateStr(sbf.toString());
        		
        		// 组装策略拥有的素材
        		List<AdvertMstrategy> advMstrategies = advertMstrategyService.selectAdvertMstrategyByStrategyId(as.getStrategyId());
        		if (advMstrategies != null && !advMstrategies.isEmpty()) {
        			List<AdvMstrategy> advms = new ArrayList<AdvMstrategy>();
        			for (AdvertMstrategy advertMstrategy : advMstrategies) {
        				AdvMstrategy advm = avv.new AdvMstrategy();
        				advm.setMediaName(advertMstrategy.getMediaName());
        				advm.setMediaTypeName(SystemUtil.parseMediaType(advertMstrategy.getMediaType()));
        				advm.setMediaUrl(advertMstrategy.getMediaUrl());
        				//文本和视频类型统一返回一个地址
        				if(Constant.MEDIA_TYPE_TEXT.equals(advertMstrategy.getMediaType())) {
        					advm.setMediaUrl(manageConfig.getIp()+Constant.TEXT_ICON_ADDRESS);
        				}
        				if(Constant.MEDIA_TYPE_VIDEO.equals(advertMstrategy.getMediaType())) {
        					advm.setMediaUrl(manageConfig.getIp()+Constant.VIDEO_ICON_ADDRESS);
        				}
        				advms.add(advm);
        			}
        			advStrategy.setAdvMstrategies(advms);// 给每个策略拥有的素材
        		}
        		advStrategies.add(advStrategy);
        	}
        	avv.setAdvStrategies(advStrategies);	// 将策略给广告信息
        }        
        
		return AjaxResult.success(avv);
	}
	
	/**
	 * 新增保存广告配置
	 */
	@Log(title = "广告配置", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody AdvertSaveVo advertSaveVo)
	{		
		try {
			int result = advertConfigService.insertAdvertConfig(advertSaveVo);
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("广告配置已经下发到终端,无法修改");
			}else if(result==3) {
				return AjaxResult.error("广告配置名称不能为空");
			}else if(result==4) {
				return AjaxResult.error("广告配置名称已存在");
			}else if(result==5) {
				return AjaxResult.error("投放类型不能为空");
			}else if(result==6) {
				return AjaxResult.error("投放平台不能为空");
			}else if(result==7) {
				return AjaxResult.error("操作类型不能为空");
			}else if(result==8) {
				return AjaxResult.error("策略不能为空");
			}else if(result==9) {
				return AjaxResult.error("下发的售货机不能为空");
			}else if(result==10) {
				return AjaxResult.error("延迟投放延迟时间不能为空");
			}else if(result==11) {
				return AjaxResult.error("素材不能为空");
			}else if(result==12) {
				return AjaxResult.error("首页主广告位广告策略不能为空");
			}else if(result==13) {
				return AjaxResult.error("延迟投放延迟时间不能早于当前时间");
			}else {
				return AjaxResult.error("保存失败");
			}
		}catch (AdvertStrategyPointNameNotExit e) {
			return AjaxResult.error("平台广告位名称不能为空");
		}catch (MstrategyNotExitException e) {
			return AjaxResult.error("当前广告策略素材不能为空");
		}catch (AdvertPlayTimesIsNull e) {
			return AjaxResult.error("素材播放次数不能为空");
		}catch (AdvertPlayTimesLessThanZero e) {
			return AjaxResult.error("素材播放次数不能小于等于0");
		}catch (AdvertPlayTimeIsNull e) {
			return AjaxResult.error("素材播放时间不能为空");
		}catch (AdvertPlayTimeLessThanZero e) {
			return AjaxResult.error("素材播放时间不能小于等于0");
		}catch (CmdNoticeDeviceFailedException e) {
			return AjaxResult.error("命令下发终端失败");
		}catch (AdvertStrategyTypeIsEmptyException e) {
			return AjaxResult.error("策略类型不能为空");
		}catch (StrategyNotExistExceprion e) {
			return AjaxResult.error("平台广告位的策略不能为空");
		}catch (AdvertStrategyPlaySTimeEmptyException e) {
			return AjaxResult.error("策略开始时间不能为空");
		}catch (AdvertStrategyPlayETimeEmptyException e) {
			return AjaxResult.error("策略结束时间不能为空");
		}catch (AdvertStrategyPlaySTimeLaterThanPlayETimeException e) {
			return AjaxResult.error("策略开始时间不能晚于结束时间");
		}catch (AdvertStrategyPlaySTimeEarlyThanNowException e) {
			return AjaxResult.error("策略开始时间不能比当前时间早");
		}catch (Exception e) {
			log.error("新增广告配置失败,时间:"+DateUtils.getTime()+",广告配置:"+JSONObject.toJSONString(advertSaveVo),e);
			return AjaxResult.error("保存失败");
		}
	}
	
	/**
	 * 修改保存广告配置
	 */
	@Log(title = "广告配置", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody AdvertSaveVo advertSaveVo)
	{	
		try {
			int result = advertConfigService.updateAdvertConfig(advertSaveVo);
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("广告配置已经下发到终端,无法修改");
			}else if(result==3) {
				return AjaxResult.error("广告配置名称不能为空");
			}else if(result==4) {
				return AjaxResult.error("广告配置名称已存在");
			}else if(result==5) {
				return AjaxResult.error("投放类型不能为空");
			}else if(result==6) {
				return AjaxResult.error("投放平台不能为空");
			}else if(result==7) {
				return AjaxResult.error("操作类型不能为空");
			}else if(result==8) {
				return AjaxResult.error("策略不能为空");
			}else if(result==9) {
				return AjaxResult.error("下发的售货机不能为空");
			}else if(result==10) {
				return AjaxResult.error("延迟投放延迟时间不能为空");
			}else if(result==11) {
				return AjaxResult.error("素材不能为空");
			}else if(result==12) {
				return AjaxResult.error("首页主广告位广告策略不能为空");
			}else if(result==13) {
				return AjaxResult.error("延迟投放延迟时间不能早于当前时间");
			}else {
				return AjaxResult.error("保存失败");
			}
		}catch (AdvertStrategyPointNameNotExit e) {
			return AjaxResult.error("平台广告位名称不能为空");
		}catch (MstrategyNotExitException e) {
			return AjaxResult.error("当前广告策略素材不能为空");
		}catch (AdvertPlayTimesIsNull e) {
			return AjaxResult.error("素材播放次数不能为空");
		}catch (AdvertPlayTimesLessThanZero e) {
			return AjaxResult.error("素材播放次数不能小于等于0");
		}catch (AdvertPlayTimeIsNull e) {
			return AjaxResult.error("素材播放时间不能为空");
		}catch (AdvertPlayTimeLessThanZero e) {
			return AjaxResult.error("素材播放时间不能小于等于0");
		}catch (CmdNoticeDeviceFailedException e) {
			return AjaxResult.error("命令下发终端失败");
		}catch (AdvertStrategyTypeIsEmptyException e) {
			return AjaxResult.error("策略类型不能为空");
		}catch (StrategyNotExistExceprion e) {
			return AjaxResult.error("平台广告位的策略不能为空");
		}catch (AdvertStrategyPlaySTimeEmptyException e) {
			return AjaxResult.error("策略开始时间不能为空");
		}catch (AdvertStrategyPlayETimeEmptyException e) {
			return AjaxResult.error("策略结束时间不能为空");
		}catch (AdvertStrategyPlaySTimeLaterThanPlayETimeException e) {
			return AjaxResult.error("策略开始时间不能晚于结束时间");
		}catch (AdvertStrategyPlaySTimeEarlyThanNowException e) {
			return AjaxResult.error("策略开始时间不能比当前时间早");
		}catch (Exception e) {
			log.error("编辑广告配置失败,时间:"+DateUtils.getTime()+",广告配置:"+JSONObject.toJSONString(advertSaveVo),e);
			return AjaxResult.error("保存失败");
		}
	}
	
	/**
	 * 广告失效
	 */
	@Log(title = "广告配置", action = BusinessType.UPDATE)
	@PostMapping("/invalid")
	@ResponseBody
	public AjaxResult invalid(@RequestBody CommonVo ids)
	{		
		try {
			int result = advertConfigService.invalid(ids.getIds());
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("广告已失效,请不要重复执行");
			}else if(result==3) {
				return AjaxResult.error("该广告已经删除,不能失效");
			}else {
				return AjaxResult.error("操作失败");
			}
		}catch (CmdNoticeDeviceFailedException e) {
			return AjaxResult.error("失效命令下发失败失败");
		}catch (Exception e) {
			log.error("失效广告配置失败,时间:"+DateUtils.getTime()+",广告配置编号:"+ids.getIds(),e);
			return AjaxResult.error("操作失败");
		}
		
	}
	
	/**
	 * 删除广告配置
	 */
	@Log(title = "广告配置", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{
		try {
			int result = advertConfigService.deleteAdvertConfigByIds(ids.getIds());
			if(result==1) {
				return AjaxResult.success();
			}else {
				return AjaxResult.error("操作失败");
			}
		}catch (AdvertNotInvalidException e) {
			return AjaxResult.error("只有失效状态的广告可以删除");
		}catch (Exception e) {
			log.error("删除广告配置失败,时间:"+DateUtils.getTime()+",广告配置编号:"+ids.getIds(),e);
			return AjaxResult.error("操作失败");
		}
	}
	
}
