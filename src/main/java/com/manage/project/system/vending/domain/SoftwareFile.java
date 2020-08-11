package com.manage.project.system.vending.domain;

import java.math.BigDecimal;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 软件文件下载信息表
 * 
 * @author zhangjiawei
 *
 */
public class SoftwareFile extends BaseEntity{

	private String logid;
	/**软件文件唯一识别*/
	private String vFileId;
	/**版本唯一识别号*/
	private String versionId;
	/**文件名称*/
	private String fileName;
	/**文件大小(M)*/
	private Float fileSize;
	/**文件格式(zip,apk)*/
	private String fileType;
	/**云文件地址*/
	private String filePath;
	/**本地文件地址*/
	private String localPath;
	/**排序号*/
	private Integer seqId;
	/**文件MD5值*/
	private String fileMDF;
	/**托管公司编号*/
	private String corpId;

	public String getFileMDF() {
		return fileMDF;
	}


	public void setFileMDF(String fileMDF) {
		this.fileMDF = fileMDF;
	}


	public String getLogid() {
		return logid;
	}


	public void setLogid(String logid) {
		this.logid = logid;
	}


	public String getvFileId() {
		return vFileId;
	}


	public void setvFileId(String vFileId) {
		this.vFileId = vFileId;
	}


	public String getVersionId() {
		return versionId;
	}


	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public Float getFileSize() {
		return fileSize;
	}


	public void setFileSize(Float fileSize) {
		this.fileSize = fileSize;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getLocalPath() {
		return localPath;
	}


	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}


	public Integer getSeqId() {
		return seqId;
	}


	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}


	public String getCorpId() {
		return corpId;
	}


	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	
	public String getSize() {
		float size = new BigDecimal(fileSize/(1024*1024)).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
		return String.valueOf(size);
	}

	@Override
	public String toString() {
		return "SoftwareFile [logid=" + logid + ", vFileId=" + vFileId + ", versionId=" + versionId + ", fileName="
				+ fileName + ", fileSize=" + fileSize + ", fileType=" + fileType + ", filePath=" + filePath
				+ ", localPath=" + localPath + ", seqId=" + seqId + ", corpId=" + corpId + "]";
	}
	
}
