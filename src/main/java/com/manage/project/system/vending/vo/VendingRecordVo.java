/**
 * 
 */
package com.manage.project.system.vending.vo;

/**
 * 售货机记录vo
 * 
 * @author zhangjiawei
 * @date 2018年10月23日
 * 
 */
public class VendingRecordVo {
	
	/**补货记录数量*/
	private int supplyNum;
	/**售货机当前商品数量*/
	private int pCurNum;
	/**售货机最大商品数量*/
	private int pMaxNum;
	/**历史交易总数*/
	private int orderNum;
	/**设备警告总数*/
	private int warnNum;
	/**货道差异数量*/
	private int lsdifferNum;
	/**货道差异数量*/
	private int logfileNum;
	
	public int getLogfileNum() {
		return logfileNum;
	}

	public void setLogfileNum(int logfileNum) {
		this.logfileNum = logfileNum;
	}

	public int getSupplyNum() {
		return supplyNum;
	}

	public void setSupplyNum(int supplyNum) {
		this.supplyNum = supplyNum;
	}

	public int getpCurNum() {
		return pCurNum;
	}

	public void setpCurNum(int pCurNum) {
		this.pCurNum = pCurNum;
	}

	public int getpMaxNum() {
		return pMaxNum;
	}

	public void setpMaxNum(int pMaxNum) {
		this.pMaxNum = pMaxNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getWarnNum() {
		return warnNum;
	}

	public void setWarnNum(int warnNum) {
		this.warnNum = warnNum;
	}

	public int getLsdifferNum() {
		return lsdifferNum;
	}

	public void setLsdifferNum(int lsdifferNum) {
		this.lsdifferNum = lsdifferNum;
	}	
}
