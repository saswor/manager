/**
 * 
 */
package com.manage.project.system.report.vo;

import java.io.Serializable;

import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * 营销分析返回值vo
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
public class RankVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 名称 */
	@Excel(name="名称")
	private String name;
	/** 销售量 */
	@Excel(name="销售量")
	private int saleNum;
	/** 销售额 */
	@Excel(name="销售额")
	private double saleMoney;
	/** 利润 */
	@Excel(name="利润")
	private double profit;
	/** 占百分比 */
	private double percent;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}
	public double getSaleMoney() {
		return saleMoney;
	}
	public void setSaleMoney(double saleMoney) {
		this.saleMoney = saleMoney;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	
}
