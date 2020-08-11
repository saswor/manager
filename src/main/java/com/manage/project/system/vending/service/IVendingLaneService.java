package com.manage.project.system.vending.service;

import com.manage.project.system.vending.domain.VendingLane;
import java.util.List;

/**
 * 售货机货道 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IVendingLaneService 
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
	
	/**
     * 修改售货机货道
     * 
     * @param vendingLane 售货机货道信息
     * @return 结果
     */
	public int updateVendingLane(VendingLane vendingLane);
		
	/**
     * 删除售货机货道信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingLaneByIds(String ids);
	
	/**
	 * 根据货柜id查询该货柜货道及商品
	 * @param cabinetId	货柜id
	 * @return	货道商品列表
	 */
	public List<VendingLane> selectVendingLaneByCabinetId(String cabinetId);
}
