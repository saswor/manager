package com.manage.project.system.product.mapper;

import java.util.List;

import com.manage.project.system.product.domain.ProductLunder;
import com.manage.project.system.product.vo.ProductLunderVo;	

/**
 * 站点货道商品下架 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface ProductLunderMapper 
{
	/**
     * 查询站点货道商品下架信息
     * 
     * @param logid 站点货道商品下架ID
     * @return 站点货道商品下架信息
     */
	public ProductLunder selectProductLunderById(String logid);
	
	/**
     * 查询站点货道商品下架列表
     * 
     * @param productLunder 站点货道商品下架信息
     * @return 站点货道商品下架集合
     */
	public List<ProductLunderVo> selectProductLunderList(ProductLunder productLunder);
	
	/**
     * 新增站点货道商品下架
     * 
     * @param productLunder 站点货道商品下架信息
     * @return 结果
     */
	public int insertProductLunder(ProductLunder productLunder);
	
	/**
     * 修改站点货道商品下架
     * 
     * @param productLunder 站点货道商品下架信息
     * @return 结果
     */
	public int updateProductLunder(ProductLunder productLunder);
	
	/**
     * 删除站点货道商品下架
     * 
     * @param logid 站点货道商品下架ID
     * @return 结果
     */
	public int deleteProductLunderById(String logid);
	
	/**
     * 批量删除站点货道商品下架
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductLunderByIds(String[] logids);

	/**
     * 查询站点货道商品下架列表
     * 
     * @param productLunderVo 站点货道商品下架信息
     * @return 站点货道商品下架集合
     */
	public List<ProductLunderVo> selectProductLunderVoList(ProductLunderVo vo);

	/**
	 * 批量插入信息
	 * 
	 * @param productLunderList
	 */
	public void insertProductLunderBatch(List<ProductLunder> productLunderList);
	
}