package com.manage.project.system.supply.vo;

import com.manage.project.system.supply.domain.SupplyOrder;

/**
 * 售货机补货记录vo
 * 
 * @author zhangjiawei
 *
 */
public class SupplyVendingVo extends SupplyOrder{

	private static final long serialVersionUID = 1L;
	
	private String siteId;

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	
}
