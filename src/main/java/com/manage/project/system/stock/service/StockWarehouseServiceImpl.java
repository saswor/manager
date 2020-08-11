package com.manage.project.system.stock.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.stock.mapper.StockWarehouseMapper;
import com.manage.project.system.stock.domain.StockWarehouse;
import com.manage.project.system.stock.service.IStockWarehouseService;
import com.manage.common.support.Convert;

/**
 * 仓库商品库存存量 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class StockWarehouseServiceImpl implements IStockWarehouseService 
{
	@Autowired
	private StockWarehouseMapper stockWarehouseMapper;

	/**
     * 查询仓库商品库存存量信息
     * 
     * @param logid 仓库商品库存存量ID
     * @return 仓库商品库存存量信息
     */
    @Override
	public StockWarehouse selectStockWarehouseById(String logid)
	{
	    return stockWarehouseMapper.selectStockWarehouseById(logid);
	}
	
	/**
     * 查询仓库商品库存存量列表
     * 
     * @param stockWarehouse 仓库商品库存存量信息
     * @return 仓库商品库存存量集合
     */
	@Override
	public List<StockWarehouse> selectStockWarehouseList(StockWarehouse stockWarehouse)
	{
	    return stockWarehouseMapper.selectStockWarehouseList(stockWarehouse);
	}
	
	/**
     * 查询仓库商品库存存量列表
     * 
     * @param stockWarehouse 仓库商品库存存量信息
     * @return 仓库商品库存存量集合
     */
	@Override
	public List<StockWarehouse> selectStockWarehouse(StockWarehouse stockWarehouse)
	{
	    return stockWarehouseMapper.selectStockWarehouse(stockWarehouse);
	}
	
    /**
     * 新增仓库商品库存存量
     * 
     * @param stockWarehouse 仓库商品库存存量信息
     * @return 结果
     */
	@Override
	public int insertStockWarehouse(StockWarehouse stockWarehouse)
	{
	    return stockWarehouseMapper.insertStockWarehouse(stockWarehouse);
	}
	
	/**
     * 修改仓库商品库存存量
     * 
     * @param stockWarehouse 仓库商品库存存量信息
     * @return 结果
     */
	@Override
	public int updateStockWarehouse(StockWarehouse stockWarehouse)
	{
	    return stockWarehouseMapper.updateStockWarehouse(stockWarehouse);
	}

	/**
     * 删除仓库商品库存存量对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteStockWarehouseByIds(String ids)
	{
		return stockWarehouseMapper.deleteStockWarehouseByIds(Convert.toStrArray(ids));
	}
	
}
