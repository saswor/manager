package com.manage.project.system.stock.service;

import com.manage.project.system.stock.domain.StockProduct;
import java.util.List;

/**
 * 商品库存量 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IStockProductService 
{
	/**
     * 查询商品库存量信息
     * 
     * @param logid 商品库存量ID
     * @return 商品库存量信息
     */
	public StockProduct selectStockProductById(String logid);
	
	/**
     * 查询商品库存量列表
     * 
     * @param stockProduct 商品库存量信息
     * @return 商品库存量集合
     */
	public List<StockProduct> selectStockProductList(StockProduct stockProduct);
	
	/**
     * 新增商品库存量
     * 
     * @param stockProduct 商品库存量信息
     * @return 结果
     */
	public int insertStockProduct(StockProduct stockProduct);
	
	/**
     * 修改商品库存量
     * 
     * @param stockProduct 商品库存量信息
     * @return 结果
     */
	public int updateStockProduct(StockProduct stockProduct);
		
	/**
     * 删除商品库存量信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteStockProductByIds(String ids);
	
}
