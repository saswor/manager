package com.manage.project.system.product.vo;

import java.util.List;

import com.manage.project.system.vending.domain.VendingLanep;

/**
 * 下架商品vo
 * 
 * @author zhangjiawei
 *
 */
public class UnderLaneProductVo {

	private String siteId;
	private String ProductId;
	//下架的货道商品信息
	private List<VendingLanep> vendingLanepList;
	
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public List<VendingLanep> getVendingLanepList() {
		return vendingLanepList;
	}
	public void setVendingLanepList(List<VendingLanep> vendingLanepList) {
		this.vendingLanepList = vendingLanepList;
	}
		
}
