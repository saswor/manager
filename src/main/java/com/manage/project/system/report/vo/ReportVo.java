/**
 * 
 */
package com.manage.project.system.report.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 线路销售报表接收参数vo
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
public class ReportVo implements Serializable{

	private static final long serialVersionUID = 1L;	
	/** 点位编号 */
	private String pointId;
	/** 点位名称 */
	private String pointName;
	/** 线路名称 */
	private String lineName;
	/** 线路编号 */
	private String lineId;
	/** 日期 */
	private String date;
	/** 开始日期 */
	private String beginDay;
	/** 结束日期 */
	private String endDay;
	/**托管公司编号*/
	private String corpId;
	/** 开始日期 */
	private Date beginDate;
	/** 结束日期 */
	private Date endDate;
	/** 年 */
	private int syear;
	/** 月 */
	private int smonth;
	
	public int getSyear() {
		return syear;
	}

	public void setSyear(int syear) {
		this.syear = syear;
	}

	public int getSmonth() {
		return smonth;
	}

	public void setSmonth(int smonth) {
		this.smonth = smonth;
	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}


	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getBeginDay() {
		return beginDay;
	}

	public void setBeginDay(String beginDay) {
		this.beginDay = beginDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
		
}
