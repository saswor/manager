package com.manage.project.system.supply.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 补货配置的售货机表 as_supply_vending
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class SupplyVending extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 补货配置编号 */
	private String supplyId;
	/** 售货机编号 */
	private String siteId;
	/** 补货名称 */
	private String name;
	/** 托管公司编号 */
	private String corpId;
	/** 线路编号 */
	private String lineId;
	/** 当前商品容量 */
	private Integer curPNum;
	/** 商品最大存放量 */
	private Integer maxPNum;
	/** 库存等级 1,2,3 */
	private Integer storyLevel;
	/** 第一等级库存 最小到最大百分比 整数-整数  10-30代表10%到30% */
	private String fristlevel;
	/** 第二等级库存 最小到最大百分比 */
	private String twoLevel;
	/** 第三等级库存 最小到最大百分比 */
	private String threeLevel;
	/** 售货机名称 */
	private String siteName;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSupplyId(String supplyId) 
	{
		this.supplyId = supplyId;
	}

	public String getSupplyId() 
	{
		return supplyId;
	}
	public void setSiteId(String siteId) 
	{
		this.siteId = siteId;
	}

	public String getSiteId() 
	{
		return siteId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setLineId(String lineId) 
	{
		this.lineId = lineId;
	}

	public String getLineId() 
	{
		return lineId;
	}
	public void setCurPNum(Integer curPNum) 
	{
		this.curPNum = curPNum;
	}

	public Integer getCurPNum() 
	{
		return curPNum;
	}
	public void setMaxPNum(Integer maxPNum) 
	{
		this.maxPNum = maxPNum;
	}

	public Integer getMaxPNum() 
	{
		return maxPNum;
	}
	public void setStoryLevel(Integer storyLevel) 
	{
		this.storyLevel = storyLevel;
	}

	public Integer getStoryLevel() 
	{
		return storyLevel;
	}
	public void setFristlevel(String fristlevel) 
	{
		this.fristlevel = fristlevel;
	}

	public String getFristlevel() 
	{
		return fristlevel;
	}
	public void setTwoLevel(String twoLevel) 
	{
		this.twoLevel = twoLevel;
	}

	public String getTwoLevel() 
	{
		return twoLevel;
	}
	public void setThreeLevel(String threeLevel) 
	{
		this.threeLevel = threeLevel;
	}

	public String getThreeLevel() 
	{
		return threeLevel;
	}


    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("supplyId", getSupplyId())
            .append("siteId", getSiteId())
            .append("name", getName())
            .append("corpId", getCorpId())
            .append("lineId", getLineId())
            .append("curPNum", getCurPNum())
            .append("maxPNum", getMaxPNum())
            .append("storyLevel", getStoryLevel())
            .append("fristlevel", getFristlevel())
            .append("twoLevel", getTwoLevel())
            .append("threeLevel", getThreeLevel())
            .append("createTime", getCreateTime())
            .toString();
    }
}
