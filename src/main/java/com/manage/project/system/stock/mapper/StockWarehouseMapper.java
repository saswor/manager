package com.manage.project.system.stock.mapper;

import com.manage.project.system.stock.domain.StockWarehouse;
import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 仓库商品库存存量 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface StockWarehouseMapper 
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
     * 查询仓库商品库存存量汇总列表
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
     * 删除仓库商品库存存量
     * 
     * @param logid 仓库商品库存存量ID
     * @return 结果
     */
	public int deleteStockWarehouseById(String logid);
	
	/**
     * 批量删除仓库商品库存存量
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteStockWarehouseByIds(String[] logids);

	/**
	 * 根据商品编号修改商品库存存量
	 * 
	 * @param stockWarehouse
	 * @return
	 */
	public int updateStockWarehouseByProductId(StockWarehouse stockWarehouse);

	/**
	 * 根据商品编号和仓库编号查询库存信息
	 * 
	 * @param productId 商品编号
	 * @param stockId 仓库编号
	 * @param corpId 公司编号
	 * @return
	 */
	public StockWarehouse selectStockWarehouseByProductAndStock(@Param("productId") String productId,@Param("stockId") String stockId,@Param("corpId") String corpId);

	/**
	 * 查询库存不为0的仓库库存
	 * 
	 * @param stockWarehouseSelect
	 */
	public List<StockWarehouse> selectStockWarehouseCurNumNotNull(StockWarehouse stockWarehouseSelect);

	/**
	 * 删除仓库的所有库存信息
	 * 
	 * @param stockId 仓库编号
	 * @return
	 */
	public int deleteStockWarehouseByStockId(String stockId);

	/**
	 * 根据商品编号删除仓库库存
	 * 
	 * @param productId 商品编号
	 */
	public void deleteStockWarehouseByProductId(String productId);
	
}