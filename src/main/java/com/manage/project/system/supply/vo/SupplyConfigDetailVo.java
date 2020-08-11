package com.manage.project.system.supply.vo;

import java.util.Date;

/**
 * @ClassName WarnLineDetailVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00:00$ 2018-10-09 $
 **/
public class SupplyConfigDetailVo {
    private String supplyName;
    private String description;
    private String Strategy;
    private String fristlevel;
    private String twolevel;
    private String threelevel;
    private String type;
    private String wmId;
    private String wmName;
    private String supplierId;
    private String supplierName;
    private Integer advTime;
    private String lineId;
    private String createTime;
    private String supplyId;
    /**补货名称*/
    public String getName() {
		return supplyName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStrategy() {
        return Strategy;
    }

    public void setStrategy(String strategy) {
        Strategy = strategy;
    }

    public String getFristlevel() {
        return fristlevel;
    }

    public void setFristlevel(String fristlevel) {
        this.fristlevel = fristlevel;
    }

    public String getTwolevel() {
        return twolevel;
    }

    public void setTwolevel(String twolevel) {
        this.twolevel = twolevel;
    }

    public String getThreelevel() {
        return threelevel;
    }

    public void setThreelevel(String threelevel) {
        this.threelevel = threelevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWmId() {
        return wmId;
    }

    public void setWmId(String wmId) {
        this.wmId = wmId;
    }

    public String getWmName() {
        return wmName;
    }

    public void setWmName(String wmName) {
        this.wmName = wmName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }



    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public Integer getAdvTime() {
        return advTime;
    }

    public void setAdvTime(Integer advTime) {
        this.advTime = advTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
