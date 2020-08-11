package com.manage.project.system.base.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.CacheUtils;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.common.Constant;
import com.manage.project.system.base.PayConfigWsHandler;
import com.manage.project.system.base.domain.PayconfigAlipay;
import com.manage.project.system.base.domain.PayconfigWechat;
import com.manage.project.system.base.service.IPayconfigAlipayService;
import com.manage.project.system.base.service.IPayconfigWechatService;
import com.manage.project.system.index.IndexWsHandler;
import com.manage.project.system.server.domain.ServerMessage;
import com.manage.project.system.server.service.IServerService;
//import com.manage.project.system.job.IndexJobBean;
import com.manage.project.system.util.SystemUtil;

/**
 * 支付配置接收订单支付结果
 * 
 * @author 张佳伟
 *
 */
@Controller
@RequestMapping("/system/payConfig")
public class PayConfigController {
	
	private static final Logger log = LoggerFactory.getLogger(PayConfigController.class);
	
	@Autowired
	private IPayconfigAlipayService payconfigAlipayService;
	
	@Autowired
	private IPayconfigWechatService payconfigWechatService;
	
	@Autowired
	private IServerService serverService;
	
	@Autowired
	private ManageConfig manageConfig;

	/**
	 * 接收支付状态
	 * 
	 * @param orderId 订单编号
	 * @param loginId 登录名
	 * @param corpId 公司编号
	 * @param payState 支付状态
	 * @param payType 支付类型
	 * @param payMoney 支付金额
	 */
	@GetMapping("/receivePayResult")
	@ResponseBody
	public String receivePayResult(String orderId,String loginId,String corpId,String payState,String payType,Float payMoney) {
		log.info("向用户推送支付配置测试消息,订单编号:"+orderId+",用户:"+loginId+",支付方式:"+payType+",支付状态:"+payState+",支付金额"+payMoney);
		
		//参数有误
		if(StringUtils.isEmpty(orderId)||StringUtils.isEmpty(loginId)||(!"01".equals(payType)&&!"02".equals(payType))||StringUtils.isEmpty(SystemUtil.parsePayState(payState))||payMoney==null||payMoney<0) {
			return "1000";
		}
		try {
			//修改支付宝测试状态
			if("01".equals(payType)&&"2".equals(payState)) {
				PayconfigAlipay payconfigAlipay = new PayconfigAlipay();
				payconfigAlipay.setCorpId(corpId);
				PayconfigAlipay payconfig = payconfigAlipayService.selectPayconfigAlipay(payconfigAlipay);
				PayconfigAlipay payconfigAlipaySave = new PayconfigAlipay(); 
				payconfigAlipaySave.setTestState("2");
				payconfigAlipaySave.setPayConfigId(payconfig.getPayConfigId());
				payconfigAlipayService.updatePayconfigAlipay(payconfigAlipaySave);
			}
			//修改微信测试状态
			if("02".equals(payType)&&"2".equals(payState)) {
				PayconfigWechat payconfigWechat = new PayconfigWechat();
				payconfigWechat.setCorpId(corpId);
				PayconfigWechat payconfig = payconfigWechatService.selectPayconfigWechat(payconfigWechat);
				PayconfigWechat payconfigWechatSave = new PayconfigWechat();
				payconfigWechatSave.setTestState("2");
				payconfigWechatSave.setPayConfigId(payconfig.getPayConfigId());
				payconfigWechatService.updatePayconfigWechat(payconfigWechatSave);
			}
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("orderId", orderId);
			map.put("payState", SystemUtil.parsePayState(payState));
			map.put("payType", payType);
			map.put("payTypeName", SystemUtil.parsePayType(payType));
			map.put("payMoney", payMoney);
			IndexWsHandler.sendMessageToUser(loginId, JSONObject.toJSONString(map));
		}catch (Exception e) {
			//推送消息失败
			return "1100";
		}
		//成功
		return "0000";
	}
	
	/**
	 * 支付配置测试接口
	 * 
	 * @param payType
	 * @return
	 */
	@GetMapping("/payTest")
	@ResponseBody
	public AjaxResult payTest(String payType) {
		ServerMessage message = null;
		try {
			message = serverService.payConfigTest(payType, ShiroUtils.getCorpId(), 1, manageConfig.getPayTestNotice());
			if(message!=null) {
				if(message.success()) {
					//将订单信息放入缓存
					String orderId = (String)message.getZbody("OrderId");
					Map<String,Object> orderMap = new HashMap<>();
					orderMap.put("orderId", orderId);
					orderMap.put("userId", ShiroUtils.getUserId().toString());
					orderMap.put("payType", payType);
					orderMap.put("payMoney", 0.01);
					CacheUtils.put(Constant.PAY_TEST+orderId,orderMap);
					return AjaxResult.success(message);
				}else {
					log.error("获取测试二维码失败:时间:"+DateUtils.getTime()+","+JSONObject.toJSONString(message));
					return AjaxResult.error("获取二维码失败");
				}
			}else {
				log.error("获取测试二维码失败:时间:"+DateUtils.getTime());
				return AjaxResult.error("获取二维码失败");
			}
		}catch (Exception e) {
			log.error("获取测试二维码失败:时间:"+DateUtils.getTime()+","+JSONObject.toJSONString(message),e);
			return AjaxResult.error("获取二维码失败");
		}
		
	}
	
	@GetMapping("/receiveResult")
	@ResponseBody
	public String receiveResult(String content) {
		String orderId="";
		String payState="";
		String payType="";
		String payMoney="";
		if(StringUtils.isNotEmpty(content)) {
			ServerMessage message = ServerMessage.jsonToMessage(content);
			if(message!=null&&message.success()) {
				orderId=(String)message.getZbody("TradeNo");				
				payMoney=(String)message.getZbody("Price");
				payState=(String)message.getZbody("PayState");
			}
		}
		
		log.info("接收支付平台推送支付配置测试结果:"+content+",时间:"+DateUtils.getTime());
		//参数有误
		if(StringUtils.isEmpty(orderId)||(!"1".equals(payState)&&!"2".equals(payState))) {
			return "1000";
		}
		log.info("接收支付平台推送支付配置测试结果:订单:"+orderId+",支付结果:"+payState+",时间:"+DateUtils.getTime());
		try {
			Map orderMap = (Map)CacheUtils.get(Constant.PAY_TEST+orderId);
			if(orderMap!=null) {
				HashMap<String, Object> map = new HashMap<String,Object>();
				map.put("orderId", orderId);
				map.put("payState", payState);
				String payStateName="";
				if("1".equals(payState)) {
					payStateName="支付成功";
				}
				if("2".equals(payState)) {
					payStateName="支付失败";
				}
				map.put("payStateName", payStateName);
				payType=(String)orderMap.get("payType");
				map.put("payType", payType);
				map.put("payTypeName", SystemUtil.parsePayType(payType));
				map.put("payMoney", payMoney);
				PayConfigWsHandler.sendMessageToUser((String)orderMap.get("userId"), JSONObject.toJSONString(map));
//				IndexWsHandler.sendMessageToUser("39", JSONObject.toJSONString(map));
			}
			
		}catch (Exception e) {
			//推送消息失败
			log.error("推送支付配置结果失败:时间:"+DateUtils.getTime(),e);
		}
		//成功
		return "0000";
		
	}
	
	
	@GetMapping("/returnMoney")
	@ResponseBody
	public AjaxResult returnMoney(String orderId) {
		if(StringUtils.isEmpty(orderId)) {
			return AjaxResult.error("订单不存在");
		}
		ServerMessage message = null;
		try {
			message = serverService.payConfigTestReturnMoney(orderId);
			if(message!=null) {
				if(message.success()) {
					return AjaxResult.success("退款成功");
				}else {
					log.error("退款失败:时间:"+DateUtils.getTime()+","+JSONObject.toJSONString(message));
					return AjaxResult.error("退款失败");
				}
			}else {
				log.error("退款失败:时间:"+DateUtils.getTime());
				return AjaxResult.error("退款失败");
			}
		}catch (Exception e) {
			log.error("退款失败:时间:"+DateUtils.getTime()+","+JSONObject.toJSONString(message),e);
			return AjaxResult.error("退款失败");
		}
		
	}
}
