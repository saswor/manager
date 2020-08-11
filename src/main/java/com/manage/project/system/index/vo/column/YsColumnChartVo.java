package com.manage.project.system.index.vo.column;

import java.util.List;

/**
 * 首页营收柱图vo,用于组装前台柱图需要的格式
 * @author xufeng
 *
 */
public class YsColumnChartVo {
	private List<String> months;	// 月份
	private List data;	// 包括销售额,销售量,净利润
	public List<String> getMonths() {
		return months;
	}
	public void setMonths(List<String> months) {
		this.months = months;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	
	
}
