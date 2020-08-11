package com.manage.project.system.index.vo.column;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页营收柱图vo,用于组装前台柱图需要的格式
 * @author xufeng
 *
 */
public class YsColChartVo {	
	private List<String> months;	// 月份
	private List<Float> salesList = new ArrayList<Float>();	// 销售额
	private List<Integer> salesNumList = new ArrayList<Integer>();	// 销售量
	private List<Float> profitList = new ArrayList<Float>();	// 净利润
	public List<String> getMonths() {
		return months;
	}
	public void setMonths(List<String> months) {
		this.months = months;
	}
	public List<Float> getSalesList() {
		return salesList;
	}
	public void setSalesList(List<Float> salesList) {
		this.salesList = salesList;
	}
	public List<Integer> getSalesNumList() {
		return salesNumList;
	}
	public void setSalesNumList(List<Integer> salesNumList) {
		this.salesNumList = salesNumList;
	}
	public List<Float> getProfitList() {
		return profitList;
	}
	public void setProfitList(List<Float> profitList) {
		this.profitList = profitList;
	}
	
	
}
