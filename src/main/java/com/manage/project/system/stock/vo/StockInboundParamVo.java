package com.manage.project.system.stock.vo;

/**
 * 入库管理查询条件
 * @author xufeng
 *
 */
public class StockInboundParamVo {
	private String createTime;
	/** 入库人姓名 */
	private String inboundName;
	/** 仓库名称 */
	private String stokcName;
	/** 当前状态 1:未入库 2:已入库 */
	private String curState;
	/** 仓库入库记录编号 */
	private String wInboundId;
	/** 仓采购记录编号 */
	private String wpurchaseId;
	private String corpId;
	
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getInboundName() {
		return inboundName;
	}
	public void setInboundName(String inboundName) {
		this.inboundName = inboundName;
	}
	public String getStokcName() {
		return stokcName;
	}
	public void setStokcName(String stokcName) {
		this.stokcName = stokcName;
	}
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
	public String getwInboundId() {
		return wInboundId;
	}
	public void setwInboundId(String wInboundId) {
		this.wInboundId = wInboundId;
	}
	public String getWpurchaseId() {
		return wpurchaseId;
	}
	public void setWpurchaseId(String wpurchaseId) {
		this.wpurchaseId = wpurchaseId;
	}
}
