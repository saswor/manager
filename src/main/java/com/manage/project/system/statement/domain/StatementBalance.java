package com.manage.project.system.statement.domain;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 补货账单结算表 as_statement_balance
 * 
 * @author 张佳伟
 * @date 2018-10-16
 */
public class StatementBalance extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 结算编号 */
	private String balanceId;
	/** 区域编号 */
	private String districtId;
	/** 交易开始时间 */
	private String tradeSTime;
	/** 交易结束时间 */
	private String tradeEtime;
	/** 总应给款 */
	private Float payPMoney;
	/** 净收入金额 */
	private Float incomeMoney;
	/** 手续费 */
	private Float poundage;
	/** 应给款 */
	private Float payRMoney;
	/** 托管公司编号 */
	private String corpId;

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setBalanceId(String balanceId) 
	{
		this.balanceId = balanceId;
	}

	public String getBalanceId() 
	{
		return balanceId;
	}
	public void setDistrictId(String districtId) 
	{
		this.districtId = districtId;
	}

	public String getDistrictId() 
	{
		return districtId;
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
	public void setPayPMoney(Float payPMoney) 
	{
		this.payPMoney = payPMoney;
	}

	public Float getPayPMoney() 
	{
		return payPMoney;
	}
	public void setIncomeMoney(Float incomeMoney) 
	{
		this.incomeMoney = incomeMoney;
	}

	public Float getIncomeMoney() 
	{
		return incomeMoney;
	}
	public void setPoundage(Float poundage) 
	{
		this.poundage = poundage;
	}

	public Float getPoundage() 
	{
		return poundage;
	}
	public void setPayRMoney(Float payRMoney) 
	{
		this.payRMoney = payRMoney;
	}

	public Float getPayRMoney() 
	{
		return payRMoney;
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
