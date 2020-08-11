package com.manage.project.system.stock.vo;

import java.util.List;

import com.manage.project.system.stock.domain.StockWarehouse;

/**
 * 额外采购vo
 * 
 * @author zhangjiawei
 *
 */
public class ExtraPurchaseVo {

	private String stockId;
	
	private List<StockWarehouse> stockWarehouses;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public List<StockWarehouse> getStockWarehouses() {
		return stockWarehouses;
	}

	public void setStockWarehouses(List<StockWarehouse> stockWarehouses) {
		this.stockWarehouses = stockWarehouses;
	}
	
}
