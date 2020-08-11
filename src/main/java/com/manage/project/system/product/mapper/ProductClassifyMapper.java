package com.manage.project.system.product.mapper;

import com.manage.project.system.product.domain.ProductClassify;
import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 商品分类 数据层
 * 
 * @author shicong
 * @date 2018-09-25
 */
public interface ProductClassifyMapper 
{
	/**
     * 查询商品分类信息
     * 
     * @param logid 商品分类ID
     * @return 商品分类信息
     */
	public ProductClassify selectProductClassifyById(String logid);
	
	/**
     * 查询商品分类列表
     * 
     * @param productClassify 商品分类信息
     * @return 商品分类集合
     */
	public List<ProductClassify> selectProductClassifyList(ProductClassify productClassify);
	
	/**
     * 新增商品分类
     * 
     * @param productClassify 商品分类信息
     * @return 结果
     */
	public int insertProductClassify(ProductClassify productClassify);
	
	/**
     * 修改商品分类
     * 
     * @param productClassify 商品分类信息
     * @return 结果
     */
	public int updateProductClassify(ProductClassify productClassify);
	
	/**
     * 删除商品分类
     * 
     * @param logid 商品分类ID
     * @return 结果
     */
	public int deleteProductClassifyById(String logid);
	
	/**
     * 批量删除商品分类
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductClassifyByIds(String[] logids);
	
	/**
	 * 根据商品分类名称模糊查询
	 * @param classifyName
	 * @return
	 */
	public List<ProductClassify> selectProductClassifyByName(@Param("classifyName") String classifyName);

	/**
	 * 根据商品类型编号查询商品
	 * 
	 * @param classifyId
	 * @return
	 */
	public ProductClassify selectProductClassifyByClassifyId(String classifyId);
	
}