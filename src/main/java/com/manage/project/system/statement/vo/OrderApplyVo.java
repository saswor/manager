package com.manage.project.system.statement.vo;

import com.manage.common.utils.StringUtils;
import com.manage.project.system.statement.domain.OrderApply;

public class OrderApplyVo{

	private String orderId;
	private String siteId;
	private String siteName;
	private Integer pNum;
	private Float salePrice;
	private Float payPrice;
	private Float favPrice;
	private Float profitMoney;
	private String returnType;
	private float returnMoney;
	private String payType;
	private String payState;
	private String createTime;
	private String abnomarlState;
	private String abnomarlStateName;
	private String pTradeNo;
	
	public String getAbnomarlState() {
		return abnomarlState;
	}
	public void setAbnomarlState(String abnomarlState) {
		this.abnomarlState = abnomarlState;
	}
	public String getAbnomarlStateName() {
		if(StringUtils.isEmpty(abnomarlStateName)) {
			if ("0".equals(getAbnomarlState())) {
				return "无";
			} else if("1".equals(getAbnomarlState())){
				return "取货故障";
			} else if("2".equals(getAbnomarlState())){
				return "客户取消";
			} else if("3".equals(getAbnomarlState())){
				return "未取取消";
			} else {
				return "";
			}
		}else {
			return abnomarlStateName;
		}
	}
	public String getpTradeNo() {
		return pTradeNo;
	}
	public void setpTradeNo(String pTradeNo) {
		this.pTradeNo = pTradeNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public Integer getpNum() {
		return pNum;
	}
	public void setpNum(Integer pNum) {
		this.pNum = pNum;
	}
	public Float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}
	public Float getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Float payPrice) {
		this.payPrice = payPrice;
	}
	public Float getFavPrice() {
		return favPrice;
	}
	public void setFavPrice(Float favPrice) {
		this.favPrice = favPrice;
	}
	public Float getProfitMoney() {
		return profitMoney;
	}
	public void setProfitMoney(Float profitMoney) {
		this.profitMoney = profitMoney;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public float getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(float returnMoney) {
		this.returnMoney = returnMoney;
	}
	
	
}
