package com.manage.project.system.advert.vo;

import com.manage.common.utils.StringUtils;
import com.manage.project.system.product.domain.ProductClassify;
import com.manage.project.system.util.SystemUtil;

/**
 * 折扣商品查看
 * @author xufeng
 *
 */
public class ProductFavViewVo {
	
	private String productId;
	
	private String name;
	
	private Float salePrice;
	/** 分类编号 */
	private String typeId;
	
	private String typeName;	// 分类名称
	/** 包装类型1:瓶装 2:灌装 3:袋装 4:盒装 5:杯装 */
	private String bagType;
	
	private String bagTypeName;	// 包装形式名称

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		ProductClassify productClassify = SystemUtil.getProductClassify(typeId);
		if (productClassify == null) {
			return "-";
		}
		return productClassify.getClassifyName();
	}

	public String getBagType() {
		return bagType;
	}

	public void setBagType(String bagType) {
		this.bagType = bagType;
	}

	public String getBagTypeName() {
		String name = SystemUtil.parseBagType(bagType);
		if (StringUtils.isEmpty(name)) {
			return "-";
		}
		return name;
	}
}
