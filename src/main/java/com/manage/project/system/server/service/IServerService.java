/**
 * 
 */
package com.manage.project.system.server.service;

import java.util.List;
import java.util.Map;

import com.manage.project.system.base.domain.AliPayConfig;
import com.manage.project.system.base.domain.WechatPayConfig;
import com.manage.project.system.server.domain.ServerMessage;
import com.manage.project.system.vending.domain.VendingCmd;

/**
 * 调用服务器接口
 * 
 * @author zhangjiawei
 * @date 2018年11月2日
 * 
 */
public interface IServerService {

	/**
	 * 订单取消1004
	 * 
	 * @param orderId 订单id
	 * @return 结果报文
	 */
	public ServerMessage orderCancel(String orderId);
	
	/**
	 * 售货机命令推送接口1227
	 * 
	 * @param siteId 售货机序列号
	 * @param cmdCode 命令对象编号 售货机编号、商品编号、订单编号
	 * @param cmdType 命令类型 01:售货机  02:商品  03:订单  04:格口   05:柜门
	 * @param cmd 命令
	 * @param cont 命令内容
	 * @return 结果报文
	 */
	public String vendingCommand1227(String siteId,VendingCmd vendingCmd);
	
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
	public ServerMessage orderApply1003(String siteId,String loginId,String loginName,String payType,List productInfo,List wechatPayInfo,List alipayInfo);
	
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
	public ServerMessage orderApply1241(String siteId,String loginId,String loginName,String payType,List productInfo);
	
	/**
	 * 订单支付接口1007
	 * 
	 * @param orderId 订单编号
	 * @param payType 支付类型 (01支付宝 02:微信 03:百度钱包 04:翼支付 05:京东钱包 06:会员支付 07:银联支付 08:聚合支付)
	 * @param wechatPayInfo 微信支付参数节点
	 * @return 结果报文
	 */
	public ServerMessage orderPay1007(String[] orderIds,String payType,List wechatPayInfo,List alipayInfo);
	
	/**
	 * 订单退款
	 * 
	 * @param orderId 订单编号
	 * @param operId 操作人编号
	 * @param operName 操作人姓名
	 * @return
	 */
	public ServerMessage returnMoney1008(String orderId,String operId,String operName);
	
	/**
	 * 支付配置测试
	 * 
	 * @param payType
	 * @param corpId
	 * @param price
	 * @param notice
	 * @return
	 */
	public ServerMessage payConfigTest(String payType,String corpId,int price,String notice);
	
	/**
	 * 保存微信支付配置
	 * 
	 * @param wechatPayConfig
	 * @return
	 */
	public ServerMessage wechatPayConfigSave(WechatPayConfig wechatPayConfig,String tradeType);
	
	/**
	 * 保存支付宝支付配置
	 * 
	 * @param aliPayConfig
	 * @return
	 */
	public ServerMessage aliPayConfigSave(AliPayConfig aliPayConfig,String tradeType);
	
	/**
	 * 查询支付配置
	 * 
	 * @param corpId 公司编号
	 * @param payType 支付类型01支付宝扫码支付 02:微信扫码支付 03:微信公众号支付 04:微信小程序支付 05:微信信用付(微信自动扣费) 06:一码清支付
	 * @return
	 */
	public ServerMessage selectPayConfig(String corpId,String payType);
	
	/**
	 * 站点修改库存接口1061
	 * @param siteInventory
	 * @time 2019年1月16日下午7:22:48
	 */
	public ServerMessage siteInventory(String siteId,String operId,List<Map<String, String>> laneInfo);
	
	/**
	 * 售货机订单货道修改1010
	 * @param orderLane
	 * @time 2019年1月16日下午7:25:17
	 */
	public ServerMessage orderLane(String orderId,List<Map<String, String>> laneInfo, String operId);

	/**
	 * 支付配置测试退款
	 * 
	 * @param orderId 订单编号
	 * @return
	 */
	public ServerMessage payConfigTestReturnMoney(String orderId);

	/**
	 * 订单申请1011接口
	 * 
	 * @param map
	 * @return
	 */
	ServerMessage orderApply1011(Map<String, Object> map);

	/**
	 * 模拟通知服务器已支付
	 * 
	 * @param payMap
	 * @return
	 */
	public ServerMessage noticeOrderPay2502(Map<String, Object> payMap);

	
}
