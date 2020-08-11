package com.manage.project.system.product.vo;

/**
 * 商品下架vo
 * 
 * @author 张佳伟
 * @date 2018-11-20
 */
public class UnderVo {
	/**记录编号,多个中间用逗号隔开*/
	private String ids;
	/**站点编号数组*/
	private String siteIds;
	/**商品编号*/
	private String productId;

	public String getSiteIds() {
		return siteIds;
	}

	public void setSiteIds(String siteIds) {
		this.siteIds = siteIds;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
}
