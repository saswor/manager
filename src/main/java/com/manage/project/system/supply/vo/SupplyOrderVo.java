package com.manage.project.system.supply.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * @ClassName WarnLineDetailVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00:00$ 2018-10-09 $
 **/
public class SupplyOrderVo {
    private static final long serialVersionUID = 1L;

    /** 补货编号 */
    @Excel(name="补货编号")
    private String sOrderId;
    /** 补货完成时间 (yyyy-MM-dd HH:mm:ss) */
    @Excel(name="补货完成时间")
    private String supplyFTime;
    /** 线路编号 */
    @Excel(name="线路编号")
    private String lineId;
    /** 线路名称 */
    @Excel(name="线路名称")
    private String lineName;
    /** 仓库编号 */
    @Excel(name="仓库编号")
    private String wmId;
    @Excel(name="仓库名称")
    private String wmName;
    /** 补货员编号 */
    @Excel(name="补货员编号")
    private String supplierId;
    /** 补货员名称 */
    @Excel(name="补货员名称")
    private String supplierName;
    /** 补货站点数量 */
    @Excel(name="补货站点数量")
    private Integer num;
    /** 补货类型 1:全补齐 2:阈值补齐 */
    private String type;
    
    @Excel(name="需补货总数")
    private Integer supplyNum;
    @Excel(name="实际补货总数")
    private Integer rSupplyNum;
    @Excel(name="补货类型")
    private String typeName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getsOrderId() {
        return sOrderId;
    }

    public void setsOrderId(String sOrderId) {
        this.sOrderId = sOrderId;
    }

    public String getSupplyFTime() {
        return supplyFTime;
    }

    public void setSupplyFTime(String supplyFTime) {
        this.supplyFTime = supplyFTime;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSupplyNum() {
		return supplyNum;
	}

	public void setSupplyNum(Integer supplyNum) {
		this.supplyNum = supplyNum;
	}

	public Integer getrSupplyNum() {
		return rSupplyNum;
	}

	public void setrSupplyNum(Integer rSupplyNum) {
		this.rSupplyNum = rSupplyNum;
	}
    
}
