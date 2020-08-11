package com.manage.project.system.product.vo;

import com.manage.project.system.product.domain.ProductOnline;

/**
 * 在线商品信息vo
 * 
 * @author zhangjiawei
 * @date 2018-11-19
 */
public class ProductOnlineVo extends ProductOnline{

	private static final long serialVersionUID = 1L;
	/**分类名称*/
	private String typeName;
	/**包装类型名称*/
	private String bagTypeName;
	/**商品编码*/
	private String productCode;
	/**商品规格*/
	private String spec;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getBagTypeName() {
		return bagTypeName;
	}

	public void setBagTypeName(String bagTypeName) {
		this.bagTypeName = bagTypeName;
	}
	

}
