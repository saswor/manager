/**
 * 
 */
package com.manage.project.system.report.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 营收分析返回值vo
 * 
 * @author zhangjiawei
 * @date 2018年10月9日
 * 
 */
public class IncomeDetailVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/**汇总类型，1日报，2月报，3年报*/
	private String reportType;
	/**详细销售信息*/
	private List<TotalVo> list=new ArrayList<TotalVo>();
	
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public List<TotalVo> getList() {
		return list;
	}
	public void setList(List<TotalVo> list) {
		this.list = list;
	}

}
