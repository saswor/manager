package com.manage.project.system.vending.service;

import java.util.List;

import com.manage.project.system.vending.domain.VendingState;


/**
 * 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface IVendingStateService 
{
	/**
     * 查询保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     * 
     * @param logid 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。ID
     * @return 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     */
	public VendingState selectVendingStateById(String logid);
	
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
     * 删除保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingStateByIds(String ids);

	/**
	 * 根据售货机编号查询售货机状态
	 * 
	 * @param siteId 售货机编号
	 * @return
	 */
	public VendingState selectVendingStateBySiteId(String siteId);
	
}
