package com.manage.project.system.index.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 仪盘概要统计报(即时更新)表 as_report_board
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class ReportBoard implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 托管公司编号 */
	private String corpId;
	/** 售货机在线数 */
	private Integer onlineNum;
	/** 售货机离线数(小于24小时) */
	private Integer offlineNum;
	/** 售货机离线数(大于24小时) */
	private Integer offlineDayNum;
	/** 累计销售量 */
	private Integer saleNum;
	/** 累计销售额 */
	private Float saleMoney;
	/** 累计利润 */
	private Float profit;
	/** 柜子累计成本 */
	private Float vendingCost;
	/** 采购累计成本 */
	private Float productCost;
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
	public void setOnlineNum(Integer onlineNum) 
	{
		this.onlineNum = onlineNum;
	}

	public Integer getOnlineNum() 
	{
		return onlineNum;
	}
	public void setOfflineNum(Integer offlineNum) 
	{
		this.offlineNum = offlineNum;
	}

	public Integer getOfflineNum() 
	{
		return offlineNum;
	}
	public void setOfflineDayNum(Integer offlineDayNum) 
	{
		this.offlineDayNum = offlineDayNum;
	}

	public Integer getOfflineDayNum() 
	{
		return offlineDayNum;
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
	public void setVendingCost(Float vendingCost) 
	{
		this.vendingCost = vendingCost;
	}

	public Float getVendingCost() 
	{
		return vendingCost;
	}
	public void setProductCost(Float productCost) 
	{
		this.productCost = productCost;
	}

	public Float getProductCost() 
	{
		return productCost;
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
            .append("onlineNum", getOnlineNum())
            .append("offlineNum", getOfflineNum())
            .append("offlineDayNum", getOfflineDayNum())
            .append("saleNum", getSaleNum())
            .append("saleMoney", getSaleMoney())
            .append("profit", getProfit())
            .append("vendingCost", getVendingCost())
            .append("productCost", getProductCost())
            .append("createTime", getCreateTime())
            .toString();
    }
}
