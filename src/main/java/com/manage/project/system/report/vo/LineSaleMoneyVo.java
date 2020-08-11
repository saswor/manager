/**
 * 
 */
package com.manage.project.system.report.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * 线路销售报表返回值vo
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
public class LineSaleMoneyVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 线路编号 */
	private String lineId;
	/** 线路名称 */
	private String lineName;
	/** 点位数量 */
	private int pointNum;
	/** 总计销售额 */
	private double totalSaleMoney;
	/** 详细信息 */
	private List<Map<String,Object>> details;

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getPointNum() {
		return pointNum;
	}

	public void setPointNum(int pointNum) {
		this.pointNum = pointNum;
	}

	public double getTotalSaleMoney() {
		return totalSaleMoney;
	}

	public void setTotalSaleMoney(double totalSaleMoney) {
		this.totalSaleMoney = totalSaleMoney;
	}

	public List<Map<String, Object>> getDetails() {
		return details;
	}

	public void setDetails(List<Map<String, Object>> details) {
		this.details = details;
	}

}
