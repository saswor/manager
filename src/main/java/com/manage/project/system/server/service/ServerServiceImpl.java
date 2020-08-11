/**
 * 
 */
package com.manage.project.system.server.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.http.HttpClientUtils;
import com.manage.common.utils.spring.SpringUtils;
import com.manage.framework.config.ManageConfig;
import com.manage.project.system.base.domain.AliPayConfig;
import com.manage.project.system.base.domain.WechatPayConfig;
import com.manage.project.system.server.domain.ServerMessage;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingCmd;

import org.apache.commons.putils.utils.DateUtil;

/**
 * 调用服务器接口实现类
 * 
 * @author zhangjiawei
 * @date 2018年11月2日
 * 
 */
@Service
public class ServerServiceImpl implements IServerService{

	/**核心服务器url*/
	@Value("${coreServer.url}")
	private String url;
	/**支付平台url*/
	private String payUrl;
	
//	private String appServerUrl = "";
	
	private Logger log = LoggerFactory.getLogger(ServerServiceImpl.class);
	

	public ServerServiceImpl() {
		super();
		BussinessCacheService bussinessCacheService = SpringUtils.getBean(BussinessCacheService.class);
		bussinessCacheService.initDictCache();
		payUrl=SystemUtil.getPayUrl("1");
	}

	/**
	 * 订单取消1004
	 * 
	 * @param orderId 订单id
	 * @return 结果报文
	 */
	@Override
	public ServerMessage orderCancel(String orderId) {
		//设置请求报文
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("1004");
		message.putVersion("01");
		message.putZbody("OrderId", orderId);
		String json = message.toJson();
		return HttpClientUtils.getToServer(url, json);
	}

	/**
	 * 售货机命令推送接口
	 * 
	 * @param siteId 售货机序列号
	 * @param cmdCode 命令对象编号 售货机编号、商品编号、订单编号
	 * @param cmdType 命令类型 01:售货机 02:商品 03: 订单
	 * @param cmd 命令
	 * @param cont 命令内容
	 * @return 结果报文
	 */
	@Override
	public String vendingCommand1227(String siteId,VendingCmd vendingCmd) {
		//设置请求报文
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("1227");
		message.putVersion("01");
		message.putZbody("SiteId", siteId);
		String cmdId = vendingCmd.getCmdId();
		message.putZbody("CmdId", vendingCmd.getCmdId());
		message.putZbody("CmdType", vendingCmd.getCmdType());
		message.putZbody("CmdCode", vendingCmd.getCmdCode());
		message.putZbody("Cmd", vendingCmd.getCmd());
		message.putZbody("Cont", vendingCmd.getCont());
		message.putZbody("CreateTime", DateUtils.getTime());
		String json = message.toJson();
//		return HttpClientUtils.getToServer(SystemUtil.getSysParameter("msgServer_url"), json);
		String param="";
		try {
			String content=URLEncoder.encode(json,"UTF-8");
			param="content="+content+ "&deviceId=" + siteId + "&msgId=" + DateUtil.getLogid();
			return HttpClientUtils.httpGet(SystemUtil.getSysParameter("msgServer_url")+"?", param,false);
		} catch (Exception e) {
			log.error("下发命令1227失败,参数:url:"+SystemUtil.getSysParameter("msgServer_url")+"siteId:"+siteId+",content:"+json,e);
			return null;
		}
		
	}

	/**
	 * 订单申请接口1003
	 * 
	 * @param siteId 售货机编号
	 * @param loginId 登陆编号
	 * @param loginName 客户名称
	 * @param payType 支付类型 (01支付宝 02:微信 03:百度钱包 04:翼支付 05:京东钱包 06:会员支付 07:银联支付 08:聚合支付)
	 * @param productInfo 商品信息
	 * @param wechatPayInfo 微信支付参数节点
	 * @return 结果报文
	 */
	@Override
	public ServerMessage orderApply1003(String siteId, String loginId, String loginName, String payType,
			List productInfo,List wechatPayInfo,List alipayInfo) {
		//设置请求报文
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("1003");
		message.putVersion("01");
		message.putIStart("1");
		message.putZbody("SiteId", siteId);
		message.putZbody("LoginId", loginId);
		message.putZbody("LoginName", loginName);
		message.putZbody("ApplyTime", DateUtils.getTime());
		message.putZbody("PayType", payType);
		message.putZbody("ProductInfo", productInfo);
		message.putZbody("WechatPayInfo", wechatPayInfo);
		message.putZbody("AlipayInfo", alipayInfo);
		String json = message.toJson();
		return HttpClientUtils.getToServer(url, json);
	}

	/**
	 * 订单支付接口1007
	 * 
	 * @param orderId 订单编号
	 * @param payType 支付类型 (01支付宝 02:微信 03:百度钱包 04:翼支付 05:京东钱包 06:会员支付 07:银联支付 08:聚合支付)
	 * @param wechatPayInfo 微信支付参数节点
	 * @return 结果报文
	 */
	@Override
	public ServerMessage orderPay1007(String[] orderIds, String payType,List wechatPayInfo,List alipayInfo) {
		//设置请求报文
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("1007");
		message.putVersion("01");
		message.putZbody("OrderId", orderIds);
		message.putZbody("PayType", payType);
		message.putZbody("WechatPayInfo", wechatPayInfo);
		message.putZbody("AlipayInfo", alipayInfo);
		String json = message.toJson();
		return HttpClientUtils.getToServer(url, json);
	}

	/**
	 * 订单申请接口1241
	 * 
	 * @param siteId 售货机编号
	 * @param loginId 登陆编号
	 * @param loginName 客户名称
	 * @param payType 支付类型 (01支付宝 02:微信 03:百度钱包 04:翼支付 05:京东钱包 06:会员支付 07:银联支付 08:聚合支付)
	 * @param productInfo 商品信息
	 * @return 结果报文
	 */
	@Override
	public ServerMessage orderApply1241(String siteId, String loginId, String loginName, String payType,
			List productInfo) {
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("1241");
		message.putVersion("01");
		message.putIStart("1");
		message.putZbody("SiteId", siteId);
		message.putZbody("LoginId", loginId);
		message.putZbody("LoginName", loginName);
		message.putZbody("ApplyTime", DateUtils.getTime());
		message.putZbody("PayType", payType);
		message.putZbody("ProductInfo", productInfo);
		String json = message.toJson();
		return HttpClientUtils.getToServer(url, json);
	}

	/**
	 * 订单退款
	 * 
	 * @param orderId 订单编号
	 * @param operId 操作人编号
	 * @param operName 操作人姓名
	 * @return
	 */
	@Override
	public ServerMessage returnMoney1008(String orderId, String operId, String operName) {
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("1008");
		message.putVersion("01");
		message.putIStart("1");
		message.putZbody("OrderId", orderId);
		message.putZbody("OperId", operId);
		message.putZbody("OperName", operName);
		String json = message.toJson();
		return HttpClientUtils.getToServer(url, json);
	}

	/**
	 * 支付配置测试
	 * @param payType 测试类型01支付宝扫码支付 02:微信扫码支付
	 * @param corpId 公司编号
	 * @param price 价格
	 * @param notice 通知地址
	 */
	@Override
	public ServerMessage payConfigTest(String payType, String corpId, int price, String notice) {
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("2507");
		message.putVersion("01");
		message.putIStart("1");
		message.putZbody("PayType", payType);
		message.putZbody("CorpId", corpId);
		message.putZbody("Price", price);
		message.putZbody("Notice", notice);
		String json = message.toJson();
		return HttpClientUtils.getToServer(payUrl, json);
	}
	
	/**
	 * 保存微信支付配置
	 * 
	 * @param wechatPayConfig
	 * @param tradeType 00新增,01修改,02删除
	 * @return
	 */
	@Override
	public ServerMessage wechatPayConfigSave(WechatPayConfig wechatPayConfig,String tradeType) {
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("2505");
		message.putVersion("01");
		message.putIStart("1");
		message.putZbody("TradeType", tradeType);
		message.putZbody("PayType", wechatPayConfig.getPayType());
		message.putZbody("CorpId", wechatPayConfig.getCorpId());
		message.putZbody("CorpName", wechatPayConfig.getCorpName());
		message.putZbody("AppId", wechatPayConfig.getAppId());
		message.putZbody("ApiSercret", wechatPayConfig.getApiSercret());
		message.putZbody("MchId", wechatPayConfig.getMchId());
		message.putZbody("SubMchId", wechatPayConfig.getSubMchId());
		message.putZbody("Cert", wechatPayConfig.getCert());
		message.putZbody("NotifyUrl", wechatPayConfig.getNotifyUrl());
		String json = message.toJson();
		return HttpClientUtils.getToServer(payUrl, json);
	}

	/**
	 * 保存支付宝支付配置
	 * 
	 * @param aliPayConfig
	 * @param tradeType 00新增,01修改,02删除
	 * @return
	 */
	@Override
	public ServerMessage aliPayConfigSave(AliPayConfig aliPayConfig,String tradeType) {
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("2505");
		message.putVersion("01");
		message.putIStart("1");
		message.putZbody("TradeType", tradeType);
		message.putZbody("PayType", aliPayConfig.getPayType());
		message.putZbody("CorpId", aliPayConfig.getCorpId());		
		message.putZbody("CorpName", aliPayConfig.getCorpName());
		message.putZbody("AppId", aliPayConfig.getAppId());
		message.putZbody("PubKey", aliPayConfig.getPubKey());
		message.putZbody("PrivKey", aliPayConfig.getPrivKey());
		message.putZbody("SignType", aliPayConfig.getSignType());
		message.putZbody("NotifyUrl", aliPayConfig.getNotifyUrl());
		String json = message.toJson();
		return HttpClientUtils.getToServer(payUrl, json);
	}

	/**
	 * 查询支付配置
	 * 
	 * @param corpId 公司编号
	 * @param payType 支付类型01支付宝扫码支付 02:微信扫码支付 03:微信公众号支付 04:微信小程序支付 05:微信信用付(微信自动扣费) 06:一码清支付
	 * @return
	 */
	@Override
	public ServerMessage selectPayConfig(String corpId, String payType) {
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("2506");
		message.putVersion("01");
		message.putIStart("1");
		message.putZbody("PayType", payType);
		message.putZbody("CorpId", corpId);
		String json = message.toJson();
		String payUrl2 = SystemUtil.getPayUrl("1");
		return HttpClientUtils.getToServer(payUrl, json);
	}
    
	/**
     * 站点修改库存
     */
	@Override
	public ServerMessage siteInventory(String siteId,String operId,List<Map<String, String>> laneInfo) {
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("1061");
		message.putVersion("01");
		message.putIStart("1");
		message.putZbody("SiteId", siteId);
		message.putZbody("OperId", operId);
		message.putZbody("LaneInfo", laneInfo);
		String json = message.toJson();
		return HttpClientUtils.getToServer(url, json);	
	}
    
	/**
     * 售货机订单货道修改
     * @param outState 出柜状态  4:出柜失败 2:出柜成功
     */
	@Override
	public ServerMessage orderLane(String orderId,List<Map<String, String>> laneInfo, String operId) {
		
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("1010");
		message.putVersion("01");
		message.putIStart("1");
		message.putZbody("OrderId", orderId);
		message.putZbody("LaneInfo", laneInfo);
		message.putZbody("OperId", operId);
		String json = message.toJson();
		return HttpClientUtils.getToServer(url, json);	
	}

	/**
	 * 支付配置测试退款
	 * 
	 * @param orderId 订单编号
	 * @return
	 */
	@Override
	public ServerMessage payConfigTestReturnMoney(String orderId) {
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("2508");
		message.putVersion("01");
		message.putIStart("1");
		message.putZbody("OrderId", orderId);
		String json = message.toJson();
		return HttpClientUtils.getToServer(payUrl, json);
	}
	
	/**
	 * 订单申请1011
	 * 
	 */
	@Override
	public ServerMessage orderApply1011(Map<String,Object> map) {
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("1011");
		message.putVersion("01");
		message.putIStart("1");
		message.setZbody(map);
//		message.putZbody("SiteId", map.get("siteId"));
//		message.putZbody("LoginId", map.get("loginId"));
//		message.putZbody("LoginName", map.get(""));
//		message.putZbody("SaleChannel", map.get(""));
//		message.putZbody("ApplyTime", map.get(""));
//		message.putZbody("PayType", map.get(""));
//		message.putZbody("ProductInfo", map.get(""));
//		message.putZbody("WechatPayInfo", map.get(""));
		String json = message.toJson();
		return HttpClientUtils.getToServer(url, json);
	}

	/**
	 * 模拟通知服务器已支付
	 * 
	 * @param payMap
	 * @return
	 */
	@Override
	public ServerMessage noticeOrderPay2502(Map<String, Object> payMap) {
		ServerMessage message = new ServerMessage();
		message.putBCode("01");
		message.putTCode("1011");
		message.putVersion("01");
		message.putIStart("1");
		message.setZbody(payMap);
		String json = message.toJson();
		return HttpClientUtils.getToServer(url, json);
	}

}
