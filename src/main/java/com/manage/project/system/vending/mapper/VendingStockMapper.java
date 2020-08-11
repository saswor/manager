package com.manage.project.system.vending.mapper;

import java.util.List;

import com.manage.project.system.vending.domain.VendingStock;
import com.manage.project.system.vending.vo.UnderProductVo;	

/**
 * 售货机商品库存，提供下单检查初步检查库存情况。 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface VendingStockMapper 
{
	
	/**
     * 查询售货机商品库存，提供下单检查初步检查库存情况。信息
     * 
     * @param logid 售货机商品库存，提供下单检查初步检查库存情况。ID
     * @return 售货机商品库存，提供下单检查初步检查库存情况。信息
     */
	public VendingStock selectVendingStockById(String logid);
	
	/**
     * 查询售货机商品库存，提供下单检查初步检查库存情况。列表
     * 
     * @param vendingStock 售货机商品库存，提供下单检查初步检查库存情况。信息
     * @return 售货机商品库存，提供下单检查初步检查库存情况。集合
     */
	public List<VendingStock> selectVendingStockList(VendingStock vendingStock);
	
	/**
     * 新增售货机商品库存，提供下单检查初步检查库存情况。
     * 
     * @param vendingStock 售货机商品库存，提供下单检查初步检查库存情况。信息
     * @return 结果
     */
	public int insertVendingStock(VendingStock vendingStock);
	
	/**
     * 修改售货机商品库存，提供下单检查初步检查库存情况。
     * 
     * @param vendingStock 售货机商品库存，提供下单检查初步检查库存情况。信息
     * @return 结果
     */
	public int updateVendingStock(VendingStock vendingStock);
	
	/**
     * 删除售货机商品库存，提供下单检查初步检查库存情况。
     * 
     * @param logid 售货机商品库存，提供下单检查初步检查库存情况。ID
     * @return 结果
     */
	public int deleteVendingStockById(String logid);
	/**
     * 
     * 
     * @param siteId
     * @return 结果
     */
	public int deleteVendingStockBySiteId(String siteId);
	
	/**
     * 批量删除售货机商品库存，提供下单检查初步检查库存情况。
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingStockByIds(String[] logids);

	/**
     * 查询售货机商品库存，提供下单检查初步检查库存情况。列表
     * 
     * @param vendingStock 售货机商品库存，提供下单检查初步检查库存情况。信息
     * @return 售货机商品库存，提供下单检查初步检查库存情况。集合
     */
	public List<VendingStock> selectVendingStockListByProductId(VendingStock vendingStock);

	/**
     * 根据id查询售货机库存列表
     * 
     * @param ids 多个查询id
     * @return 售货机商品库存信息
     */
	public List<VendingStock> selectVendingStockByIds(String[] ids);

	/**
	 * 根据商品编号修改售货机商品库存信息表
	 * 
	 * @param vendingStock
	 * @return
	 */
	public int updateVendingStockByProductId(VendingStock vendingStock);

	/**
	 * 查询要下架商品的站点
	 * 
	 * @param vo
	 * @return
	 */
	public List<VendingStock> selectOnlineVendingStockList(UnderProductVo vo);

	/**
	 * 查询库存非空的信息
	 * 
	 * @param vendingStock
	 * @return
	 */
	public List<VendingStock> selectVendingStockCurNumNotNull(VendingStock vendingStock);

	/**
	 * 根据商品编号删除售货机商品库存
	 * 
	 * @param productId 商品库存
	 * @return
	 */
	public int deleteVendingStockByProductId(String productId);
	
}