package com.manage.project.system.base.controller;

import java.lang.reflect.InvocationTargetException;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
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
import com.manage.project.system.base.service.ICorpService;
import com.manage.project.system.base.service.IPayconfigAlipayService;
import com.manage.project.system.base.service.IPayconfigWechatService;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.server.domain.ServerMessage;
import com.manage.project.system.server.service.IServerService;
import com.manage.project.system.statement.service.IOrderApplyService;
import com.manage.project.system.statement.vo.OrderApplyVo;
import com.manage.project.system.statement.vo.OrderVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 支付宝支付配置 信息操作处理
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/payconfigAlipay")
public class PayconfigAlipayController extends BaseController
{
	private static final Logger log = LoggerFactory.getLogger(PayconfigAlipayController.class);
	
	@Autowired
	private IPayconfigAlipayService payconfigAlipayService;
	
	@Autowired
	private IServerService serverService;
	
	@Autowired
	private IOrderApplyService orderApplyService;
	
	@Autowired
	private ICorpService corpService;
	
	@Autowired
	private ManageConfig manageConfig;
	
	
	/**
	 * 查询支付宝支付配置列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(PayconfigAlipay payconfigAlipay)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			payconfigAlipay.setCorpId("");
		} else {
			payconfigAlipay.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<PayconfigAlipay> list = payconfigAlipayService.selectPayconfigAlipayList(payconfigAlipay);
		return getDataTable(list);
	}
	
	/**
	 * 查询支付宝支付配置
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail()
	{	
		ServerMessage message = null;
		try {
			message = serverService.selectPayConfig(ShiroUtils.getCorpId(), "01");
			Object payInfo = message.getZbody("PayInfo");			
			JSONObject payInfoMap = null;
			if(payInfo instanceof JSONObject) {
				payInfoMap = (JSONObject)payInfo;
			}
			String testState = payInfoMap.getString("TestState");
			JSONObject alipayInfo = payInfoMap.getJSONObject("AlipayInfo");
			AliPayConfig aliPayConfig = new AliPayConfig();
			if(alipayInfo!=null) {
				aliPayConfig.setAppId(alipayInfo.getString("AppId"));
				aliPayConfig.setPubKey(alipayInfo.getString("PubKey"));
				aliPayConfig.setPrivKey(alipayInfo.getString("PrivKey"));
				aliPayConfig.setSignType(alipayInfo.getString("SignType"));
				aliPayConfig.setNotifyUrl(alipayInfo.getString("NotifyUrl"));
				aliPayConfig.setTestState(testState);
				return AjaxResult.success(aliPayConfig);
			}else {
				log.error("查询支付配置失败:"+message.toJson()+"时间:"+DateUtils.getTime());
				return AjaxResult.success();
			}
		}catch (Exception e) {
			if(message!=null) {
				log.error("查询支付配置失败:"+message.toJson()+"时间:"+DateUtils.getTime(),e);
			}else {
				log.error("查询支付配置失败:"+"时间:"+DateUtils.getTime(),e);
			}
			return AjaxResult.success();
		}
		
	}
	
	/**
	 * 修改或新增支付宝支付配置
	 */
	@Log(title = "支付宝支付配置", action = BusinessType.UPDATE)
	@PostMapping("/saveOrUpdate")
	@ResponseBody
	public AjaxResult saveOrUpdate(@RequestBody AliPayConfig aliPayConfig)
	{	
		aliPayConfig.setCorpId(ShiroUtils.getCorpId());
		aliPayConfig.setCorpName(ShiroUtils.getCorpId());
		aliPayConfig.setNotifyUrl(manageConfig.getAliNotifyUrl());
		//Corp corp = corpService.selectCorpById(ShiroUtils.getCorpId());
//		if(corp!=null) {
//			aliPayConfig.setCorpName(corp.getCorpName());
//		}else {
//			return AjaxResult.error("当前公司不存在");
//		}
		try {
			//查询当前支付配置是否存在
			aliPayConfig.setPayType("01");
			if("1".equals(aliPayConfig.getSignType())) {
				aliPayConfig.setSignType("RSA");
			}else if("2".equals(aliPayConfig.getSignType())) {
				aliPayConfig.setSignType("RSA2");
			}
			ServerMessage selectMessage = serverService.selectPayConfig(ShiroUtils.getCorpId(), "01");
			ServerMessage message = null;
			if(selectMessage.success()) {
				message = serverService.aliPayConfigSave(aliPayConfig,Constant.PAYCONFIG_UPDATE);
			}else {
				message = serverService.aliPayConfigSave(aliPayConfig,Constant.PAYCONFIG_INSERT);
			}
			
			if(message.success()) {
				return AjaxResult.success();
			}else {
				log.error("保存支付配置失败:"+message.toJson()+"时间:"+DateUtils.getTime());
				return  AjaxResult.error("保存失败");
			}
		}catch (Exception e) {
			log.error("保存支付配置失败:"+DateUtils.getTime(),e);
			return  AjaxResult.error("保存失败");
		}
		
	}
	
	/**
	 * 修改保存支付宝支付配置
	 */
	@Log(title = "支付宝支付配置", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(PayconfigAlipay payconfigAlipay)
	{	
		return toAjax(payconfigAlipayService.updatePayconfigAlipay(payconfigAlipay));
	}
	
	/**
	 * 删除支付宝支付配置
	 */
	@Log(title = "支付宝支付配置", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(payconfigAlipayService.deletePayconfigAlipayByIds(ids));
	}
	
	
	/**
	 * 支付宝订单支付
	 */
	@PostMapping("/orderPay")
	@ResponseBody
	public AjaxResult orderPay(@RequestBody String orderIds)
	{
		List<Map<String, String>> alipayInfo = new ArrayList<Map<String,String>>();
		HashMap<String, String> alipayInfoMap = new HashMap<String,String>(); 
		alipayInfoMap.put("OpenId", "123");
		alipayInfo.add(alipayInfoMap);
		String[] orders = orderIds.split(",");
		ServerMessage message = serverService.orderPay1007(orders, "01",null, alipayInfo);
		if(message.success()) {
			return AjaxResult.success(message.getZbody());
		}else {
			return AjaxResult.error("支付失败");
		}
	}
	
}
