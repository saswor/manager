package com.manage.project.system.vending.domain;

import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.common.Constant;
import com.manage.project.system.util.SystemUtil;

/**
 * 售货机日志文件信息
 * 
 * @author zhangjiawei
 *
 */
public class VendingLogfile extends BaseEntity{
	/**记录编号*/
	private String logid;
	/**售货机日志文件编号*/
	private String fileId;
	/**售货机编号*/
	private String siteId;
	/**售货机编码*/
	private String siteCode;
	/**开始时间*/
	private String sTime;
	/**结束时间*/
	private String eTime;
	/**文件大小*/
	private Float fileSize;
	/**文件数量*/
	private int fileNum;
	/**文件云地址 如果多个文件则用|分割,如果多个文件采用fileId+”_(从1递增)”开始,如果没有则fileId_end结尾*/
	private String fileUrl;
	/**文件本地路径 如果多个文件则用|分割*/
	private String filePath;
	/**上传状态 1:等待上传 2:上传中 3:上传完成*/
	private String curState;
	/**状态时间*/
	private String stateTime;
	/**托管公司编号*/
	private String corpId;
	
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public String getSiteName() {
		return SystemUtil.getVendingNameBySiteId(siteId);
	}
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public String geteTime() {
		return eTime;
	}
	public void seteTime(String eTime) {
		this.eTime = eTime;
	}
	public Float getFileSize() {
		return fileSize;
	}
	public void setFileSize(Float fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public int getFileNum() {
		return fileNum;
	}
	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCurState() {
		return curState;
	}
	public String getCurStateName() {
		if(Constant.VENDING_LOGFILE_CURSTATE_WAIT.equals(curState)) {
			return "等待上传";
		}else if(Constant.VENDING_LOGFILE_CURSTATE_UPLOAD.equals(curState)) {
			return "上传中";
		}else if(Constant.VENDING_LOGFILE_CURSTATE_FINISH.equals(curState)) {
			return "上传完成";
		}else {
			return "";
		}
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
	public String getStateTime() {
		return stateTime;
	}
	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	
	
}
