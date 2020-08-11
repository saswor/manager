/**
 * 
 */
package com.manage.project.system.report.vo;

import java.io.Serializable;

import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * 订单明细返回值vo
 * 
 * @author zhangjiawei
 * @date 2018年10月9日
 * 
 */
public class OrderDetailVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 订单编号 */
	@Excel(name = "订单编号", column = "A")
	private String orderId;
	/** 点位编号 */
	@Excel(name = "点位编号", column = "C")
	private String  pointId;
	/** 点位名称 */
	@Excel(name = "点位名称", column = "B")
	private String pointName;
	/** 售货机编号 */
	private String siteId;
	/** 货柜编号 */
	private String cabinetId;
	/** 货柜类型 */
	private String cabinetType;
	/** 货柜类型名称 */
	private String cabinetTypeName;
	/** 货道编号 */
	private String laneSId;
	/** 商品编号 */
	private String productId;
	/** 商品名称 */
	@Excel(name = "商品名称", column = "D")
	private String productName;
	/** 订单价格 */
	@Excel(name = "出售价", column = "E")
	private String payPrice;
	/** 支付方式 */
	private String payType;
	/** 支付方式名称 */
	@Excel(name = "方式", column = "G")
	private String payTypeName;
	/** 支付状态 */
	private String payState;
	/** 支付状态名称 */
	@Excel(name = "支付", column = "H")
	private String payStateName;
	/** 出货状态 */
	private String outState;
	/** 出货状态名称 */
	@Excel(name = "出货状态", column = "I")
	private String outStateName;
	/** 退款状态 */
	private String returnType;
	/** 退款状态名称 */
	@Excel(name = "退款状态", column = "J")
	private String returnTypeName;
	/** 创建时间 */
	@Excel(name = "交易时间", column = "K")
	private String createTime;
	/**利润*/
	@Excel(name = "利润", column = "F")
	private String profit;
	
	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getReturnTypeName() {
		return returnTypeName;
	}
	public void setReturnTypeName(String returnTypeName) {
		this.returnTypeName = returnTypeName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getCabinetId() {
		return cabinetId;
	}
	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}
	public String getCabinetType() {
		return cabinetType;
	}
	public void setCabinetType(String cabinetType) {
		this.cabinetType = cabinetType;
	}
	public String getLaneSId() {
		return laneSId;
	}
	public void setLaneSId(String laneSId) {
		this.laneSId = laneSId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCabinetTypeName() {
		return cabinetTypeName;
	}
	public void setCabinetTypeName(String cabinetTypeName) {
		this.cabinetTypeName = cabinetTypeName;
	}
	public String getPayTypeName() {
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
	public String getPayStateName() {
		return payStateName;
	}
	public void setPayStateName(String payStateName) {
		this.payStateName = payStateName;
	}
	public String getOutStateName() {
		return outStateName;
	}
	public void setOutStateName(String outStateName) {
		this.outStateName = outStateName;
	}

}
