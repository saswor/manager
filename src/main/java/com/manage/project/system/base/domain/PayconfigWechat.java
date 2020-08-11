package com.manage.project.system.base.domain;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 微信支付配置表 as_payconfig_wechat,因更换支付平台，不再使用
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Deprecated
public class PayconfigWechat extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 配置编号 */
	private String payConfigId;
	/** 公司编号 */
	private String corpId;
	/** 是否开启 1:是 2:否 */
	private String payAccept;
	/** 自动退款 1:支持 2:否 */
	private String autoReturn;
	/** 支付方式 1:扫码支付 2:公众号支付 */
	private String payType;
	/** 商户类型 1:服务商 2:子商 */
	private String contactType;
	/** 微信appid */
	private String appId;
	/** 微信密匙 */
	private String appSecret;
	/** 商户编号 */
	private String contactCode;
	/** API密匙 */
	private String apiSecret;
	/** 授权回调域名 */
	private String authBack;
	/** 退款通知地址 */
	private String returnNotice;
	/** 取货码出货通知地址 */
	private String fetchCodeNotice;
	/** 费率% */
	private Integer rate;
	/** 创建时间 */
	private String createTime;
	/**  */
	private String title;
	/** 商品描述,商品简单描述，该字段须严格按照规范传递 */
	private String body;
	/** 商户子号 */
	private String subMchId;
	/** 设备号,终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" */
	private String deviceInfo;
	/** 终端IP,APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。 */
	private String spbillCreateIp;
	/** 货币类型,符合ISO 4217标准的三位字母代码，默认人民币：CNY   */
	private String feeType;
	/** 交易类型,取值如下：JSAPI，NATIVE，APP */
	private String tradeType;
	/** 微信证书地址 */
	private String license;
	/** 是否测试成功：1等待测试；2测试成功 */
	private String testState;	
	
	public String getTestState() {
		return testState;
	}

	public void setTestState(String testState) {
		this.testState = testState;
	}
	
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setPayConfigId(String payConfigId) 
	{
		this.payConfigId = payConfigId;
	}

	public String getPayConfigId() 
	{
		return payConfigId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setPayAccept(String payAccept) 
	{
		this.payAccept = payAccept;
	}

	public String getPayAccept() 
	{
		return payAccept;
	}
	public void setAutoReturn(String autoReturn) 
	{
		this.autoReturn = autoReturn;
	}

	public String getAutoReturn() 
	{
		return autoReturn;
	}
	public void setPayType(String payType) 
	{
		this.payType = payType;
	}

	public String getPayType() 
	{
		return payType;
	}
	public void setContactType(String contactType) 
	{
		this.contactType = contactType;
	}

	public String getContactType() 
	{
		return contactType;
	}
	public void setAppId(String appId) 
	{
		this.appId = appId;
	}

	public String getAppId() 
	{
		return appId;
	}
	public void setAppSecret(String appSecret) 
	{
		this.appSecret = appSecret;
	}

	public String getAppSecret() 
	{
		return appSecret;
	}
	public void setContactCode(String contactCode) 
	{
		this.contactCode = contactCode;
	}

	public String getContactCode() 
	{
		return contactCode;
	}
	public void setApiSecret(String apiSecret) 
	{
		this.apiSecret = apiSecret;
	}

	public String getApiSecret() 
	{
		return apiSecret;
	}
	public void setAuthBack(String authBack) 
	{
		this.authBack = authBack;
	}

	public String getAuthBack() 
	{
		return authBack;
	}
	public void setReturnNotice(String returnNotice) 
	{
		this.returnNotice = returnNotice;
	}

	public String getReturnNotice() 
	{
		return returnNotice;
	}
	public void setFetchCodeNotice(String fetchCodeNotice) 
	{
		this.fetchCodeNotice = fetchCodeNotice;
	}

	public String getFetchCodeNotice() 
	{
		return fetchCodeNotice;
	}
	public void setRate(Integer rate) 
	{
		this.rate = rate;
	}

	public Integer getRate() 
	{
		return rate;
	}
	public void setCreateTime(String createTime) 
	{
		this.createTime = createTime;
	}

	public String getCreateTime() 
	{
		return createTime;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getTitle() 
	{
		return title;
	}
	public void setBody(String body) 
	{
		this.body = body;
	}

	public String getBody() 
	{
		return body;
	}
	public void setSubMchId(String subMchId) 
	{
		this.subMchId = subMchId;
	}

	public String getSubMchId() 
	{
		return subMchId;
	}
	public void setDeviceInfo(String deviceInfo) 
	{
		this.deviceInfo = deviceInfo;
	}

	public String getDeviceInfo() 
	{
		return deviceInfo;
	}
	public void setSpbillCreateIp(String spbillCreateIp) 
	{
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getSpbillCreateIp() 
	{
		return spbillCreateIp;
	}
	public void setFeeType(String feeType) 
	{
		this.feeType = feeType;
	}

	public String getFeeType() 
	{
		return feeType;
	}
	public void setTradeType(String tradeType) 
	{
		this.tradeType = tradeType;
	}

	public String getTradeType() 
	{
		return tradeType;
	}

}
