package com.manage.project.system.stock.mapper;

import com.manage.project.system.stock.domain.StockPpurchase;
import java.util.List;	

/**
 * 仓库采购记录 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface StockPpurchaseMapper 
{
	/**
     * 查询仓库采购记录信息
     * 
     * @param logid 仓库采购记录ID
     * @return 仓库采购记录信息
     */
	public StockPpurchase selectStockPpurchaseById(String logid);
	
	/**
     * 查询仓库采购记录列表
     * 
     * @param stockPpurchase 仓库采购记录信息
     * @return 仓库采购记录集合
     */
	public List<StockPpurchase> selectStockPpurchaseList(StockPpurchase stockPpurchase);
	
	/**
     * 新增仓库采购记录
     * 
     * @param stockPpurchase 仓库采购记录信息
     * @return 结果
     */
	public int insertStockPpurchase(StockPpurchase stockPpurchase);
	
	/**
     * 修改仓库采购记录
     * 
     * @param stockPpurchase 仓库采购记录信息
     * @return 结果
     */
	public int updateStockPpurchase(StockPpurchase stockPpurchase);
	
	/**
     * 删除仓库采购记录
     * 
     * @param logid 仓库采购记录ID
     * @return 结果
     */
	public int deleteStockPpurchaseById(String logid);
	
	/**
     * 批量删除仓库采购记录
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteStockPpurchaseByIds(String[] logids);
	
}