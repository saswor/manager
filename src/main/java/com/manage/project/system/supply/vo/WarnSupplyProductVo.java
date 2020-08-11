package com.manage.project.system.supply.vo;

import java.util.List;

/**
 * @ClassName WarnSupplyProductVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00:00$ 2018-10-10$
 **/
public class WarnSupplyProductVo {
    private List<VendingSupplyProductVo> siteInfo;
    private List<SupplyProductVo> 	sumInfo;

    public List<VendingSupplyProductVo> getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(List<VendingSupplyProductVo> siteInfo) {
        this.siteInfo = siteInfo;
    }

    public List<SupplyProductVo> getSumInfo() {
        return sumInfo;
    }

    public void setSumInfo(List<SupplyProductVo> sumInfo) {
        this.sumInfo = sumInfo;
    }
}
