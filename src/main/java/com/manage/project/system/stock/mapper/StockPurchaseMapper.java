package com.manage.project.system.stock.mapper;

import com.manage.project.system.stock.domain.StockPurchase;
import java.util.List;	

/**
 * 仓库采购记录 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface StockPurchaseMapper 
{
	/**
     * 查询仓库采购记录信息
     * 
     * @param wPurchaseId 仓库采购记录ID
     * @return 仓库采购记录信息
     */
	public StockPurchase selectStockPurchaseById(String wpurchaseId);
	
	/**
     * 查询仓库采购记录列表
     * 
     * @param stockPurchase 仓库采购记录信息
     * @return 仓库采购记录集合
     */
	public List<StockPurchase> selectStockPurchaseList(StockPurchase stockPurchase);
	
	/**
     * 新增仓库采购记录
     * 
     * @param stockPurchase 仓库采购记录信息
     * @return 结果
     */
	public int insertStockPurchase(StockPurchase stockPurchase);
	
	/**
     * 修改仓库采购记录
     * 
     * @param stockPurchase 仓库采购记录信息
     * @return 结果
     */
	public int updateStockPurchase(StockPurchase stockPurchase);
	
	/**
     * 删除仓库采购记录
     * 
     * @param logid 仓库采购记录ID
     * @return 结果
     */
	public int deleteStockPurchaseById(String logid);
	
	/**
     * 批量删除仓库采购记录
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteStockPurchaseByIds(String[] logids);

	/**
	 * 根据仓采购记录编号查询记录
	 * 
	 * @param wpurchaseId 仓采购记录编号
	 * @return
	 */
	public StockPurchase selectStockPurchaseByWpurchaseId(String wpurchaseId);
	
}