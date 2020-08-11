package com.manage.project.system.stock.vo;

import java.util.ArrayList;
import java.util.List;

import com.manage.project.system.stock.domain.StockPinbound;

/**
 * 入库提交vo
 * @author zhangjiawei
 *
 */
public class StockInboundVo {
	
	/** 仓库入库记录编号 */
	private String wInboundId;
	/** 仓采购记录编号 */
	private String wpurchaseId;
	
	private List<StockPinbound> stockPinbounds=new ArrayList<StockPinbound>();

	public String getwInboundId() {
		return wInboundId;
	}

	public void setwInboundId(String wInboundId) {
		this.wInboundId = wInboundId;
	}

	public String getWpurchaseId() {
		return wpurchaseId;
	}

	public void setWpurchaseId(String wpurchaseId) {
		this.wpurchaseId = wpurchaseId;
	}

	public List<StockPinbound> getStockPinbounds() {
		return stockPinbounds;
	}

	public void setStockPinbounds(List<StockPinbound> stockPinbounds) {
		this.stockPinbounds = stockPinbounds;
	}
	
}
