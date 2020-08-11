package com.manage.project.system.index.vo;

import java.io.Serializable;

/**
 * 首页,近一月总览
 * @author xufeng
 *
 */
public class OneMonthReviewVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String totalSale = "0";	// 销售额
	private String totalSaleNum = "0";	// 销售量
	private String totalProfit = "0";	// 净利润
	private int type;	// 类型，1今天,2昨天,3近7天,4近30天
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
