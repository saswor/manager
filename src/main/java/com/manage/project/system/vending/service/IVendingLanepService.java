package com.manage.project.system.vending.service;

import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.vo.UnderProductVo;

import java.util.List;

/**
 * 售货机货道商品 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IVendingLanepService 
{
	/**
	 * 查询售卖机拥有不为空的商品货道
	 * @param siteId
	 * @return
	 */
	public List<VendingLanep> selectVendingProduct(String siteId);
	/**
     * 查询售货机货道商品信息
     * 
     * @param logid 售货机货道商品ID
     * @return 售货机货道商品信息
     */
	public VendingLanep selectVendingLanepById(String logid);
	
	/**
     * 查询售货机货道商品列表
     * 
     * @param vendingLanep 售货机货道商品信息
     * @return 售货机货道商品集合
     */
	public List<VendingLanep> selectVendingLanepList(VendingLanep vendingLanep);
	
	/**
     * 新增售货机货道商品
     * 
     * @param vendingLanep 售货机货道商品信息
     * @return 结果
     */
	public int insertVendingLanep(VendingLanep vendingLanep);
	
	/**
     * 修改售货机货道商品
     * 
     * @param vendingLanep 售货机货道商品信息
     * @return 结果
     */
	public int updateVendingLanep(VendingLanep vendingLanep);
		
	/**
     * 删除售货机货道商品信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingLanepByIds(String ids);
	
	/**
	 * 根据货柜id查询该货柜货道及商品
	 * @param cabinetId	货柜id
	 * @return	货道商品列表
	 */
	public List<VendingLanep> selectVendingLanepByCabinetId(String cabinetId);
    /**  根据阈值查询站点的补货商品信息**/
	public List<VendingLanep> selectSupplyPThreshold(String siteId);
	/**  根据站点查询所有商品补货信息**/
	public List<VendingLanep> selectSupplyProduct(String siteId);

	/**
	 * 查询下架商品的站点
	 * 
	 * @param vendingLaneProductVo 下架商品信息
	 * @return 下架商品的货道商品信息
	 */
	public List<UnderProductVo> selectUnderProductSite(UnderProductVo vendingLaneProductVo);

	/**
	 * 通过id数组查询货道商品信息
	 * 
	 * @param id id数组
	 * @return 下架商品的货道商品信息
	 */
	public List<UnderProductVo> selectVendingLanepByIds(String[] id);
	
}
