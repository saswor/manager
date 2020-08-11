package com.manage.project.system.product.mapper;

import com.manage.project.system.product.domain.ProductInfo;
import java.util.List;	

/**
 * 记录商品 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface ProductInfoMapper 
{
	/**
     * 查询记录商品信息
     * 
     * @param logid 记录商品ID
     * @return 记录商品信息
     */
	public ProductInfo selectProductInfoById(String logid);
	
	/**
     * 查询记录商品列表
     * 
     * @param productInfo 记录商品信息
     * @return 记录商品集合
     */
	public List<ProductInfo> selectProductInfoList(ProductInfo productInfo);
	
	/**
     * 新增记录商品
     * 
     * @param productInfo 记录商品信息
     * @return 结果
     */
	public int insertProductInfo(ProductInfo productInfo);
	
	/**
     * 修改记录商品
     * 
     * @param productInfo 记录商品信息
     * @return 结果
     */
	public int updateProductInfo(ProductInfo productInfo);
	
	/**
     * 删除记录商品
     * 
     * @param logid 记录商品ID
     * @return 结果
     */
	public int deleteProductInfoById(String logid);
	
	/**
     * 批量删除记录商品
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductInfoByIds(String[] logids);

	/**
     * 查询记录商品信息
     * 
     * @param productId 商品编号
     * @return 记录商品信息
     */
	public ProductInfo selectProductInfoByProductId(String productId);

	/**
	 * 批量查询商品信息
	 * 
	 * @param ids
	 * @return
	 */
	public List<ProductInfo> selectProductInfoByIds(String[] ids);

	/**
	 * 查询商品是否已经存在
	 * 
	 * @param productInfo
	 * @return
	 */
	public ProductInfo selectProductInfoExist(ProductInfo productInfo);

	/**
	 * 查询可以引用的商品信息
	 * 
	 * @param productInfo
	 * @return
	 */
	public List<ProductInfo> selectReferenceProductInfoList(ProductInfo productInfo);
	
}