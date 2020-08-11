package com.manage.project.system.supply.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 补货记录信息
 * 
 * @author xufeng
 * @date 2018-12-22
 */
public class SupplyVorder extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	private String vorderId;
	/** 托管公司编号 */
	private String corpId;
	private String sorderId;
	private String supplierId;
	private String supplyId;
	private String curState;
	private String stateTime;
	private String siteId;
	private String lineId;
	private Integer supplyNum;
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getVorderId() {
		return vorderId;
	}
	public void setVorderId(String vorderId) {
		this.vorderId = vorderId;
	}	
	public String getvOrderId() {
		return vorderId;
	}
	public void setvOrderId(String vorderId) {
		this.vorderId = vorderId;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getSorderId() {
		return sorderId;
	}
	public void setSorderId(String sorderId) {
		this.sorderId = sorderId;
	}
	public String getsOrderId() {
		return sorderId;
	}
	public void setsOrderId(String sorderId) {
		this.sorderId = sorderId;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}
	public String getCurState() {
		return curState;
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
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public Integer getSupplyNum() {
		return supplyNum;
	}
	public void setSupplyNum(Integer supplyNum) {
		this.supplyNum = supplyNum;
	}

}

