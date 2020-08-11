/**
 * 
 */
package com.manage.project.system.report.vo;

import java.io.Serializable;

/**
 * 订单汇总接收参数vo
 * 
 * @author zhangjiawei
 * @date 2018年10月9日
 * 
 */
public class OrderCollectVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 汇总类型，1日报，2月报，3年报 4季度 5半年 */
	private String reportType;
	/**托管公司编号*/
	private String corpId;
	/** 日期 */
	private String date;
	/** 从日期中获取的年 */
	private Integer year;
	/** 从日期中获取的月 */
	private Integer month;
	/** 从日期中获取的日 */
	private Integer day;
	/** 季度 */
	private Integer quarter;
	/** 半年 */
	private Integer halfYear;
	/** 区域编号 */
	private String districtId;
	/** 线路编号 */
	private String lineId;
	/** 点位编号 */
	private String pointId;
	/** 售货机编号 */
	private String siteId;
	/** 商品编号 */
	private String productId;
	/** 商品名称 */
	private String productName;
	/** 开始月份 */
	private String beginMonth;
	/** 结束月份 */
	private String endMonth;
	
	public Integer getQuarter() {
		return quarter;
	}
	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}
	public Integer getHalfYear() {
		return halfYear;
	}
	public void setHalfYear(Integer halfYear) {
		this.halfYear = halfYear;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getBeginMonth() {
		return beginMonth;
	}
	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

}
