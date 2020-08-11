package com.manage.project.system.base.domain;

/**
 * 微信支付配置
 * 
 * @author zhangjiawei
 *
 */
public class WechatPayConfig {

	/** 公司编号 */
	private String corpId;
	/** 公司名称 */
	private String corpName;
	/** 支付方式 01支付宝扫码支付 02:微信扫码支付 03:微信公众号支付 04:微信小程序支付 05:微信信用付(微信自动扣费) 06:一码清支付 */
	private String payType;
	/** 微信appid */
	private String appId;
	/** API密匙 */
	private String apiSercret;
	/** 微信商户接入号 */
	private String mchId;
	/** 微信子商户接入号 */
	private String subMchId;
	/** 证书云地址 */
	private String cert;
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
	public String getApiSercret() {
		return apiSercret;
	}
	public void setApiSercret(String apiSercret) {
		this.apiSercret = apiSercret;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getSubMchId() {
		return subMchId;
	}
	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}
	public String getCert() {
		return cert;
	}
	public void setCert(String cert) {
		this.cert = cert;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
}
