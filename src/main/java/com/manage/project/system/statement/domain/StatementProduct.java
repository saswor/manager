package com.manage.project.system.statement.domain;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 补货对账售出明细as_statement_product
 * 
 * @author 张佳伟
 * @date 2018-10-16
 */
public class StatementProduct extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 补货账单售出主键 */
	private String statementPid;
	/** 商品编号 */
	private String productId;
	/** 销售订单编号 */
	private String orderId;
	/** 销售订单货道商品出货编号 */
	private String proboxId;
	/** 售货机编号 */
	private String siteId;
	/** 货道开始编号 */
	private Integer laneSId;
	/** 货道结束编号 */
	private Integer laneEId;
	/** 排序(按货道，seqid排序) */
	private Integer seqId;
	/** 出柜类型 1:销售 2:下架 */
	private String outType;
	/** 支付类型 01:支付宝 02:微信 03:无 */
	private String payType;
	/** 支付宝和微信的交易流水号货无 */
	private String tradeNo;
	/** 采购进价(单商品进价价格) */
	private Float buyMoney;
	/** 销售金额(单商品销售价格) */
	private Float salePMoney;
	/** 支付金额(下架商品没有值) */
	private Float saleRMoney;
	/** 优惠金额(下架商品没有值) */
	private Float saleFMoney;
	/** 退款金额(单商品退款价格) */
	private Float saleReturn;
	/** 支付宝和微信支付的交易流水号 */
	private String pTradeNo;
	/** 支付宝和微信退款的交易流水号 */
	private String rTradeNo;
	/** 对账状态 1:未出柜 1:已出柜 2:对账正常 3:异常对账 */
	private String curState;
	/** 异常金额 */
	private Float abnomarlMoney;
	/** 托管公司编号 */
	private String corpId;
	/** 补货编号 */
	private String sOrderId;

	public String getSOrderId() {
		return sOrderId;
	}

	public void setSOrderId(String sOrderId) {
		this.sOrderId = sOrderId;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setStatementPid(String statementPid) 
	{
		this.statementPid = statementPid;
	}

	public String getStatementPid() 
	{
		return statementPid;
	}
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setOrderId(String orderId) 
	{
		this.orderId = orderId;
	}

	public String getOrderId() 
	{
		return orderId;
	}
	public void setProboxId(String proboxId) 
	{
		this.proboxId = proboxId;
	}

	public String getProboxId() 
	{
		return proboxId;
	}
	public void setSiteId(String siteId) 
	{
		this.siteId = siteId;
	}

	public String getSiteId() 
	{
		return siteId;
	}
	public void setLaneSId(Integer laneSId) 
	{
		this.laneSId = laneSId;
	}

	public Integer getLaneSId() 
	{
		return laneSId;
	}
	public void setLaneEId(Integer laneEId) 
	{
		this.laneEId = laneEId;
	}

	public Integer getLaneEId() 
	{
		return laneEId;
	}
	public void setSeqId(Integer seqId) 
	{
		this.seqId = seqId;
	}

	public Integer getSeqId() 
	{
		return seqId;
	}
	public void setOutType(String outType) 
	{
		this.outType = outType;
	}

	public String getOutType() 
	{
		return outType;
	}
	public void setPayType(String payType) 
	{
		this.payType = payType;
	}

	public String getPayType() 
	{
		return payType;
	}
	public void setTradeNo(String tradeNo) 
	{
		this.tradeNo = tradeNo;
	}

	public String getTradeNo() 
	{
		return tradeNo;
	}
	public void setBuyMoney(Float buyMoney) 
	{
		this.buyMoney = buyMoney;
	}

	public Float getBuyMoney() 
	{
		return buyMoney;
	}
	public void setSalePMoney(Float salePMoney) 
	{
		this.salePMoney = salePMoney;
	}

	public Float getSalePMoney() 
	{
		return salePMoney;
	}
	public void setSaleRMoney(Float saleRMoney) 
	{
		this.saleRMoney = saleRMoney;
	}

	public Float getSaleRMoney() 
	{
		return saleRMoney;
	}
	public void setSaleFMoney(Float saleFMoney) 
	{
		this.saleFMoney = saleFMoney;
	}

	public Float getSaleFMoney() 
	{
		return saleFMoney;
	}
	public void setSaleReturn(Float saleReturn) 
	{
		this.saleReturn = saleReturn;
	}

	public Float getSaleReturn() 
	{
		return saleReturn;
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
	public void setCurState(String curState) 
	{
		this.curState = curState;
	}

	public String getCurState() 
	{
		return curState;
	}
	public void setAbnomarlMoney(Float abnomarlMoney) 
	{
		this.abnomarlMoney = abnomarlMoney;
	}

	public Float getAbnomarlMoney() 
	{
		return abnomarlMoney;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	
}
