package com.manage.project.system.vending.service;

import java.util.List;

import com.manage.project.system.vending.domain.VendingStock;
import com.manage.project.system.vending.vo.UnderProductVo;

/**
 * 售货机商品库存，提供下单检查初步检查库存情况。 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface IVendingStockService 
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
     * 删除售货机商品库存，提供下单检查初步检查库存情况。信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingStockByIds(String ids);

	/**
     * 根据id查询售货机库存列表
     * 
     * @param ids 多个查询id
     * @return 售货机商品库存信息
     */
	public List<VendingStock> selectVendingStockByIds(String[] id);
	
	/**
	 * 查询要下架商品的站点
	 * 
	 * @param vo
	 * @return
	 */
	public List<VendingStock> selectOnlineVendingStockList(UnderProductVo vo);
	
}
