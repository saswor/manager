package com.manage.project.system.product.service;

import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.product.vo.ImportProductVo;

import java.util.List;

/**
 * 记录商品 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IProductInfoService 
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
     * 删除记录商品信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductInfoByIds(String ids);
	
	/**
     * 导入商品信息
     * 
     * @param productInfoList 商品信息
     * @return 结果
     */
	public int insertProductInfoBatch(List<ProductInfo> productInfoList);
	
	/**
	 * 批量插入商品
	 * @param list
	 * @return
	 */
	public int insertProduct(List<ImportProductVo> list);

	/**
     * 查询记录商品信息
     * 
     * @param productId 商品编号
     * @return 记录商品信息
     */
	public ProductInfo selectProductInfoByProductId(String productId);

	/**
	 * 保存导入商品信息
	 * 
	 * @param productInfoList
	 * @return
	 */
	public AjaxResult saveImportProductInfo(ProductInfo productInfo);

	/**
	 * 查询可以引用的商品信息
	 * 
	 * @param productInfo
	 * @return
	 */
	public List<ProductInfo> selectReferenceProductInfoList(ProductInfo productInfo);
}
