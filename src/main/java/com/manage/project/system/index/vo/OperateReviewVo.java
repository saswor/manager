package com.manage.project.system.index.vo;

import java.io.Serializable;

/**
 * 首页运营总鉴
 * @author xufeng
 *
 */
public class OperateReviewVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String totalSale = "0";	// 累计销售额
	private String totalSaleNum = "0";	// 累计销售量
	private String totalProfit = "0";	// 累计净利润
	private String onlineMachine = "0";	// 在线设备
	private String outlineMachine = "0";	// 离线设备
	public String getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(String totalSale) {
		this.totalSale = totalSale;
	}
	public String getTotalSaleNum() {
		return totalSaleNum;
	}
	public void setTotalSaleNum(String totalSaleNum) {
		this.totalSaleNum = totalSaleNum;
	}
	public String getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}
	public String getOnlineMachine() {
		return onlineMachine;
	}
	public void setOnlineMachine(String onlineMachine) {
		this.onlineMachine = onlineMachine;
	}
	public String getOutlineMachine() {
		return outlineMachine;
	}
	public void setOutlineMachine(String outlineMachine) {
		this.outlineMachine = outlineMachine;
	}
}
