package com.manage.project.system.product.domain;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 下架站点信息
 * 
 * @author zhangjiawei
 *
 */
public class ProductVunder extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String logid;
	
	private String vUnderId;
	
	private String loginId;
	
	private String corpId;
	
	private String siteId;
	
	private Integer underNum;
	
	private String curState;
	
	private String stateTime;
	
	private String lineId;

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getvUnderId() {
		return vUnderId;
	}

	public void setvUnderId(String vUnderId) {
		this.vUnderId = vUnderId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
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

	public Integer getUnderNum() {
		return underNum;
	}

	public void setUnderNum(Integer underNum) {
		this.underNum = underNum;
	}
	
	
}
