/**
 * 
 */
package com.manage.project.system.statement.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 对账导入第三方账单vo
 * 
 * @author zhangjiawei
 * @date 2018年10月17日
 * 
 */
public abstract class StatementVo implements Serializable{

	private static final long serialVersionUID = 1L;
/*
	*//**交易时间*//*
	private Date payTime;
	*//**交易流水号*//*
	private String tradeNo;
	*//**订单编号*//*
	private String orderId;
	*//**支付方式: (1:支付宝 2:微信 3:百度钱包)*//*
	private String payType;
	*//**支付状态1:等待支付 2:支付成功 3:支付失败*//*
	private String payState;
	*//**支付金额*//*
	private Float payMoney;
	*/
	public abstract Date getPayTime();
	
	public abstract void setPayTime(Date payTime);
	
	public abstract String getTradeNo();
	
	public abstract void setTradeNo(String tradeNo);
	
	public abstract String getOrderId();
	
	public abstract void setOrderId(String orderId);
	
	public abstract String getPayType();
	
	public abstract void setPayType(String payType);
	
	public abstract String getPayState();
	
	public abstract void setPayState(String payState);
	
	public abstract Float getPayMoney();
	
	public abstract void setPayMoney(Float payMoney);
	
}
