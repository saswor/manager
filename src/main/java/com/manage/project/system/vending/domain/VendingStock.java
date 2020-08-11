package com.manage.project.system.vending.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 售货机商品库存，提供下单检查初步检查库存情况。表 as_vending_stock
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class VendingStock extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 主键编号 */
	private String wproductId;
	/** 公司编号 */
	private String corpId;
	/** 售货机编号 */
	private String siteId;
	/** 售货机名称 */
	private String siteName;
	/** 商品编号 */
	private String productId;
	/** 商品名称 */
	private String productName;
	/** 当前商品可售卖数量 */
	private String num;
	/** 当前商品需回收库存数 */
	private String recoveryNum;
	/** 当前商品过期库存数 */
	private String overdueNum;
	/** 分类编号 */
	private String typeId;
	/** 售卖价 */
	private Float salePrice;
	/** bcode对应的商品图片地址 */
	private String picUrl;
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
	public void setWproductId(String wproductId) 
	{
		this.wproductId = wproductId;
	}

	public String getWproductId() 
	{
		return wproductId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
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
	public void setProductName(String productName) 
	{
		this.productName = productName;
	}

	public String getProductName() 
	{
		return productName;
	}
	public void setNum(String num) 
	{
		this.num = num;
	}

	public String getNum() 
	{
		return num;
	}
	public void setRecoveryNum(String recoveryNum) 
	{
		this.recoveryNum = recoveryNum;
	}

	public String getRecoveryNum() 
	{
		return recoveryNum;
	}
	public void setOverdueNum(String overdueNum) 
	{
		this.overdueNum = overdueNum;
	}

	public String getOverdueNum() 
	{
		return overdueNum;
	}
	public void setTypeId(String typeId) 
	{
		this.typeId = typeId;
	}

	public String getTypeId() 
	{
		return typeId;
	}
	public void setSalePrice(Float salePrice) 
	{
		this.salePrice = salePrice;
	}

	public Float getSalePrice() 
	{
		return salePrice;
	}
	public void setPicUrl(String picUrl) 
	{
		this.picUrl = picUrl;
	}

	public String getPicUrl() 
	{
		return picUrl;
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
            .append("wproductId", getWproductId())
            .append("corpId", getCorpId())
            .append("siteId", getSiteId())
            .append("siteName", getSiteName())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("num", getNum())
            .append("recoveryNum", getRecoveryNum())
            .append("overdueNum", getOverdueNum())
            .append("typeId", getTypeId())
            .append("salePrice", getSalePrice())
            .append("picUrl", getPicUrl())
            .append("createTime", getCreateTime())
            .toString();
    }
}
