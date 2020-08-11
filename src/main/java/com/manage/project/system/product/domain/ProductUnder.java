package com.manage.project.system.product.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 商品下架表 as_product_under
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class ProductUnder extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 商品下架编号 */
	private String underId;
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
	/** 商品编号 */
	private String productId;
	/** 商品名称 */
	private String name;
	/** 商品全名 */
	private String fullName;
	/** 状态 1:等待下架 2:下架中 2:已下架 */
	private String curState;
	/** 状态时间 */
	private String stateTime;
	/** 下架类型 1:立即下架 2:延迟下架 */
	private String underType;
	/** 下架延迟时间 */
	private String planTime;
	/** 托管公司编号 */
	private String corpId;
	/**下架站点编号*/
	private String vUnderId;
	
	private Integer underNum;
	
	public Integer getUnderNum() {
		return underNum;
	}

	public void setUnderNum(Integer underNum) {
		this.underNum = underNum;
	}

	public String getvUnderId() {
		return vUnderId;
	}

	public void setvUnderId(String vUnderId) {
		this.vUnderId = vUnderId;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setUnderId(String underId) 
	{
		this.underId = underId;
	}

	public String getUnderId() 
	{
		return underId;
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
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setFullName(String fullName) 
	{
		this.fullName = fullName;
	}

	public String getFullName() 
	{
		return fullName;
	}
	public void setCurState(String curState) 
	{
		this.curState = curState;
	}

	public String getCurState() 
	{
		return curState;
	}
	public void setStateTime(String stateTime) 
	{
		this.stateTime = stateTime;
	}

	public String getStateTime() 
	{
		return stateTime;
	}
	public void setUnderType(String underType) 
	{
		this.underType = underType;
	}

	public String getUnderType() 
	{
		return underType;
	}
	public void setPlanTime(String planTime) 
	{
		this.planTime = planTime;
	}

	public String getPlanTime() 
	{
		return planTime;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("underId", getUnderId())
            .append("districtId", getDistrictId())
            .append("lineId", getLineId())
            .append("pointId", getPointId())
            .append("siteId", getSiteId())
            .append("siteName", getSiteName())
            .append("productId", getProductId())
            .append("name", getName())
            .append("fullName", getFullName())
            .append("curState", getCurState())
            .append("stateTime", getStateTime())
            .append("underType", getUnderType())
            .append("planTime", getPlanTime())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
