package com.manage.project.system.vendingPoint.mapper;

import com.manage.project.system.vendingPoint.domain.VendingPoint;
import java.util.List;	

/**
 * 售货机绑定的点位 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface VendingPointMapper 
{
	/**
     * 查询售货机绑定的点位信息
     * 
     * @param logid 售货机绑定的点位ID
     * @return 售货机绑定的点位信息
     */
	public VendingPoint selectVendingPointById(String logid);
	
	/**
     * 查询售货机绑定的点位列表
     * 
     * @param vendingPoint 售货机绑定的点位信息
     * @return 售货机绑定的点位集合
     */
	public List<VendingPoint> selectVendingPointList(VendingPoint vendingPoint);
	
	/**
     * 查询售货机绑定的点位列表
     * 
     * @param vendingPoint 售货机绑定的点位信息
     * @return 售货机绑定的点位集合
     */
	public List<VendingPoint> selectVendingPoint(VendingPoint vendingPoint);
	
	/**
     * 新增售货机绑定的点位
     * 
     * @param vendingPoint 售货机绑定的点位信息
     * @return 结果
     */
	public int insertVendingPoint(VendingPoint vendingPoint);
	
	/**
     * 修改售货机绑定的点位
     * 
     * @param vendingPoint 售货机绑定的点位信息
     * @return 结果
     */
	public int updateVendingPoint(VendingPoint vendingPoint);
	
	/**
     * 删除售货机绑定的点位
     * 
     * @param logid 售货机绑定的点位ID
     * @return 结果
     */
	public int deleteVendingPointById(String logid);
	
	/**
     * 批量删除售货机绑定的点位
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingPointByIds(String[] logids);

	/**
	 * 查询点位是否存在
	 * 
	 * @param vendingPoint
	 * @return
	 */
	public VendingPoint selectVendingPointExist(VendingPoint vendingPoint);

	/**
	 * 查询未绑定的点位
	 * 
	 * @param vendingPoint
	 * @return
	 */
	public List<VendingPoint> selectNotBindingVendingPointList(VendingPoint vendingPoint);

	/**
	 * 根据点位编号查询点位
	 * 
	 * @param pointId
	 */
	public VendingPoint selectVendingPointByPointId(String pointId);
	
}