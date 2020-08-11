package com.manage.project.system.vending.vo;

/**
 * 货柜类型下拉
 * @author xufeng
 *
 */
public class CabinetTypeSelectVo {
	
	private String factoryId;	// 厂商编号
	private String cabinetType;	// 货柜类型
	private String cabinetTypeName;	// 货柜类型名称

	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getCabinetType() {
		return cabinetType;
	}
	public void setCabinetType(String cabinetType) {
		this.cabinetType = cabinetType;
	}
	public String getCabinetTypeName() {
		return cabinetTypeName;
	}
	public void setCabinetTypeName(String cabinetTypeName) {
		this.cabinetTypeName = cabinetTypeName;
	}
	
	public String getCabinetTypeExcel() {
		return cabinetTypeName+cabinetType;
	}
	
}
