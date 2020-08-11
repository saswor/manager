package com.manage.project.system.vending.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.common.Constant;

/**
 * 软件版本信息
 * 
 * @author zhangjiawei
 *
 */
public class SoftwareVersion extends BaseEntity{

	private String logid;
	/**版本唯一号*/
	private String versionId;
	/**版本号*/
	private String vName;
	/**类型 1:app升级2:固件升级 3:vcs升级*/
	private String uType;
	/**文件数*/
	private Integer fileNum;
	/**文件大小(M)*/
	private Float fileSize;
	/**版本描述*/
	private String description;
	/**托管公司编号*/
	private String corpId;
	/**版本文件列表*/
	private List<SoftwareFile> softwareFileList=new ArrayList<SoftwareFile>();
	
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
	
	public String getSize() {
		if(fileSize==null) {
			return "0";
		}else {
			float size = new BigDecimal(fileSize/(1024*1024)).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
			return String.valueOf(size);
		}
		
	}
	
	public List<SoftwareFile> getSoftwareFileList() {
		return softwareFileList;
	}
	public void setSoftwareFileList(List<SoftwareFile> softwareFileList) {
		this.softwareFileList = softwareFileList;
	}
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getvName() {
		return vName;
	}
	public void setvName(String vName) {
		this.vName = vName;
	}
	public String getuType() {
		return uType;
	}
	public void setuType(String uType) {
		this.uType = uType;
	}
	public Integer getFileNum() {
		return fileNum;
	}
	public void setFileNum(Integer fileNum) {
		this.fileNum = fileNum;
	}
	public Float getFileSize() {
		return fileSize;
	}
	public void setFileSize(Float fileSize) {
		this.fileSize = fileSize;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	@Override
	public String toString() {
		return "SoftwareVersion [logid=" + logid + ", versionId=" + versionId + ", vName=" + vName + ", uType=" + uType
				+ ", fileNum=" + fileNum + ", fileSize=" + fileSize + ", description=" + description + ", corpId="
				+ corpId + ", softwareFileList=" + softwareFileList + "]";
	}
	
}
