package com.manage.project.system.vending.domain;

import com.manage.framework.web.domain.BaseEntity;
/**
 * 
 * @author ldh
 * @title VendingLsdiffer
 * @description TODO
 * @time 2019-01-16
 */
public class VendingLsdiffer extends BaseEntity{

	private static final long serialVersionUID = 6069298892809125428L;
	
	/**
	 * 记录编号
	 */
	private String logid;
	/**
	 * 货道差异编号
	 */
	private String lsdifferId;
	/**
	 * 售货机编号
	 */
	private String siteId;
	/**
	 * 售货机名称
	 */
	private String siteName;
	/**
	 * 货道开始编号
	 */
	private Integer laneSId;
	/**
	 * 货道结束编号
	 */
	private Integer laneEId;
	/**
	 * 当前商品编号
	 */
	private String productId;
	/**
	 * 当前商品名称
	 */
	private String productName;
	/**
	 * 当前库存量
	 */
	private Integer curCap;
	/**
	 * 设置后的库存
	 */
	private Integer resetCap;
	/**
	 * 差异数(绝对值)
	 */
	private Integer differNum;
	/**
	 * 已处理数
	 */
	private Integer handlerNum;
	/**
	 * 需处理类型    1:入库 2:出库
	 */
	private String handleType;
	/**
	 * 需处理类型    1:入库 2:出库
	 */
	private String handleTypeName;
	/**
	 * 状态变化时间
	 */
	private String stateTime;
	/**
	 * 状态 1:待处理 2:超期 2:已处理
	 */
	private String curState;
	/**
	 * 状态 1:待处理 2:超期 2:已处理
	 */
	private String curStateName;
	/**
	 * 创建人编号
	 */
	private String createrId;
	/**
	 * 创建人姓名
	 */
	private String createrName;
	/**
	 * 操作时间
	 */
	private String operTime;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 托管公司编号
	 */
	private String corpId;
	
	//虚拟字段
	/**
	 * 开始时间
	 */
	private String btime;	
	/**
	 * 结束时间
	 */
	private String etime;	 
	
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getLsdifferId() {
		return lsdifferId;
	}
	public void setLsdifferId(String lsdifferId) {
		this.lsdifferId = lsdifferId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public Integer getLaneSId() {
		return laneSId;
	}
	public void setLaneSId(Integer laneSId) {
		this.laneSId = laneSId;
	}
	public Integer getLaneEId() {
		return laneEId;
	}
	public void setLaneEId(Integer laneEId) {
		this.laneEId = laneEId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getCurCap() {
		return curCap;
	}
	public void setCurCap(Integer curCap) {
		this.curCap = curCap;
	}
	public Integer getResetCap() {
		return resetCap;
	}
	public void setResetCap(Integer resetCap) {
		this.resetCap = resetCap;
	}
	public Integer getDifferNum() {
		return differNum;
	}
	public void setDifferNum(Integer differNum) {
		this.differNum = differNum;
	}
	public Integer getHandlerNum() {
		return handlerNum;
	}
	public void setHandlerNum(Integer handlerNum) {
		this.handlerNum = handlerNum;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	public String getStateTime() {
		return stateTime;
	}
	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getHandleTypeName() {
		return handleTypeName;
	}
	public void setHandleTypeName(String handleTypeName) {
		this.handleTypeName = handleTypeName;
	}
	public String getCurStateName() {
		return curStateName;
	}
	public void setCurStateName(String curStateName) {
		this.curStateName = curStateName;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}	
	
}
