package com.manage.project.system.vending.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.common.Constant;

/**
 * 控制设备的升级，升级包括app升级、固件升级、视频软件升级表 as_vending_upgrade
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class VendingUpgrade extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 升级编号 */
	private String upgradeId;
	/** 升级名称 */
	private String uName;
	/** 升级类型 1:app升级2:固件升级 3:vcs升级 */
	private String uType;
	/** 下发类型 1:立即升级 2:延迟升级 */
	private String issuedType;
	/** 升级时间 */
	private String planTime;
	/** 生产厂家 */
	private String factoryId;
	/** 描述 */
	private String description;
	/** 升级文件 */
	private String versionId;
	/** 托管公司编号 */
	private String corpId;
	/** 是否推送 */
	private String isPush;

	public String getIsPush() {
		return isPush;
	}

	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}

	public String getuTypeName() {
		if(Constant.UPGRADE_TYPE_APP.equals(uType)) {
			return "app升级";
		}else if(Constant.UPGRADE_TYPE_FIRMWARE.equals(uType)) {
			return "固件升级";
		}else if(Constant.UPGRADE_TYPE_VCS.equals(uType)) {
			return "vcs升级";
		}else {
			return "";
		}
	}
	
	public String getIssuedTypeName() {
		if(Constant.UPGRADE_ISSUEDTYPE_NOW.equals(issuedType)) {
			return "立即升级";
		}else if(Constant.UPGRADE_ISSUEDTYPE_LATER.equals(issuedType)) {
			return "延迟升级";
		}else {
			return "";
		}
	}
	
	private List<VendingUpgradeTask> vendingUpgradeTaskList = new ArrayList<VendingUpgradeTask>();
	
	public List<VendingUpgradeTask> getVendingUpgradeTaskList() {
		return vendingUpgradeTaskList;
	}

	public void setVendingUpgradeTaskList(List<VendingUpgradeTask> vendingUpgradeTaskList) {
		this.vendingUpgradeTaskList = vendingUpgradeTaskList;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setUpgradeId(String upgradeId) 
	{
		this.upgradeId = upgradeId;
	}

	public String getUpgradeId() 
	{
		return upgradeId;
	}
	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuType() {
		return uType;
	}

	public void setuType(String uType) {
		this.uType = uType;
	}

	public void setIssuedType(String issuedType) 
	{
		this.issuedType = issuedType;
	}

	public String getIssuedType() 
	{
		return issuedType;
	}
	public void setPlanTime(String planTime) 
	{
		this.planTime = planTime;
	}

	public String getPlanTime() 
	{
		return planTime;
	}
	public void setFactoryId(String factoryId) 
	{
		this.factoryId = factoryId;
	}

	public String getFactoryId() 
	{
		return factoryId;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}
	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

	@Override
	public String toString() {
		return "VendingUpgrade [logid=" + logid + ", upgradeId=" + upgradeId + ", uName=" + uName + ", uType=" + uType
				+ ", issuedType=" + issuedType + ", planTime=" + planTime + ", factoryId=" + factoryId
				+ ", description=" + description + ", versionId=" + versionId + ", corpId=" + corpId + "]";
	}

}
