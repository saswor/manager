package com.manage.project.system.index.vo;

import java.io.Serializable;

/**
 * 今日销售点位排行榜
 * @author xufeng
 *
 */
public class TodaySaleVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int index;	// 序号
	private String name;	// 点位名称
	private String totalSale;	// 销售额
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(String totalSale) {
		this.totalSale = totalSale;
	}
	
	
	
}
