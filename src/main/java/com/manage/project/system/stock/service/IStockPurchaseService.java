package com.manage.project.system.stock.service;

import com.manage.project.system.stock.domain.StockPpurchase;
import com.manage.project.system.stock.domain.StockPurchase;
import com.manage.project.system.stock.domain.StockWarehouse;
import com.manage.project.system.stock.vo.ExtraPurchaseVo;

import java.util.List;

/**
 * 仓库采购记录 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IStockPurchaseService 
{
	/**
     * 查询仓库采购记录信息
     * 
     * @param logid 仓库采购记录ID
     * @return 仓库采购记录信息
     */
	public StockPurchase selectStockPurchaseById(String logid);
	
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
     * 删除仓库采购记录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteStockPurchaseByIds(String ids);
	
	
	public int insertPurchase(StockPurchase stockPurchase, List<StockPpurchase> stockPpurchases);

	/**
	 * 根据仓采购记录编号查询记录
	 * 
	 * @param wpurchaseId 
	 * @return
	 */
	public StockPurchase selectStockPurchaseByWpurchaseId(String wpurchaseId);

	/**
	 * 补货过程中额外采购
	 * 
	 * @param stockWarehouses
	 * @return
	 */
	public int extraPurchase(ExtraPurchaseVo vo);
	
}
