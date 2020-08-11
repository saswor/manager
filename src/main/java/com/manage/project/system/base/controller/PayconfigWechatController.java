package com.manage.project.system.base.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.google.common.io.Files;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.project.common.Constant;
import com.manage.project.system.base.domain.AliPayConfig;
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.base.domain.PayconfigAlipay;
import com.manage.project.system.base.domain.PayconfigWechat;
import com.manage.project.system.base.domain.WechatPayConfig;
import com.manage.project.system.base.service.ICorpService;
import com.manage.project.system.base.service.IPayconfigWechatService;
import com.manage.project.system.server.domain.ServerMessage;
import com.manage.project.system.server.service.IServerService;
import com.manage.project.system.statement.service.IOrderApplyService;
import com.manage.project.system.statement.vo.OrderVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 微信支付配置 信息操作处理
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/payconfigWechat")
public class PayconfigWechatController extends BaseController
{
	private static final Logger log = LoggerFactory.getLogger(PayconfigWechatController.class);

	@Autowired
	private IPayconfigWechatService payconfigWechatService;
	
	@Autowired
	private IServerService serverService;
	
	@Autowired
	private IOrderApplyService orderApplyService;
	
	@Autowired
	private ICorpService corpService;
	
	@Autowired
	private ManageConfig manageConfig;
	
	/**
	 * 查询微信支付配置列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(PayconfigWechat payconfigWechat)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			payconfigWechat.setCorpId("");
		} else {
			payconfigWechat.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<PayconfigWechat> list = payconfigWechatService.selectPayconfigWechatList(payconfigWechat);
		return getDataTable(list);
	}
	

	/**
	 * 查询微信支付配置
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(String payType) {
		ServerMessage message=null;
		WechatPayConfig wechatPayConfig = new WechatPayConfig();
		wechatPayConfig.setPayType(payType);
		try {
			message = serverService.selectPayConfig(ShiroUtils.getCorpId(), payType);
			Object payInfo = message.getZbody("PayInfo");			
			JSONObject payInfoMap = null;
			if(payInfo instanceof JSONObject) {
				payInfoMap = (JSONObject)payInfo;
			}
			String testState = payInfoMap.getString("TestState");
			JSONObject weChatInfo = payInfoMap.getJSONObject("WeChatInfo");
			
			if(weChatInfo!=null) {
				wechatPayConfig.setAppId(weChatInfo.getString("AppId"));
				wechatPayConfig.setApiSercret(weChatInfo.getString("ApiSercret"));
				wechatPayConfig.setMchId(weChatInfo.getString("MchId"));
				if("null".equals(weChatInfo.getString("SubMchId"))) {
					wechatPayConfig.setSubMchId(weChatInfo.getString(""));
				}else {
					wechatPayConfig.setSubMchId(weChatInfo.getString("SubMchId"));
				}
				wechatPayConfig.setCert(weChatInfo.getString("Cert"));
				wechatPayConfig.setNotifyUrl(weChatInfo.getString("NotifyUrl"));
				wechatPayConfig.setTestState(testState);
				return AjaxResult.success(wechatPayConfig);
			}else {
				return AjaxResult.success(wechatPayConfig);
			}
		}catch (Exception e) {
			if(message!=null) {
				log.error("查询支付配置失败:"+message.toJson()+"时间:"+DateUtils.getTime(),e);
			}else {
				log.error("查询支付配置失败:"+"时间:"+DateUtils.getTime(),e);
			}
			
			return AjaxResult.success(wechatPayConfig);
		}

	}
	
	/**
	 * 新增或修改微信支付配置
	 */
	@Log(title = "微信支付配置", action = BusinessType.UPDATE)
	@PostMapping("/saveOrUpdate")
	@ResponseBody
	public AjaxResult saveOrUpdate(@RequestBody WechatPayConfig wechatPayConfig)
	{
		String payType = wechatPayConfig.getPayType();
		if(StringUtils.isEmpty(payType)) {
			return  AjaxResult.error("支付类型不能为空"); 
		}
		//保存微信证书
		String cert = wechatPayConfig.getCert();
		String path="front/license/"+ ShiroUtils.getCorpId()+"_weChat_"+payType+".p12";
		File src = new File(ManageConfig.getUploadPrefix()+cert);
		if(!src.exists()) {
			return  AjaxResult.error("证书文件不存在"); 
		}
		File file = new File(ManageConfig.getUploadPrefix()+path);
		File dir = file.getParentFile();
		if(!dir.exists()) {
			dir.mkdirs();
		}
		if(file.exists()) {
			file.delete();
		}
		try {
			Files.copy(src, file);
			wechatPayConfig.setCert(manageConfig.getIp()+"/"+path);
			ServerMessage message =null;
			wechatPayConfig.setCorpId(ShiroUtils.getCorpId());
//			Corp corp = corpService.selectCorpById(ShiroUtils.getCorpId());
//			if(corp!=null) {
//				wechatPayConfig.setCorpName(corp.getCorpName());
//			}else {
//				return AjaxResult.error("当前公司不存在");
//			}
			wechatPayConfig.setCorpName(ShiroUtils.getCorpId());
			//wechatPayConfig.setPayType("02");
			wechatPayConfig.setNotifyUrl(manageConfig.getWechatNotifyUrl());
			ServerMessage selectMessage = serverService.selectPayConfig(ShiroUtils.getCorpId(), wechatPayConfig.getPayType());
			if(selectMessage==null) {
				return  AjaxResult.error("保存失败");
			}else if(selectMessage.success()) {
				message = serverService.wechatPayConfigSave(wechatPayConfig,Constant.PAYCONFIG_UPDATE);
			}else {
				message = serverService.wechatPayConfigSave(wechatPayConfig,Constant.PAYCONFIG_INSERT);
			}
			if(message==null) {
				return  AjaxResult.error("保存失败");
			}else if(message.success()) {
				return AjaxResult.success();
			}else {
				return  AjaxResult.error("保存失败");
			}
		} catch (Exception e) {
			log.error("保存微信配置失败,时间:"+DateUtils.getTime(),e);
			return  AjaxResult.error("保存失败");
		}

	}
	
	/**
	 * 修改保存微信支付配置
	 */
	@RequiresPermissions("module:payconfigWechat:edit")
	@Log(title = "微信支付配置", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(PayconfigWechat payconfigWechat)
	{		
		return toAjax(payconfigWechatService.updatePayconfigWechat(payconfigWechat));
	}
	
	/**
	 * 删除微信支付配置
	 */
	@Log(title = "微信支付配置", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(payconfigWechatService.deletePayconfigWechatByIds(ids));
	}
	
	
	/**
	 * 微信订单支付
	 */
	@PostMapping("/orderPay")
	@ResponseBody
	public AjaxResult orderPay(@RequestBody String orderIds)
	{
		List<Map<String, String>> wechatPayInfo = new ArrayList<Map<String,String>>();
		HashMap<String, String> wechatPayInfoMap = new HashMap<String,String>(); 
		wechatPayInfoMap.put("OpenId", "123");
		wechatPayInfo.add(wechatPayInfoMap);
		String[] orders = orderIds.split(",");
		ServerMessage message = serverService.orderPay1007(orders, "01",wechatPayInfo, null);
		if(message.success()) {
			return AjaxResult.success(message.getZbody());
		}else {
			return AjaxResult.error("支付失败");
		}
	}
	
}
