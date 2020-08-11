package com.manage.project.system.vending.mapper;

import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.vo.UnderProductVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 售货机货道商品 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Component
public interface VendingLanepMapper 
{
	/**
     * 查询售货机货道商品信息
     * 
     * @param logid 售货机货道商品ID
     * @return 售货机货道商品信息
     */
	public VendingLanep selectVendingLanepById(String logid);

	public VendingLanep selectVendingLanepByProId(VendingLanep vendingLanep);

	public VendingLanep selectTotalNumBySiteIds(String[] siteIds);
	
	/**
	 * 查询售卖机拥有不为空的商品货道
	 * @param siteId
	 * @return
	 */
	public List<VendingLanep> selectVendingProduct(String siteId);

	
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
	 * 批量插入
	 * @param Laneps
	 * @return
	 */
	public int insertVendingLanepBatch(List<VendingLanep> Laneps);
	
	
	/**
     * 修改售货机货道商品
     * 
     * @param vendingLanep 售货机货道商品信息
     * @return 结果
     */
	public int updateVendingLanep(VendingLanep vendingLanep);
	
	/**
     * 删除售货机货道商品
     * 
     * @param logid 售货机货道商品ID
     * @return 结果
     */
	public int deleteVendingLanepById(String logid);
	
	/**
     * 批量删除售货机货道商品
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingLanepByIds(String[] logids);
	
	/**
	 * 根据货柜id查询该货柜货道及商品
	 * @param cabinetId	货柜id
	 * @return	货道商品列表
	 */
	public List<VendingLanep> selectVendingLanepByCabinetId(String cabinetId);
	
	/**
     * 根据货柜logid货道商品
     * 
     * @param logid 货柜logid
     * @return 结果
     */
	public int deleteVendingLanepByCabId(String logid);

	/**  根据阈值查询站点的补货商品信息**/
	public List<VendingLanep> selectSupplyPThreshold(String siteId);
	/**  根据站点查询所有商品补货信息**/
	public List<VendingLanep> selectSupplyProduct(String siteId);

	/**  查询多个站点的商品容量统计 **/
	public List<VendingLanep> selectCapacityBySiteIds(String[] siteIds);
	/**  查询多个站点的商品容量统计 **/
	public List<VendingLanep> selectCapacityThresholdBySiteIds(String[] siteIds);

	/**  根据站点编号查询 站点的货道库存信息**/
	public List<VendingLanep> selectSupplyVPThreshold(String siteId);
	/**  根据站点编号查询 站点的货道库存信息**/
	public List<VendingLanep> selectSupplyVProduct(String siteId);
	
	/**
	 * 根据货柜id删除所有商品
	 * @param cabinetId
	 * @return
	 */
	public int deleteVendingLanepByCabinetId(@Param("cabinetId") String cabinetId);
	
	/**
	 * 查询下架商品的站点
	 * 
	 * @param vendingLaneProductVo 下架商品信息
	 * @return
	 */
	public List<UnderProductVo> selectUnderProductSite(UnderProductVo vendingLaneProductVo);

	/**
	 * 通过id数组查询货道商品信息
	 * 
	 * @param id id数组
	 * @return
	 */
	public List<UnderProductVo> selectVendingLanepByIds(String[] id);

	/**
	 * 根据商品编号修改货道商品信息
	 * 
	 * @param vendingLanep
	 */
	public int updateVendingLanepByProductId(VendingLanep vendingLanep);
	
	/**
	 * 获取售卖机货道商品最大sid
	 * @param siteId
	 * @return
	 */
	public Integer selectLanepMaxSidBySiteId(String siteId);

	/**
	 * 根据售货机id删除
	 * 
	 * @param siteId
	 * @return
	 */
	public int deleteVendingLanepBySiteId(String siteId);

	/**
	 * 通过主键查询货道商品信息
	 * 
	 * @param slaneId
	 * @return
	 */
	public VendingLanep selectVendingLanepBySlaneId(String slaneId);

	/**
	 * 根据主键修改货道商品信息
	 * 
	 * @param vendingLane
	 * @return
	 */
	public void updateVendingLanepBySlaneId(VendingLanep vendingLanep);
}