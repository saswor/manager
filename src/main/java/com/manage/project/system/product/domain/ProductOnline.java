package com.manage.project.system.product.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 记录在线购买的商品表 as_product_online
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class ProductOnline extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 商品编号 */
	private String productId;
	/** 商品名称 */
	private String name;
	/** 商品全名 */
	private String fullName;
	/** 分类编号 */
	private String typeId;
	/** 售卖价 */
	private Float salePrice;
	/** 包装类型1:瓶装 2:灌装 3:袋装 4:盒装 5:杯装 */
	private String bagType;
	/** 生产厂家 */
	private String factoryName;
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
	public void setBagType(String bagType) 
	{
		this.bagType = bagType;
	}

	public String getBagType() 
	{
		return bagType;
	}
	public void setFactoryName(String factoryName) 
	{
		this.factoryName = factoryName;
	}

	public String getFactoryName() 
	{
		return factoryName;
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
            .append("productId", getProductId())
            .append("name", getName())
            .append("fullName", getFullName())
            .append("typeId", getTypeId())
            .append("salePrice", getSalePrice())
            .append("bagType", getBagType())
            .append("factoryName", getFactoryName())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
