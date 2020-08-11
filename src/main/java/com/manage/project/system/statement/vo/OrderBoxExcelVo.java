package com.manage.project.system.statement.vo;

import com.manage.common.utils.StringUtils;
import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * 订单导出
 * @author xufeng
 *
 */
public class OrderBoxExcelVo {
	@Excel(name="订单编号",column="A")
	private String orderId;
	@Excel(name="售货机名称",column="B")
	private String siteName;
	@Excel(name="商品名称",column="C")
	private String productName;
	@Excel(name="采购价格",column="D")
	private Float buyPrice;
	@Excel(name="销售价格",column="E")
	private Float salePrice;
	@Excel(name="支付价格",column="F")
	private Float payPrice;
	@Excel(name="利润",column="G")
	private Float profitMoney;
	@Excel(name="支付类型",column="H")
	private String payType;
	@Excel(name="支付方式",column="I")
	private String payState;
	@Excel(name="出柜状态",column="J")
	private String outState;
	@Excel(name="系统检测到出柜状态",column="K")
	private String sysState;
	@Excel(name="退款状态",column="L")
	private String returnType;
	@Excel(name="交易流水号",column="M")
	private String pTradeNo;
	@Excel(name="退款流水号",column="N")
	private String rTradeNo;
	@Excel(name="交易时间",column="O")
	private String createTime; 

	public String getSysState() {
		return sysState;
	}
	public void setSysState(String sysState) {
		this.sysState = sysState;
	}
	public String getSysStateName() {
		if(StringUtils.isEmpty(sysState)) {
			return "";
		}else if("1".equals(sysState)){
			return "正常出柜";
		}else if("2".equals(sysState)){
			return "红外检测异常";
		}else if("3".equals(sysState)){
			return "中间层异常";
		}else if("4".equals(sysState)){
			return "货道运转异常";
		}else if("5".equals(sysState)){
			return "其他异常";
		}else {
			return sysState;
		}
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Float getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Float buyPrice) {
		this.buyPrice = buyPrice;
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
	public String getOutState() {
		return outState;
	}
	public void setOutState(String outState) {
		this.outState = outState;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getpTradeNo() {
		return pTradeNo;
	}
	public void setpTradeNo(String pTradeNo) {
		this.pTradeNo = pTradeNo;
	}
	public String getrTradeNo() {
		return rTradeNo;
	}
	public void setrTradeNo(String rTradeNo) {
		this.rTradeNo = rTradeNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
