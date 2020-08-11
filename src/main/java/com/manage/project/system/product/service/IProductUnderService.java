package com.manage.project.system.product.service;

import com.manage.project.system.product.domain.ProductUnder;
import com.manage.project.system.vending.domain.VendingLanep;

import java.util.List;

/**
 * 商品下架 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface IProductUnderService 
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
     * 删除商品下架信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductUnderByIds(String ids);

	/**
     * 批量插入商品下架信息
     * 
     * @param productUnder 商品下架信息
     * @return 结果
     */
	//public int insertProductUnderBatch(List<ProductUnder> productUnderList);

	public int insertProductUnderBatch(List<VendingLanep> list);
	
}
