package com.manage.project.system.supply.mapper;

import com.manage.project.system.supply.domain.SupplyVending;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 补货配置的售货机 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Component
public interface SupplyVendingMapper 
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
	 * 根据logid修改最大容量
	 * @param supplyVending
	 * @return
	 */
	public int updateSupplyVendingMax(SupplyVending supplyVending);
	
	/**
     * 删除补货配置的售货机
     * 
     * @param logid 补货配置的售货机ID
     * @return 结果
     */
	public int deleteSupplyVendingById(String logid);
	
	/**
     * 批量删除补货配置的售货机
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyVendingByIds(String[] logids);
	public SupplyVending selectSupplyVendingBySiteId(String siteId);

	public int deleteSupplyVendingBySupplyIds(String[] logids);

	/**
	 * 根据站点id和补货配置编号查询站点补货配置
	 * 
	 * @param supplyVending 封装补货配置编号和站点编号
	 * @return 站点补货配置
	 */
	public SupplyVending selectSupplyVendingBySiteIdAndSupplyId(SupplyVending supplyVending);
	
	/**
	 * 根据售卖机id删除补货售货机(as_supply_vending)
	 * @param siteId
	 * @return
	 */
	public int deleteSupplyVendingBySiteId(String siteId);

}