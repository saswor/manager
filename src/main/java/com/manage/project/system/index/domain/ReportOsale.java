package com.manage.project.system.index.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 每天销量排行统计表 as_report_osale
 * 
 * @author xufeng
 */
public class ReportOsale implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 托管公司编号 */
	private String corpId;
	/** 交易年份 */
	private Integer syear;
	/** 交易月份 */
	private Integer smonth;
	/** 交易日 */
	private Integer sDay;
	/** 对象 1:商品 2:点位 2:线路 */
	private String saleType;
	/** 对象编号 */
	private String saleId;
	/** 对象名称 */
	private String saleName;
	/** 累计销售量 */
	private Integer saleNum;
	/** 累计销售额 */
	private Float saleMoney;
	/** 累计利润 */
	private Float profit;
	/** 创建时间 */
	private String createTime;

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setSyear(Integer syear) 
	{
		this.syear = syear;
	}

	public Integer getSyear() 
	{
		return syear;
	}
	public void setSmonth(Integer smonth) 
	{
		this.smonth = smonth;
	}

	public Integer getSmonth() 
	{
		return smonth;
	}
	public void setSDay(Integer sDay) 
	{
		this.sDay = sDay;
	}

	public Integer getSDay() 
	{
		return sDay;
	}
	public void setSaleType(String saleType) 
	{
		this.saleType = saleType;
	}

	public String getSaleType() 
	{
		return saleType;
	}
	public void setSaleId(String saleId) 
	{
		this.saleId = saleId;
	}

	public String getSaleId() 
	{
		return saleId;
	}
	public void setSaleName(String saleName) 
	{
		this.saleName = saleName;
	}

	public String getSaleName() 
	{
		return saleName;
	}
	public void setSaleNum(Integer saleNum) 
	{
		this.saleNum = saleNum;
	}

	public Integer getSaleNum() 
	{
		return saleNum;
	}
	public void setSaleMoney(Float saleMoney) 
	{
		this.saleMoney = saleMoney;
	}

	public Float getSaleMoney() 
	{
		return saleMoney;
	}
	public void setProfit(Float profit) 
	{
		this.profit = profit;
	}

	public Float getProfit() 
	{
		return profit;
	}
	public void setCreateTime(String createTime) 
	{
		this.createTime = createTime;
	}

	public String getCreateTime() 
	{
		return createTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("corpId", getCorpId())
            .append("syear", getSyear())
            .append("smonth", getSmonth())
            .append("sDay", getSDay())
            .append("saleType", getSaleType())
            .append("saleId", getSaleId())
            .append("saleName", getSaleName())
            .append("saleNum", getSaleNum())
            .append("saleMoney", getSaleMoney())
            .append("profit", getProfit())
            .append("createTime", getCreateTime())
            .toString();
    }
}
