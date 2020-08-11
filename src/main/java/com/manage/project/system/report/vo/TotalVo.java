/**
 * 
 */
package com.manage.project.system.report.vo;

import java.io.Serializable;

/**
 * 累计销售信息返回值vo
 * 
 * @author zhangjiawei
 * @date 2018年10月9日
 * 
 */
public class TotalVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/**汇总类型，1日报，2月报，3年报*/
	private String reportType;
	/**累计销售量*/
	private int saleNum;
	/**累计销售额*/
	private Double saleMoney;
	/**累计利润*/
	private Double profit;
	/**时间*/
	private String time;

	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}
	public Double getSaleMoney() {
		return saleMoney;
	}
	public void setSaleMoney(Double saleMoney) {
		this.saleMoney = saleMoney;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	
}
