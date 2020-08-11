package com.manage.project.system.index.vo.line;

import java.io.Serializable;
import java.util.List;

import com.manage.project.system.index.vo.column.SaleVo;

/**
 * 首页今日销售汇总 显示曲线图使用
 * @author xufeng
 *
 */
public class TodaySummaryVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> times;
	private List<Float> sales;	// 销售额
	private List<Integer> salesNum;	// 销售量
	public List<String> getTimes() {
		return times;
	}
	public void setTimes(List<String> times) {
		this.times = times;
	}
	public List<Float> getSales() {
		return sales;
	}
	public void setSales(List<Float> sales) {
		this.sales = sales;
	}
	public List<Integer> getSalesNum() {
		return salesNum;
	}
	public void setSalesNum(List<Integer> salesNum) {
		this.salesNum = salesNum;
	}
}
