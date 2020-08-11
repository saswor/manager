/**
 * 
 */
package com.manage.project.system.report.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 图表vo
 * 
 * @author zhangjiawei
 * @date 2018年10月16日
 * 
 */
public class ChartVo {
	/**时间轴*/
	private List<String> coordinate=new ArrayList<String>();
	/**销售额*/
	private List<Double> saleMoney=new ArrayList<Double>();
	/**销售量*/
	private List<Integer> saleNum=new ArrayList<Integer>();
	/**利润*/
	private List<Double> profit=new ArrayList<Double>();
	/**图表类型:1线形图,2柱状图*/
	private int type;
	
	public List<String> getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(List<String> coordinate) {
		this.coordinate = coordinate;
	}
	public List<Double> getSaleMoney() {
		return saleMoney;
	}
	public void setSaleMoney(List<Double> saleMoney) {
		this.saleMoney = saleMoney;
	}
	public List<Integer> getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(List<Integer> saleNum) {
		this.saleNum = saleNum;
	}
	public List<Double> getProfit() {
		return profit;
	}
	public void setProfit(List<Double> profit) {
		this.profit = profit;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
