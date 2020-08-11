package com.manage.project.system.vending.vo;

import com.manage.project.system.util.SystemUtil;

/**
 * 模板关联货柜查询结果使用
 * @author xufeng
 *
 */
public class RelationSelectCabinetVo {
	private String cabinetId;
	// 货柜名称
	private String cabinetName;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetType;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetTypeName;
	public String getCabinetId() {
		return cabinetId;
	}
	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}
	public String getCabinetName() {
		return cabinetName;
	}
	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}
	public String getCabinetType() {
		return cabinetType;
	}
	public void setCabinetType(String cabinetType) {
		this.cabinetType = cabinetType;
	}
	public String getCabinetTypeName() {
		return SystemUtil.parseCabinetType(cabinetType);
	}
}
