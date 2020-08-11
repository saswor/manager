package com.manage.project.system.stock.service;

import com.manage.project.system.stock.domain.StockInbound;
import com.manage.project.system.stock.domain.StockInfo;
import com.manage.project.system.stock.vo.StockInboundParamVo;

import java.util.List;

/**
 * 
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IStockInfoService {
	public StockInfo selectStockInfoById(String logid);

	public List<StockInfo> selectStockInfoList(StockInfo stockInfo);

	public int insertStockInfo(StockInfo stockInfo);

	public int updateStockInfo(StockInfo stockInfo);

	public int deleteStockInfoByIds(String ids);
}
