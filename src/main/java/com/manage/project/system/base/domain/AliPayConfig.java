package com.manage.project.system.base.domain;

/**
 * 支付宝支付配置
 * 
 * @author zhangjiawei
 *
 */
public class AliPayConfig {

	/** 公司编号 */
	private String corpId;
	/** 公司名称 */
	private String corpName;
	/** 支付方式 01支付宝扫码支付 02:微信扫码支付 03:微信公众号支付 04:微信小程序支付 05:微信信用付(微信自动扣费) 06:一码清支付 */
	private String payType;
	/** 支付宝appid */
	private String appId;
	/** 支付宝公匙 */
	private String pubKey;
	/** 支付宝私匙 */
	private String privKey;
	/** 加密算法名称 */
	private String signType;
	/** 回调地址 */
	private String notifyUrl;
	/** 认证状态 1:已认证 2:未认证(认证状态表示支付配置能成功支付) */
	private String testState;
	
	public String getTestState() {
		return testState;
	}
	public void setTestState(String testState) {
		this.testState = testState;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPubKey() {
		return pubKey;
	}
	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}
	public String getPrivKey() {
		return privKey;
	}
	public void setPrivKey(String privKey) {
		this.privKey = privKey;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
}
