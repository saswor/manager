package com.manage.project.system.product.mapper;

import java.util.List;

import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.product.domain.ProductOnline;
import com.manage.project.system.product.vo.ProductOnlineVo;	

/**
 * 记录在线购买的商品 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface ProductOnlineMapper 
{
	/**
     * 查询记录在线购买的商品信息
     * 
     * @param logid 记录在线购买的商品ID
     * @return 记录在线购买的商品信息
     */
	public ProductOnline selectProductOnlineById(String logid);
	
	/**
     * 查询记录在线购买的商品列表
     * 
     * @param vo 记录在线购买的商品信息
     * @return 记录在线购买的商品集合
     */
	public List<ProductOnlineVo> selectProductOnlineList(ProductOnlineVo vo);
	
	/**
     * 新增记录在线购买的商品
     * 
     * @param productOnline 记录在线购买的商品信息
     * @return 结果
     */
	public int insertProductOnline(ProductOnline productOnline);
	
	/**
     * 修改记录在线购买的商品
     * 
     * @param productOnline 记录在线购买的商品信息
     * @return 结果
     */
	public int updateProductOnline(ProductOnline productOnline);
	
	/**
     * 删除记录在线购买的商品
     * 
     * @param logid 记录在线购买的商品ID
     * @return 结果
     */
	public int deleteProductOnlineById(String logid);
	
	/**
     * 批量删除记录在线购买的商品
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductOnlineByIds(String[] logids);
	
	/**
     * 查询记录在线购买的商品信息
     * 
     * @param productId 记录在线购买的商品ID
     * @return 记录在线购买的商品信息
     */
	public ProductOnline selectProductOnlineByProductId(String productId);

	/**
	 * 根据商品编号修改在线商品信息
	 * 
	 * @param productOnline
	 * @return
	 */
	public int updateProductOnlineByProductId(ProductOnline productOnline);

	/**
	 * 根据商品编号删除在线商品
	 * 
	 * @param productId 商品编号
	 */
	public int deleteProductOnlineByProductId(String productId);
	
}