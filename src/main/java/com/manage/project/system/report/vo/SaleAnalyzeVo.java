/**
 * 
 */
package com.manage.project.system.report.vo;

import java.io.Serializable;

/**
 * 畅销分析接收参数vo
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
public class SaleAnalyzeVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/**托管公司编号*/
	private String corpId;
	/**年*/
	private int year;
	/**月*/
	private int month;
	/**日*/
	private int day;
	/**季度*/
	private int quarter;
	/**对象类型 1:线路 2:点位 3:商品*/
	private String saleType;
	/**日期类型 0:当天 1:本月 2:本季度 3:今年*/
	private String dateType;
	
	/**显示数据条数*/
	private Integer pageSize;
	/**排序*/
	private String orderByColumn;
	/**升序降序*/
	private String isAsc;

	public String getOrderByColumn() {
		return orderByColumn;
	}
	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getIsAsc() {
		return isAsc;
	}
	public void setIsAsc(String isAsc) {
		this.isAsc = isAsc;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	
}
