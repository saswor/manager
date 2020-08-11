package com.manage.project.system.product.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 站点货道商品下架表 as_product_lunder
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class ProductLunder extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 下架记录编号 */
	private String lunderId;
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
	/** 下架数量 */
	private Integer underNum;
	/** 货道开始编号 */
	private Integer laneSId;
	/** 货道结束编号 */
	private Integer laneEId;
	/** 托管公司编号 */
	private String corpId;
	/** 创建时间 */
	private String createTime;
	
	private String vUnderId;
	
	private String curState;

	public String getvUnderId() {
		return vUnderId;
	}

	public void setvUnderId(String vUnderId) {
		this.vUnderId = vUnderId;
	}

	public String getCurState() {
		return curState;
	}

	public void setCurState(String curState) {
		this.curState = curState;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setLunderId(String lunderId) 
	{
		this.lunderId = lunderId;
	}

	public String getLunderId() 
	{
		return lunderId;
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
	public void setUnderNum(Integer underNum) 
	{
		this.underNum = underNum;
	}

	public Integer getUnderNum() 
	{
		return underNum;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("lunderId", getLunderId())
            .append("underId", getUnderId())
            .append("districtId", getDistrictId())
            .append("lineId", getLineId())
            .append("pointId", getPointId())
            .append("siteId", getSiteId())
            .append("siteName", getSiteName())
            .append("productId", getProductId())
            .append("underNum", getUnderNum())
            .append("laneSId", getLaneSId())
            .append("laneEId", getLaneEId())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
