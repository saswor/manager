package com.manage.project.system.statement.domain;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 记录客户购买的商品表 as_order_apply
 * 
 * @author 张佳伟
 * @date 2018-10-17
 */
public class OrderApply extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 订单编号 */
	private String orderId;
	/** 终端订单编号 */
	private String torderId;
	/** 托管公司编号 */
	private String corpId;
	/** 区域编号 */
	private String districtId;
	/** 线路编号 */
	private String lineId;
	/** 点位编号 */
	private String pointId;
	/** 售货机编号 */
	private String siteId;
	/** 站点名称 */
	private String siteName;
	/** 购买客户唯一识别身份编号 */
	private String loginId;
	/** 购买客户唯一识别身份名称 */
	private String loginName;
	/** 来源渠道 1:微信 2:安卓3:ios 4:终端 */
	private String saleChannel;
	/** 是否立即出柜 1:是 2:否 */
	private String outState;
	/** 商品数量 */
	private Integer pNum;
	/** 优惠方式 01:购买折扣 02:消费立减 */
	private String favWay;
	/** 商品总采购价 */
	private Float buyPrice;
	/** 商品总售价 */
	private Float salePrice;
	/** 商品总支付价格 */
	private Float payPrice;
	/** 总优惠金额(整机优惠采用平均法计算优惠以及支付价格) */
	private Float favPrice;
	/** 总退款金额(目前只支持全额退款) */
	private Float returnMoney;
	/** 毛利总额 */
	private Float profitMoney;
	/** 支付方式: (1:支付宝 2:微信 3:百度钱包) */
	private String payType;
	/** 支付状态1:等待支付 2:支付成功 3:支付失败 */
	private String payState;
	/** 退款状态 0:无 1:全额退款 2:部分退款 2:退款失败 */
	private String returnType;
	/** 取货超期时间 */
	private String fetchOverTime;
	/** 当前状态 1:申请 2:提前取货3:客户已取货 4:已回收 */
	private String curState;
	/** 状态时间 */
	private String stateTime;
	/** 异常状态0:无 1:取货故障2:客户取消 3:未取取消 */
	private String abnomarlState;
	/** 取货口编号 */
	private String outId;
	/** 异常状态时间 */
	private String aStateTime;
	/** 订单状态 1:正常 2:关闭 */
	private String orderType;
	/** 取件密码 */
	private String passWord;
	/** 加密类型1:NONE 2:3DES 3:AES 4:SHA256 5:MD5 */
	private String encryptionType;
	/** 盐值 */
	private String slat;
	/** 支付宝和微信支付的交易流水号 */
	private String pTradeNo;
	/** 支付宝和微信退款的交易流水号 */
	private String rTradeNo;
	/** 推送状态 1：未推送 2:已推送 3：推送成功 4：推送失败[增加] */
	private String pushState;
	/** 推送状态变化时间[增加] */
	private String pStateTime;

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setOrderId(String orderId) 
	{
		this.orderId = orderId;
	}

	public String getOrderId() 
	{
		return orderId;
	}
	public void setTorderId(String torderId) 
	{
		this.torderId = torderId;
	}

	public String getTorderId() 
	{
		return torderId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setDistrictId(String districtId) 
	{
		this.districtId = districtId;
	}

	public String getDistrictId() 
	{
		return districtId;
	}
	public void setLineId(String lineId) 
	{
		this.lineId = lineId;
	}

	public String getLineId() 
	{
		return lineId;
	}
	public void setPointId(String pointId) 
	{
		this.pointId = pointId;
	}

	public String getPointId() 
	{
		return pointId;
	}
	public void setSiteId(String siteId) 
	{
		this.siteId = siteId;
	}

	public String getSiteId() 
	{
		return siteId;
	}
	public void setSiteName(String siteName) 
	{
		this.siteName = siteName;
	}

	public String getSiteName() 
	{
		return siteName;
	}
	public void setLoginId(String loginId) 
	{
		this.loginId = loginId;
	}

	public String getLoginId() 
	{
		return loginId;
	}
	public void setLoginName(String loginName) 
	{
		this.loginName = loginName;
	}

	public String getLoginName() 
	{
		return loginName;
	}
	public void setSaleChannel(String saleChannel) 
	{
		this.saleChannel = saleChannel;
	}

	public String getSaleChannel() 
	{
		return saleChannel;
	}
	public void setOutState(String outState) 
	{
		this.outState = outState;
	}

	public String getOutState() 
	{
		return outState;
	}
	public void setPNum(Integer pNum) 
	{
		this.pNum = pNum;
	}

	public Integer getPNum() 
	{
		return pNum;
	}
	public void setFavWay(String favWay) 
	{
		this.favWay = favWay;
	}

	public String getFavWay() 
	{
		return favWay;
	}
	public void setBuyPrice(Float buyPrice) 
	{
		this.buyPrice = buyPrice;
	}

	public Float getBuyPrice() 
	{
		return buyPrice;
	}
	public void setSalePrice(Float salePrice) 
	{
		this.salePrice = salePrice;
	}

	public Float getSalePrice() 
	{
		return salePrice;
	}
	public void setPayPrice(Float payPrice) 
	{
		this.payPrice = payPrice;
	}

	public Float getPayPrice() 
	{
		return payPrice;
	}
	public void setFavPrice(Float favPrice) 
	{
		this.favPrice = favPrice;
	}

	public Float getFavPrice() 
	{
		return favPrice;
	}
	public void setReturnMoney(Float returnMoney) 
	{
		this.returnMoney = returnMoney;
	}

	public Float getReturnMoney() 
	{
		return returnMoney;
	}
	public void setProfitMoney(Float profitMoney) 
	{
		this.profitMoney = profitMoney;
	}

	public Float getProfitMoney() 
	{
		return profitMoney;
	}
	public void setPayType(String payType) 
	{
		this.payType = payType;
	}

	public String getPayType() 
	{
		return payType;
	}
	public void setPayState(String payState) 
	{
		this.payState = payState;
	}

	public String getPayState() 
	{
		return payState;
	}
	public void setReturnType(String returnType) 
	{
		this.returnType = returnType;
	}

	public String getReturnType() 
	{
		return returnType;
	}
	public void setFetchOverTime(String fetchOverTime) 
	{
		this.fetchOverTime = fetchOverTime;
	}

	public String getFetchOverTime() 
	{
		return fetchOverTime;
	}
	public void setCurState(String curState) 
	{
		this.curState = curState;
	}

	public String getCurState() 
	{
		return curState;
	}
	public void setStateTime(String stateTime) 
	{
		this.stateTime = stateTime;
	}

	public String getStateTime() 
	{
		return stateTime;
	}
	public void setAbnomarlState(String abnomarlState) 
	{
		this.abnomarlState = abnomarlState;
	}

	public String getAbnomarlState() 
	{
		return abnomarlState;
	}
	public void setOutId(String outId) 
	{
		this.outId = outId;
	}

	public String getOutId() 
	{
		return outId;
	}
	public void setAStateTime(String aStateTime) 
	{
		this.aStateTime = aStateTime;
	}

	public String getAStateTime() 
	{
		return aStateTime;
	}
	public void setOrderType(String orderType) 
	{
		this.orderType = orderType;
	}

	public String getOrderType() 
	{
		return orderType;
	}
	public void setPassWord(String passWord) 
	{
		this.passWord = passWord;
	}

	public String getPassWord() 
	{
		return passWord;
	}
	public void setEncryptionType(String encryptionType) 
	{
		this.encryptionType = encryptionType;
	}

	public String getEncryptionType() 
	{
		return encryptionType;
	}
	public void setSlat(String slat) 
	{
		this.slat = slat;
	}

	public String getSlat() 
	{
		return slat;
	}

	public void setPTradeNo(String pTradeNo) 
	{
		this.pTradeNo = pTradeNo;
	}

	public String getPTradeNo() 
	{
		return pTradeNo;
	}
	public void setRTradeNo(String rTradeNo) 
	{
		this.rTradeNo = rTradeNo;
	}

	public String getRTradeNo() 
	{
		return rTradeNo;
	}
	public void setPushState(String pushState) 
	{
		this.pushState = pushState;
	}

	public String getPushState() 
	{
		return pushState;
	}
	public void setPStateTime(String pStateTime) 
	{
		this.pStateTime = pStateTime;
	}

	public String getPStateTime() 
	{
		return pStateTime;
	}

}
