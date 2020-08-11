package com.manage.project.system.vending.mapper;

import com.manage.project.system.vending.domain.VendingCabinet;
import java.util.List;	

/**
 * 售货机挂载的货柜，主柜的挂载副柜 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface VendingCabinetMapper 
{
	/**
     * 查询售货机挂载的货柜，主柜的挂载副柜信息
     * 
     * @param logid 售货机挂载的货柜，主柜的挂载副柜ID
     * @return 售货机挂载的货柜，主柜的挂载副柜信息
     */
	public VendingCabinet selectVendingCabinetById(String logid);
	
	/**
	 * 根据货柜id查询货柜信息
	 * @param cabinetId
	 * @return
	 */
	public VendingCabinet selectVendingCabinetByCabinetId(String cabinetId);
	
	/**
     * 查询售货机挂载的货柜，主柜的挂载副柜列表
     * 
     * @param vendingCabinet 售货机挂载的货柜，主柜的挂载副柜信息
     * @return 售货机挂载的货柜，主柜的挂载副柜集合
     */
	public List<VendingCabinet> selectVendingCabinetList(VendingCabinet vendingCabinet);
	
	/**
     * 新增售货机挂载的货柜，主柜的挂载副柜
     * 
     * @param vendingCabinet 售货机挂载的货柜，主柜的挂载副柜信息
     * @return 结果
     */
	public int insertVendingCabinet(VendingCabinet vendingCabinet);
	
	/**
     * 修改售货机挂载的货柜，主柜的挂载副柜
     * 
     * @param vendingCabinet 售货机挂载的货柜，主柜的挂载副柜信息
     * @return 结果
     */
	public int updateVendingCabinet(VendingCabinet vendingCabinet);
	
	/**
     * 删除售货机挂载的货柜，主柜的挂载副柜
     * 
     * @param logid 售货机挂载的货柜，主柜的挂载副柜ID
     * @return 结果
     */
	public int deleteVendingCabinetById(String logid);
	
	/**
     * 批量删除售货机挂载的货柜，主柜的挂载副柜
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingCabinetByIds(String[] logids);

	/**
	 * 根据机型查询是否存在未删除的货柜
	 * 
	 * @param deviceIds
	 * @return
	 */
	public Integer selectVendingCabinetListByDeviceIds(String[] deviceIds);

	/**
	 * 根据售货机编号删除货柜信息
	 * 
	 * @param siteId 售货机编号
	 * @return
	 */
	public int deleteVendingCabinetBySiteId(String siteId);
	
}