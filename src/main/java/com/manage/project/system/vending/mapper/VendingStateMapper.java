package com.manage.project.system.vending.mapper;

import com.manage.project.system.vending.domain.VendingState;
import java.util.List;	

/**
 * 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface VendingStateMapper 
{
	/**
     * 查询保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     * 
     * @param siteId 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。ID
     * @return 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     */
	public VendingState selectVendingStateById(String siteId);
	
	/**
     * 查询保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。列表
     * 
     * @param vendingState 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     * @return 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。集合
     */
	public List<VendingState> selectVendingStateList(VendingState vendingState);
	
	/**
     * 新增保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。
     * 
     * @param vendingState 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     * @return 结果
     */
	public int insertVendingState(VendingState vendingState);
	
	/**
     * 修改保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。
     * 
     * @param vendingState 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     * @return 结果
     */
	public int updateVendingState(VendingState vendingState);
	
	/**
     * 删除保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。
     * 
     * @param logid 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。ID
     * @return 结果
     */
	public int deleteVendingStateById(String siteId);
	
	/**
     * 批量删除保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingStateByIds(String[] siteIds);

	/**
	 * 根据售货机编号查询售货机状态
	 * 
	 * @param siteId 售货机编号
	 * @return
	 */
	public VendingState selectVendingStateBySiteId(String siteId);
	
}