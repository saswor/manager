package com.manage.project.system.supply.vo;

import java.util.Date;

/**
 * @ClassName WarnLineDetailVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00:00$ 2018-10-09 $
 **/
public class SupplyConfigListVo {
    private String supplyName;
    private String lineId;
    private String lineName;
    private String districtName;
    private Integer storyLevel;
    private String  wmName;
    private Integer everyTime;
    private String supplierName;
    private String createTime;
    private String supplyId;
    /**补货站点数*/
    private Integer num;

    public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getStoryLevel() {
        return storyLevel;
    }

    public void setStoryLevel(Integer storyLevel) {
        this.storyLevel = storyLevel;
    }

    public String getWmName() {
        return wmName;
    }

    public void setWmName(String wmName) {
        this.wmName = wmName;
    }

    public Integer getEveryTime() {
        return everyTime;
    }

    public void setEveryTime(Integer everyTime) {
        this.everyTime = everyTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
