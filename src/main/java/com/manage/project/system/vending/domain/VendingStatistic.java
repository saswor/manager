package com.manage.project.system.vending.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 售货机业务数据统计表 as_vending_statistic
 * 
 * @author 张佳伟
 * @date 2018-10-23
 */
public class VendingStatistic extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 统计日期(yyyy-MM-dd) */
	private String rptdate;
	/** 区域编号 */
	private String districtId;
	/** 线路编号 */
	private String lineId;
	/** 点位编号 */
	private String pointId;
	/** 售货机编号 */
	private String siteId;
	/** 售货机名称 */
	private String siteName;
	/** 补货总数 */
	private Integer supplyNum;
	/** 历史交易总数 */
	private Integer orderNum;
	/** 网络掉线总次数 */
	private Integer offlineNum;
	/** 当前商品种类总数 */
	private Integer proTNum;
	/** 设备警告总数 */
	private Integer warnNum;
	/** 毛利总额 */
	private Float profitMoney;
	/** 托管公司编号 */
	private String corpId;
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
	public void setRptdate(String rptdate) 
	{
		this.rptdate = rptdate;
	}

	public String getRptdate() 
	{
		return rptdate;
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
	public void setPointId(String pointId) 
	{
		this.pointId = pointId;
	}

	public String getPointId() 
	{
		return pointId;
	}
	public void setSiteId(String siteId) 
	{
		this.siteId = siteId;
	}

	public String getSiteId() 
	{
		return siteId;
	}
	public void setSiteName(String siteName) 
	{
		this.siteName = siteName;
	}

	public String getSiteName() 
	{
		return siteName;
	}
	public void setSupplyNum(Integer supplyNum) 
	{
		this.supplyNum = supplyNum;
	}

	public Integer getSupplyNum() 
	{
		return supplyNum;
	}
	public void setOrderNum(Integer orderNum) 
	{
		this.orderNum = orderNum;
	}

	public Integer getOrderNum() 
	{
		return orderNum;
	}
	public void setOfflineNum(Integer offlineNum) 
	{
		this.offlineNum = offlineNum;
	}

	public Integer getOfflineNum() 
	{
		return offlineNum;
	}
	public void setProTNum(Integer proTNum) 
	{
		this.proTNum = proTNum;
	}

	public Integer getProTNum() 
	{
		return proTNum;
	}
	public void setWarnNum(Integer warnNum) 
	{
		this.warnNum = warnNum;
	}

	public Integer getWarnNum() 
	{
		return warnNum;
	}
	public void setProfitMoney(Float profitMoney) 
	{
		this.profitMoney = profitMoney;
	}

	public Float getProfitMoney() 
	{
		return profitMoney;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setCreateTime(String createTime) 
	{
		this.createTime = createTime;
	}

	public String getCreateTime() 
	{
		return createTime;
	}

}
