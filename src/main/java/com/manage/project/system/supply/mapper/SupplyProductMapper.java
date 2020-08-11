package com.manage.project.system.supply.mapper;

import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.domain.SupplyProduct;
import com.manage.project.system.supply.vo.SupplyProductUpdateVo;
import com.manage.project.system.vending.domain.Vending;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 补货商品记录 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Component
public interface SupplyProductMapper 
{
	/**
     * 查询补货商品记录信息
     * 
     * @param logid 补货商品记录ID
     * @return 补货商品记录信息
     */
	public SupplyProduct selectSupplyProductById(String logid);

	public SupplyProduct selectSupplyProductByPId(SupplyProduct supplyProduct);
	
	/**
     * 查询补货商品记录列表
     * 
     * @param supplyProduct 补货商品记录信息
     * @return 补货商品记录集合
     */
	public List<SupplyProduct> selectSupplyProductList(SupplyProduct supplyProduct);
	
	/**
     * 新增补货商品记录
     * 
     * @param supplyProduct 补货商品记录信息
     * @return 结果
     */
	public int insertSupplyProduct(SupplyProduct supplyProduct);
	
	/**
     * 修改补货商品记录
     * 
     * @param supplyProduct 补货商品记录信息
     * @return 结果
     */
	public int updateSupplyProduct(SupplyProduct supplyProduct);
	
	/**
     * 删除补货商品记录
     * 
     * @param logid 补货商品记录ID
     * @return 结果
     */
	public int deleteSupplyProductById(String logid);
	
	/**
     * 批量删除补货商品记录
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyProductByIds(String[] logids);
	
	public List<SupplyProduct> selectListBySOrderId(String sOrderId);

	/**
	 * 查询商品历史采购价格
	 * 
	 * @param supplyProduct
	 * @return
	 */
	public List<Float> selectSupplyProductPriceList(SupplyProduct supplyProduct);

	/**
	 * 批量插入补货商品记录
	 * 
	 * @param supplyProductList
	 */
	public void insertSupplyProductBatch(List<SupplyProduct> supplyProductList);

	/**
	 * 查询补货商品(导出)
	 * 
	 * @param supplyOrder
	 * @return
	 */
	public List<SupplyProduct> selectSupplyProductListForExport(SupplyOrder supplyOrder);
	
}