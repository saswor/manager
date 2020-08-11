package com.manage.project.system.stock.mapper;

import com.manage.project.system.stock.domain.StockInfo;
import java.util.List;	

/**
 * 仓库入库记录 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface StockInfoMapper 
{
	public StockInfo selectStockInfoById(String logid);
	
	public List<StockInfo> selectStockInfoList(StockInfo stockInfo);
	
	public int insertStockInfo(StockInfo stockInfo);

	public int updateStockInfo(StockInfo stockInfo);
	
	public int deleteStockInfoById(String logid);
	
	public int deleteStockInfoByIds(String[] logids);

	/**
	 * 批量查询仓库信息
	 * 
	 * @param ids
	 * @return
	 */
	public List<StockInfo> selectStockInfoByIds(String[] ids);
}