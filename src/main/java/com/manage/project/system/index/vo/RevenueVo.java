package com.manage.project.system.index.vo;

import java.io.Serializable;

/**
 * 营收分析
 * @author xufeng
 *
 */
public class RevenueVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String totalSale;	// 销售额
	private String totalSaleNum;	// 销售量
	private String totalProfit;	// 净利润
	private String month;	// 月份，如2018-01
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
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
}
