package com.manage.project.system.stock.service;

import com.manage.project.system.stock.domain.StockWarehouse;
import java.util.List;

/**
 * 仓库商品库存存量 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IStockWarehouseService 
{
	/**
     * 查询仓库商品库存存量信息
     * 
     * @param logid 仓库商品库存存量ID
     * @return 仓库商品库存存量信息
     */
	public StockWarehouse selectStockWarehouseById(String logid);
	
	/**
     * 查询仓库商品库存存量列表
     * 
     * @param stockWarehouse 仓库商品库存存量信息
     * @return 仓库商品库存存量集合
     */
	public List<StockWarehouse> selectStockWarehouseList(StockWarehouse stockWarehouse);
	
	/**
     * 查询仓库商品库存存量列表
     * 
     * @param stockWarehouse 仓库商品库存存量信息
     * @return 仓库商品库存存量集合
     */
	public List<StockWarehouse> selectStockWarehouse(StockWarehouse stockWarehouse);
	
	/**
     * 新增仓库商品库存存量
     * 
     * @param stockWarehouse 仓库商品库存存量信息
     * @return 结果
     */
	public int insertStockWarehouse(StockWarehouse stockWarehouse);
	
	/**
     * 修改仓库商品库存存量
     * 
     * @param stockWarehouse 仓库商品库存存量信息
     * @return 结果
     */
	public int updateStockWarehouse(StockWarehouse stockWarehouse);
		
	/**
     * 删除仓库商品库存存量信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteStockWarehouseByIds(String ids);
	
}
