package com.manage.project.system.statement.vo;

/**
 * 订单取消定时任务查询条件
 * 
 * @author zhangjiawei
 *
 */
public class OrderCancelVo {
	
	/**商户编号*/
	String corpId;
	/**支付状态*/
	private String payState;
	/**当前状态*/
	private String curState;
	/**异常状态*/
	private String abnomarlState;
	/**开始时间*/
	private String beginTime;
	/**结束时间*/
	private String endTime;
	
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getAbnomarlState() {
		return abnomarlState;
	}
	public void setAbnomarlState(String abnomarlState) {
		this.abnomarlState = abnomarlState;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
