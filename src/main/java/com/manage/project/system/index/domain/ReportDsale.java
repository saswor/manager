package com.manage.project.system.index.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 每天销售统计情况，可从售货机时间段统计统计出来每隔一段时间统计一次，即时更新表 as_report_dsale
 * 
 * @author xufeng
 */
public class ReportDsale implements Serializable
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
	/** 交易日期 */
	private Integer sday;
	/** 交易开始时间段(HH:mm) 每隔一小时统计一次 */
	private String stime;
	/** 交易结束时间段(HH:mm) 每隔一小时统计一次 */
	private String etime;
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
	public void setSday(Integer sday) 
	{
		this.sday = sday;
	}

	public Integer getSday() 
	{
		return sday;
	}
	public void setStime(String stime) 
	{
		this.stime = stime;
	}

	public String getStime() 
	{
		return stime;
	}
	public void setEtime(String etime) 
	{
		this.etime = etime;
	}

	public String getEtime() 
	{
		return etime;
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
            .append("sday", getSday())
            .append("stime", getStime())
            .append("etime", getEtime())
            .append("saleNum", getSaleNum())
            .append("saleMoney", getSaleMoney())
            .append("profit", getProfit())
            .append("createTime", getCreateTime())
            .toString();
    }
}
