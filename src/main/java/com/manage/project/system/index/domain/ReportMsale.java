package com.manage.project.system.index.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 仪盘概要统计报表 as_report_msale
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class ReportMsale implements Serializable
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
            .append("saleNum", getSaleNum())
            .append("saleMoney", getSaleMoney())
            .append("profit", getProfit())
            .append("createTime", getCreateTime())
            .toString();
    }
}
