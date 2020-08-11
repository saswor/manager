package com.manage.project.system.supply.service;

import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.domain.SupplyProduct;
import com.manage.project.system.supply.vo.SupplyProductUpdateVo;

import java.util.List;

/**
 * 补货商品记录 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface ISupplyProductService 
{
	/**
     * 查询补货商品记录信息
     * 
     * @param logid 补货商品记录ID
     * @return 补货商品记录信息
     */
	public SupplyProduct selectSupplyProductById(String logid);
	
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
	public int updateSupplyProduct(SupplyProductUpdateVo supplyProductUpdateVo)  throws RuntimeException;
		
	/**
     * 删除补货商品记录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyProductByIds(String ids);
	
	public List<SupplyProduct> selectListBySOrderId(String sOrderId);

	/**
	 * 查询商品历史采购价格
	 * 
	 * @param supplyProduct
	 * @return
	 */
	public List<Float> selectSupplyProductPriceList(SupplyProduct supplyProduct);

	/**
	 * 查询补货商品(导出)
	 * 
	 * @param supplyOrder
	 * @return
	 */
	public List<SupplyProduct> selectSupplyProductListForExport(SupplyOrder supplyOrder);
	
	/**
	 * 修改出库商品记录，重新入库
	 * @param supplyProductUpdateVo
	 * @return
	 * @throws RuntimeException
	 */
	public int repeatInbound(SupplyProductUpdateVo supplyProductUpdateVo) throws RuntimeException;

	/**
	 * 追加出库
	 * 
	 * @param supplyProductUpdateVo
	 * @return
	 */
	public int extraOut(SupplyProductUpdateVo supplyProductUpdateVo);
}
