package com.manage.project.system.vending.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 售货机货道商品表 as_vending_lanep
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingLanep extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 货道主键 */
	private String slaneId;
	/** 售货机编号 */
	private String siteId;
	/** 售货机名称 */
	private String siteName;
	/** 货道开始编号(一个商品占多个货道) */
	private Integer laneSId;
	/** 货道结束编号(如果只有一个商品则只有开始货道编号) */
	private Integer laneEId;
	/** 商品编号 */
	private String productId;
	/** 商品图片(json存储) */
	private String productPic;
	/** 售卖价格 */
	private Float salePrice;
	/** 存放容量 */
	private Integer capacity;
	/** 缺货阈值 */
	private Integer warnCap;
	/** 当前容量 */
	private Integer curCap;
	/** 商品状态 1:可售 2:过期 */
	private String productSate;
	/** 商品过期时间 */
	private String pStateTime;
	/** 货道状态 1:正常 2:停用 */
	private String laneSate;
	/** 货道停用时间 */
	private String lSateTime;
	/** 托管公司编号 */
	private String corpId;
	private String pic;	// 第一个图片

	private String productName;
	/**修正库存*/
	private Integer resetCap;
	
	/**重新出货数量*/
	private Integer outNum;

	public Integer getOutNum() {
		return outNum;
	}

	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPic() {
		return SystemUtil.jsonpicToPic(productPic);
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSlaneId(String slaneId) 
	{
		this.slaneId = slaneId;
	}

	public String getSlaneId() 
	{
		return slaneId;
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
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setProductPic(String productPic) 
	{
		this.productPic = productPic;
	}

	public String getProductPic() 
	{
		return productPic;
	}
	public void setSalePrice(Float salePrice) 
	{
		this.salePrice = salePrice;
	}

	public Float getSalePrice() 
	{
		return salePrice;
	}
	public void setCapacity(Integer capacity) 
	{
		this.capacity = capacity;
	}

	public Integer getCapacity() 
	{
		return capacity;
	}
	public void setWarnCap(Integer warnCap) 
	{
		this.warnCap = warnCap;
	}

	public Integer getWarnCap() 
	{
		return warnCap;
	}
	public void setCurCap(Integer curCap) 
	{
		this.curCap = curCap;
	}

	public Integer getCurCap() 
	{
		return curCap;
	}
	public void setProductSate(String productSate) 
	{
		this.productSate = productSate;
	}

	public String getProductSate() 
	{
		return productSate;
	}
	public void setPStateTime(String pStateTime) 
	{
		this.pStateTime = pStateTime;
	}

	public String getPStateTime() 
	{
		return pStateTime;
	}
	public void setLaneSate(String laneSate) 
	{
		this.laneSate = laneSate;
	}

	public String getLaneSate() 
	{
		return laneSate;
	}
	public void setLSateTime(String lSateTime) 
	{
		this.lSateTime = lSateTime;
	}

	public String getLSateTime() 
	{
		return lSateTime;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	

    public Integer getResetCap() {
		return resetCap;
	}

	public void setResetCap(Integer resetCap) {
		this.resetCap = resetCap;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("slaneId", getSlaneId())
            .append("siteId", getSiteId())
            .append("siteName", getSiteName())
            .append("laneSId", getLaneSId())
            .append("laneEId", getLaneEId())
            .append("productId", getProductId())
            .append("productPic", getProductPic())
            .append("salePrice", getSalePrice())
            .append("capacity", getCapacity())
            .append("warnCap", getWarnCap())
            .append("curCap", getCurCap())
            .append("productSate", getProductSate())
            .append("pStateTime", getPStateTime())
            .append("laneSate", getLaneSate())
            .append("lSateTime", getLSateTime())
            .append("createTime", getCreateTime())
            .append("corpId", getCorpId())
            .toString();
    }
}
