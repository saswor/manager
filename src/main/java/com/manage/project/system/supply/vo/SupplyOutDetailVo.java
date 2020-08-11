package com.manage.project.system.supply.vo;

import java.util.List;

public class SupplyOutDetailVo {

	private List<SupplyOutVo> supplyOutProductInfo;
	private List<SupplyOutListVo> SupplyOut;

	public List<SupplyOutVo> getSupplyOutProductInfo() {
		return supplyOutProductInfo;
	}

	public void setSupplyOutProductInfo(List<SupplyOutVo> supplyOutProductInfo) {
		this.supplyOutProductInfo = supplyOutProductInfo;
	}

	public List<SupplyOutListVo> getSupplyOut() {
		return SupplyOut;
	}

	public void setSupplyOut(List<SupplyOutListVo> supplyOut) {
		SupplyOut = supplyOut;
	}

	
	
}
