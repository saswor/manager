package com.manage.project.system.supply.vo;

import java.util.List;

/**
 * @ClassName WarnSupplyProductVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00:00$ 2018-10-10$
 **/
public class SupplyOrderDetailVo {
    private SupplyOrderVo supplyOrder;
    private List<SupplyOrderVProductVo> siteInfo;
    private List<SupplyOrderProductVo> 	sumInfo;

    public List<SupplyOrderVProductVo> getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(List<SupplyOrderVProductVo> siteInfo) {
        this.siteInfo = siteInfo;
    }

    public List<SupplyOrderProductVo> getSumInfo() {
        return sumInfo;
    }

    public void setSumInfo(List<SupplyOrderProductVo> sumInfo) {
        this.sumInfo = sumInfo;
    }

    public SupplyOrderVo getSupplyOrder() {
        return supplyOrder;
    }

    public void setSupplyOrder(SupplyOrderVo supplyOrder) {
        this.supplyOrder = supplyOrder;
    }
}
