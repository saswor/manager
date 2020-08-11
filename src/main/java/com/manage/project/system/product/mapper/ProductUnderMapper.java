package com.manage.project.system.product.mapper;

import com.manage.project.system.product.domain.ProductUnder;
import java.util.List;	

/**
 * 商品下架 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface ProductUnderMapper 
{
	/**
     * 查询商品下架信息
     * 
     * @param logid 商品下架ID
     * @return 商品下架信息
     */
	public ProductUnder selectProductUnderById(String logid);
	
	/**
     * 查询商品下架列表
     * 
     * @param productUnder 商品下架信息
     * @return 商品下架集合
     */
	public List<ProductUnder> selectProductUnderList(ProductUnder productUnder);
	
	/**
     * 新增商品下架
     * 
     * @param productUnder 商品下架信息
     * @return 结果
     */
	public int insertProductUnder(ProductUnder productUnder);
	
	/**
     * 修改商品下架
     * 
     * @param productUnder 商品下架信息
     * @return 结果
     */
	public int updateProductUnder(ProductUnder productUnder);
	
	/**
     * 删除商品下架
     * 
     * @param logid 商品下架ID
     * @return 结果
     */
	public int deleteProductUnderById(String logid);
	
	/**
     * 批量删除商品下架
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductUnderByIds(String[] logids);

	/**
     * 批量插入商品下架信息
     * 
     * @param productUnder 商品下架信息
     * @return 结果
     */
	public int insertProductUnderBatch(List<ProductUnder> productUnderList);
	
}