package com.manage.project.system.product.service;

import java.util.List;

import com.manage.project.system.product.domain.ProductLunder;
import com.manage.project.system.product.vo.ProductLunderVo;

/**
 * 站点货道商品下架 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface IProductLunderService 
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
	public List<ProductLunderVo> selectProductLunderList(ProductLunderVo vo);
	
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
     * 删除站点货道商品下架信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductLunderByIds(String ids);

	/**
     * 查询站点货道商品下架列表
     * 
     * @param productLunder 站点货道商品下架信息
     * @return 站点货道商品下架集合
     */
	public List<ProductLunderVo> selectProductLunderVoList(ProductLunderVo vo);
	
}
