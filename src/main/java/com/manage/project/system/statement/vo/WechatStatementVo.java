/**
 * 
 */
package com.manage.project.system.statement.vo;

import java.util.Date;

import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * 微信账单vo
 * 
 * @author zhangjiawei
 * @date 2018年11月19日
 * 
 */
public class WechatStatementVo extends StatementVo{

	private static final long serialVersionUID = 1L;
	
	/**交易时间*/
	@Excel(name="交易时间",column="A")
	private Date payTime;
	/**交易流水号*/
	@Excel(name="交易流水号",column="B")
	private String tradeNo;
	/**订单编号*/
	@Excel(name="订单编号",column="C")
	private String orderId;
	/**支付方式: (1:支付宝 2:微信 3:百度钱包)*/
	@Excel(name="支付方式",column="D")
	private String payType;
	/**支付状态1:等待支付 2:支付成功 3:支付失败*/
	@Excel(name="支付状态",column="E")
	private String payState;
	/**支付金额*/
	@Excel(name="支付金额",column="F")
	private Float payMoney;
	
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public Float getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Float payMoney) {
		this.payMoney = payMoney;
	}

}
