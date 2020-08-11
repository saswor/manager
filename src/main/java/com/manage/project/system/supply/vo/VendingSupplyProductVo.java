package com.manage.project.system.supply.vo;

import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;

import java.util.List;

/**
 * @ClassName VendingLanePVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date $ $
 **/
public class VendingSupplyProductVo {
    private String siteId;
    private String siteName;
    private String pointId;
    private String pointName;
    private List<SupplyProductVo> supplyInfo;
    
    public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

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

    public List<SupplyProductVo> getSupplyInfo() {
        return supplyInfo;
    }

    public void setSupplyInfo(List<SupplyProductVo> supplyInfo) {
        this.supplyInfo = supplyInfo;
    }
}
