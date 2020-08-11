package com.manage.project.system.vending.service;

import com.manage.project.system.vending.domain.VendingCabinet;
import java.util.List;

/**
 * 售货机挂载的货柜，主柜的挂载副柜 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IVendingCabinetService 
{
	/**
     * 查询售货机挂载的货柜，主柜的挂载副柜信息
     * 
     * @param logid 售货机挂载的货柜，主柜的挂载副柜ID
     * @return 售货机挂载的货柜，主柜的挂载副柜信息
     */
	public VendingCabinet selectVendingCabinetById(String logid);
	
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
     * 删除售货机挂载的货柜，主柜的挂载副柜信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingCabinetByIds(String ids);

	/**
	 * 根据机型查询是否存在未删除的货柜
	 * 
	 * @param deviceIds
	 * @return
	 */
	public Integer selectVendingCabinetListByDeviceIds(String[] deviceIds);
	
}
