package com.manage.project.system.supply.vo;

import java.util.List;

/**
 * @ClassName WarnSupplyProductVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00:00$ 2018-10-10$
 **/
public class SupplyVorderDetailVo {
    private SupplyVorderVo supplyVorder;
    private List<SupplyOrderVProductVo> siteInfo;

    public List<SupplyOrderVProductVo> getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(List<SupplyOrderVProductVo> siteInfo) {
        this.siteInfo = siteInfo;
    }

	public SupplyVorderVo getSupplyVorder() {
		return supplyVorder;
	}

	public void setSupplyVorder(SupplyVorderVo supplyVorder) {
		this.supplyVorder = supplyVorder;
	}
}
