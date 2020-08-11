package com.manage.project.system.supply.vo;

/**
 * @ClassName WarnLineVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 18:00:00$ 2018-10-10$
 **/
public class WarnPointVo {
    private String siteId;
    private String siteName;
    private String pointId;
    private String pointName;
    private String districtName;
    private String address;
    private Integer supplyNum;
    private Integer storyLevel;

    /**当前库存占最大百分比*/
    private int storyPercent;

    public int getStoryPercent() {
		return storyPercent;
	}

	public void setStoryPercent(int storyPercent) {
		this.storyPercent = storyPercent;
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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(Integer supplyNum) {
        this.supplyNum = supplyNum;
    }

    public Integer getStoryLevel() {
        return storyLevel;
    }

    public void setStoryLevel(Integer storyLevel) {
        this.storyLevel = storyLevel;
    }

}
