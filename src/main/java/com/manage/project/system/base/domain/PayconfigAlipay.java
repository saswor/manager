package com.manage.project.system.base.domain;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 支付宝支付配置表 as_payconfig_alipay,因更换支付平台，不再使用
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Deprecated
public class PayconfigAlipay extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 配置编号 */
	private String payConfigId;
	/** 是否开启 1:是 2:否 */
	private String payAccept;
	/** 公司编号 */
	private String corpId;
	/** 自动退款 1:支持 2:否 */
	private String autoReturn;
	/** 支付方式 1:扫码支付 2:生活号支付 */
	private String payType;
	/** 支付宝版本 1:1.0 2:2.0 */
	private String alipayVersion;
	/** 加密方式1:rsa 2:rsa2 */
	private String enType;
	/** pid */
	private String pid;
	/** 支付宝密匙 */
	private String key;
	/** 是否入住 1:是 2:否 */
	private String isCheckIn;
	/** 费率% */
	private Integer rate;
	/** 创建时间 */
	private String createTime;
	/** 公钥? */
	private String publKey;
	/** 签名类型? */
	private String signType;
	/** 支付宝查询服务的api地址 */
	private String alipayApi;
	/** 授权回调域名 */
	private String authBack;
	/** 退款通知地址? */
	private String returnNotice;
	/** 私钥? */
	private String privKey;
	/** 商品标题? */
	private String title;
	/** 商品描述,商品简单描述，该字段须严格按照规范传递 */
	private String body;
	/** 交易类型,取值如下：JSAPI，NATIVE，APP */
	private String tradeType;
	/** 是否测试成功：1等待测试；2测试成功 */
	private String testState;	
	
	public String getTestState() {
		return testState;
	}

	public void setTestState(String testState) {
		this.testState = testState;
	}

	public String getPublKey() {
		return publKey;
	}

	public void setPublKey(String publKey) {
		this.publKey = publKey;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getAlipayApi() {
		return alipayApi;
	}

	public void setAlipayApi(String alipayApi) {
		this.alipayApi = alipayApi;
	}

	public String getAuthBack() {
		return authBack;
	}

	public void setAuthBack(String authBack) {
		this.authBack = authBack;
	}

	public String getReturnNotice() {
		return returnNotice;
	}

	public void setReturnNotice(String returnNotice) {
		this.returnNotice = returnNotice;
	}

	public String getPrivKey() {
		return privKey;
	}

	public void setPrivKey(String privKey) {
		this.privKey = privKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
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
	public void setPayAccept(String payAccept) 
	{
		this.payAccept = payAccept;
	}

	public String getPayAccept() 
	{
		return payAccept;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
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
	public void setAlipayVersion(String alipayVersion) 
	{
		this.alipayVersion = alipayVersion;
	}

	public String getAlipayVersion() 
	{
		return alipayVersion;
	}
	public void setEnType(String enType) 
	{
		this.enType = enType;
	}

	public String getEnType() 
	{
		return enType;
	}
	public void setPid(String pid) 
	{
		this.pid = pid;
	}

	public String getPid() 
	{
		return pid;
	}
	public void setKey(String key) 
	{
		this.key = key;
	}

	public String getKey() 
	{
		return key;
	}
	public void setIsCheckIn(String isCheckIn) 
	{
		this.isCheckIn = isCheckIn;
	}

	public String getIsCheckIn() 
	{
		return isCheckIn;
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

}
