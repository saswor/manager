package com.manage.project.system.vending.mapper;

import com.manage.project.system.vending.domain.VendingLane;

import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 售货机货道 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface VendingLaneMapper 
{
	/**
     * 查询售货机货道信息
     * 
     * @param logid 售货机货道ID
     * @return 售货机货道信息
     */
	public VendingLane selectVendingLaneById(String logid);
	
	/**
     * 查询售货机货道列表
     * 
     * @param vendingLane 售货机货道信息
     * @return 售货机货道集合
     */
	public List<VendingLane> selectVendingLaneList(VendingLane vendingLane);
	
	/**
     * 新增售货机货道
     * 
     * @param vendingLane 售货机货道信息
     * @return 结果
     */
	public int insertVendingLane(VendingLane vendingLane);
	
	public int insertVendingLaneBatch(List<VendingLane> lanes);
	
	/**
     * 修改售货机货道
     * 
     * @param vendingLane 售货机货道信息
     * @return 结果
     */
	public int updateVendingLane(VendingLane vendingLane);
	
	/**
     * 删除售货机货道
     * 
     * @param logid 售货机货道ID
     * @return 结果
     */
	public int deleteVendingLaneById(String logid);
	
	/**
     * 批量删除售货机货道
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingLaneByIds(String[] logids);
	
	/**
	 * 根据货柜id查询该货柜货道及商品
	 * @param cabinetId	货柜id
	 * @return	货道商品列表
	 */
	public List<VendingLane> selectVendingLaneByCabinetId(String cabinetId);
	
	/**
	 * 根据货柜logid删除,VendingLane对象添logid和corpid
	 * @param vendingLane
	 * @return
	 */
	public int deleteVendingLaneByCabId(VendingLane vendingLane);
	
	/**
	 * 根据货柜id删除所有货道
	 * @param cabinetId
	 * @return
	 */
	public int deleteVendingLaneByCabinetId(@Param("cabinetId") String cabinetId);

	/**
	 * 根据售货机编号删除货道信息
	 * 
	 * @param siteId 售货机编号
	 * @return
	 */
	public int deleteVendingLaneBySiteId(String siteId);

	/**
	 * 根据主键修改货道信息
	 * 
	 * @param vendingLane
	 * @return
	 */
	public int updateVendingLaneBySlaneId(VendingLane vendingLane);
}