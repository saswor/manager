package com.manage.project.system.supply.vo;

import java.util.List;

import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * @ClassName SupplyOrderProductVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00$ 2018-10-12$
 **/
public class SupplyOrderVProductVo {
	@Excel(name="站点编号")
    private String siteId;
	@Excel(name="站点名称")
    private String siteName;
    private List<SupplyOrderProductVo> supplyInfo;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public List<SupplyOrderProductVo> getSupplyInfo() {
        return supplyInfo;
    }

    public void setSupplyInfo(List<SupplyOrderProductVo> supplyInfo) {
        this.supplyInfo = supplyInfo;
    }
}
