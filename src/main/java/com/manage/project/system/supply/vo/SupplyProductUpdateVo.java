package com.manage.project.system.supply.vo;

import java.util.List;

public class SupplyProductUpdateVo {

	private String sOrderId;
	private List<SupplyProductListVo> supplyProductInfo;

	public String getsOrderId() {
		return sOrderId;
	}

	public void setsOrderId(String sOrderId) {
		this.sOrderId = sOrderId;
	}

	public List<SupplyProductListVo> getSupplyProductInfo() {
		return supplyProductInfo;
	}

	public void setSupplyProductInfo(List<SupplyProductListVo> supplyProductInfo) {
		this.supplyProductInfo = supplyProductInfo;
	}
	
	
}
