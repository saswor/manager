package com.manage.project.system.supply.vo;

import java.util.List;

/**
 * @ClassName VendingLanePVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date $ $
 **/
public class SupplyVProductAddVo {
    private String siteId;
    private List<SupplyProductAddVo> supplyInfo;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<SupplyProductAddVo> getSupplyInfo() {
        return supplyInfo;
    }

    public void setSupplyInfo(List<SupplyProductAddVo> supplyInfo) {
        this.supplyInfo = supplyInfo;
    }
}
