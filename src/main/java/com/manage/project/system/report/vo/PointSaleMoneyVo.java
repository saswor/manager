/**
 * 
 */
package com.manage.project.system.report.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * 点位销售报表返回值vo
 * 
 * @author zhangjiawei
 * @date 2018年11月22日
 * 
 */
public class PointSaleMoneyVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 线路编号 */
	private String pointId;
	/** 线路名称 */
	private String pointName;
	/** 总计销售额 */
	private double totalSaleMoney;
	/** 详细信息 */
	private List<Map<String,Object>> details;

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String lineId) {
		this.pointId = lineId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String lineName) {
		this.pointName = lineName;
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
