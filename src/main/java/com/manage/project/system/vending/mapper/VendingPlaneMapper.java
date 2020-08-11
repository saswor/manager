package com.manage.project.system.vending.mapper;

import com.manage.project.system.vending.domain.VendingPlane;
import java.util.List;	

/**
 * 售货机货道 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface VendingPlaneMapper 
{
	/**
     * 查询售货机货道信息
     * 
     * @param logid 售货机货道ID
     * @return 售货机货道信息
     */
	public VendingPlane selectVendingPlaneById(String logid);
	
	/**
     * 查询售货机货道列表
     * 
     * @param vendingPlane 售货机货道信息
     * @return 售货机货道集合
     */
	public List<VendingPlane> selectVendingPlaneList(VendingPlane vendingPlane);
	
	/**
     * 新增售货机货道
     * 
     * @param vendingPlane 售货机货道信息
     * @return 结果
     */
	public int insertVendingPlane(VendingPlane vendingPlane);
	
	/**
     * 修改售货机货道
     * 
     * @param vendingPlane 售货机货道信息
     * @return 结果
     */
	public int updateVendingPlane(VendingPlane vendingPlane);
	
	/**
     * 删除售货机货道
     * 
     * @param logid 售货机货道ID
     * @return 结果
     */
	public int deleteVendingPlaneById(String logid);
	
	/**
     * 批量删除售货机货道
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingPlaneByIds(String[] logids);
	
	/**
	 * 根据模板的logid,删除模板货道
	 * @param logids
	 * @return
	 */
	public int deleteVendingPlaneByConfigId(String[] logids);

	/**
	 * 根据商品编号修改售货机配货货道模板
	 * 
	 * @param vendingPlane
	 * @return
	 */
	public int updateVendingPlaneByProductId(VendingPlane vendingPlane);

	/**
	 * 根据配货模板编号查询模板货道信息
	 * 
	 * @param mConfigId
	 */
	public List<VendingPlane> selectVendingPlaneByMconfigId(String mConfigId);
	
	
}