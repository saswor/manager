package com.manage.project.system.stock.mapper;

import com.manage.project.system.stock.domain.StockProduct;
import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 商品库存量 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface StockProductMapper 
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
     * 删除商品库存量
     * 
     * @param logid 商品库存量ID
     * @return 结果
     */
	public int deleteStockProductById(String logid);
	
	/**
     * 批量删除商品库存量
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteStockProductByIds(String[] logids);

	/**
	 * 根据商品编号修改商品库存量
	 * 
	 * @param stockProduct
	 * @return
	 */
	public int updateStockProductByProductId(StockProduct stockProduct);

	/**
	 * 查询商品库存
	 * 
	 * @param corpId 公司
	 * @param productId 商品
	 * @return
	 */
	public StockProduct selectStockProductByCorpIdAndProdcutId(@Param("corpId") String corpId,@Param("productId") String productId);

	/**
	 * 查询库存不为0的记录
	 * 
	 * @param stockProduct
	 * @return
	 */
	public List<StockProduct> selectStockProductCurNumNotNull(StockProduct stockProduct);

	/**
	 * 根据商品编号删除库存
	 * 
	 * @param productId 商品编号
	 */
	public int deleteStockProductByProductId(String productId);
	
}