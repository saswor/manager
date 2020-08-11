package com.manage.project.system.supply.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @ClassName WarnLineDetailVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00:00$ 2018-10-09 $
 **/
public class SupplyVorderVo {
    private static final long serialVersionUID = 1L;

    /** 补货编号 */
    private String vorderId;
    /** 补货完成时间 (yyyy-MM-dd HH:mm:ss) */
    private String supplyFTime;
    /** 线路编号 */
    private String lineId;
    /** 线路编号 */
    private String lineName;
    /** 仓库编号 */
    private String wmId;
    private String wmName;
    /** 补货员编号 */
    private String supplierId;
    /** 补货员编号 */
    private String supplierName;
    private String curState;
    private String curStateName;
    private Integer supplyNum;
	public String getVorderId() {
		return vorderId;
	}
	public void setVorderId(String vorderId) {
		this.vorderId = vorderId;
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
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
	public String getCurStateName() {
		return curStateName;
	}
	public void setCurStateName(String curStateName) {
		this.curStateName = curStateName;
	}
	public Integer getSupplyNum() {
		return supplyNum;
	}
	public void setSupplyNum(Integer supplyNum) {
		this.supplyNum = supplyNum;
	}
    
    
}
