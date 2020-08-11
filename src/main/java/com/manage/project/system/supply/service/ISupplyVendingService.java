package com.manage.project.system.supply.service;

import com.manage.project.system.supply.domain.SupplyVending;

import java.util.List;

/**
 * 补货配置的售货机 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface ISupplyVendingService 
{
	/**
     * 查询补货配置的售货机信息
     * 
     * @param logid 补货配置的售货机ID
     * @return 补货配置的售货机信息
     */
	public SupplyVending selectSupplyVendingById(String logid);
	
	/**
     * 查询补货配置的售货机列表
     * 
     * @param supplyVending 补货配置的售货机信息
     * @return 补货配置的售货机集合
     */
	public List<SupplyVending> selectSupplyVendingList(SupplyVending supplyVending);
	
	/**
     * 新增补货配置的售货机
     * 
     * @param supplyVending 补货配置的售货机信息
     * @return 结果
     */
	public int insertSupplyVending(SupplyVending supplyVending);
	
	/**
     * 修改补货配置的售货机
     * 
     * @param supplyVending 补货配置的售货机信息
     * @return 结果
     */
	public int updateSupplyVending(SupplyVending supplyVending);
		
	/**
     * 删除补货配置的售货机信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyVendingByIds(String ids);

	public int deleteSupplyVendingBySupplyIds(String ids);

	public SupplyVending selectSupplyVendingBySiteId(String siteId);

	/**
	 * 根据站点id和补货配置编号查询站点补货配置
	 * 
	 * @param supplyVending 封装补货配置编号和站点编号
	 * @return 站点补货配置
	 */
	public SupplyVending selectSupplyVendingBySiteIdAndSupplyId(SupplyVending supplyVending);
}
