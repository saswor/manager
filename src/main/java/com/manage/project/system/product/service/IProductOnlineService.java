package com.manage.project.system.product.service;

import java.util.List;

import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.product.domain.ProductOnline;
import com.manage.project.system.product.vo.ProductOnlineVo;

/**
 * 记录在线购买的商品 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface IProductOnlineService 
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
     * 删除记录在线购买的商品信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductOnlineByIds(String ids);
	
}
