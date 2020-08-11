package com.manage.project.system.statement.domain;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 对账补货表 as_statement_supply
 * 
 * @author zhangjiawei
 * @date 2018-10-16
 */
public class StatementSupply extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 补货编号 */
	private String sOrderId;
	/** 站点编号 */
	private String siteId;
	/** 公司编号 */
	private String corpId;
	/** 补货名称 */
	private String name;
	/** 区域编号 */
	private String districtId;
	/** 线路编号 */
	private String lineId;
	/** 仓库编号 */
	private String wmId;
	/** 交易开始时间(此次补货第一次开始售卖时间) */
	private String tradeSTime;
	/** 交易结束时间(此次补货最后一次卖完时间) */
	private String tradeEtime;
	/** 实际补货总数量 */
	private Integer supplyNum;
	/** 已销售商品总数量 */
	private Integer saleNum;
	/** 已下架商品总数量(下架包括过期下架和正常下架) */
	private Integer underNum;
	/** 对账异常商品总数 */
	private Integer abnomarlNum;
	/** 采购总金额 */
	private Float buyMoney;
	/** 销售总金额 */
	private Float salePMoney;
	/** 支付总金额 */
	private Float saleRMoney;
	/** 优惠总金额 */
	private Float saleFMoney;
	/** 下架总金额 */
	private Float saleUMoney;
	/** 退款总金额 */
	private Float saleReturn;
	/** 对账异常金额 对账多了正数，少了负数 */
	private Float abnomarlMoney;
	/** 销售状态 1:未开始销售 2:销售中 3:销售完成 */
	private String salteState;
	/** 对账状态 1:等待售完 2:等待对账 3:对账正常 4:对账异常 */
	private String statementState;
	/** 结算状态 1:未提交 2:已提交 */
	private String curState;
	/** 结算编号 */
	private String sbalanceId;

	public Float getSaleReturn() {
		return saleReturn;
	}

	public void setSaleReturn(Float saleReturn) {
		this.saleReturn = saleReturn;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSOrderId(String sOrderId) 
	{
		this.sOrderId = sOrderId;
	}

	public String getSOrderId() 
	{
		return sOrderId;
	}
	public void setSiteId(String siteId) 
	{
		this.siteId = siteId;
	}

	public String getSiteId() 
	{
		return siteId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
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
	public void setWmId(String wmId) 
	{
		this.wmId = wmId;
	}

	public String getWmId() 
	{
		return wmId;
	}
	public void setTradeSTime(String tradeSTime) 
	{
		this.tradeSTime = tradeSTime;
	}

	public String getTradeSTime() 
	{
		return tradeSTime;
	}
	public void setTradeEtime(String tradeEtime) 
	{
		this.tradeEtime = tradeEtime;
	}

	public String getTradeEtime() 
	{
		return tradeEtime;
	}
	public void setSupplyNum(Integer supplyNum) 
	{
		this.supplyNum = supplyNum;
	}

	public Integer getSupplyNum() 
	{
		return supplyNum;
	}
	public void setSaleNum(Integer saleNum) 
	{
		this.saleNum = saleNum;
	}

	public Integer getSaleNum() 
	{
		return saleNum;
	}
	public void setUnderNum(Integer underNum) 
	{
		this.underNum = underNum;
	}

	public Integer getUnderNum() 
	{
		return underNum;
	}
	public void setAbnomarlNum(Integer abnomarlNum) 
	{
		this.abnomarlNum = abnomarlNum;
	}

	public Integer getAbnomarlNum() 
	{
		return abnomarlNum;
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
	public void setSaleUMoney(Float saleUMoney) 
	{
		this.saleUMoney = saleUMoney;
	}

	public Float getSaleUMoney() 
	{
		return saleUMoney;
	}
	public void setAbnomarlMoney(Float abnomarlMoney) 
	{
		this.abnomarlMoney = abnomarlMoney;
	}

	public Float getAbnomarlMoney() 
	{
		return abnomarlMoney;
	}
	public void setSalteState(String salteState) 
	{
		this.salteState = salteState;
	}

	public String getSalteState() 
	{
		return salteState;
	}
	public void setStatementState(String statementState) 
	{
		this.statementState = statementState;
	}

	public String getStatementState() 
	{
		return statementState;
	}
	public void setCurState(String curState) 
	{
		this.curState = curState;
	}

	public String getCurState() 
	{
		return curState;
	}
	public void setSbalanceId(String sbalanceId) 
	{
		this.sbalanceId = sbalanceId;
	}

	public String getSbalanceId() 
	{
		return sbalanceId;
	}


}
