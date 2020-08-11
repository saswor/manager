package com.manage.project.system.vending.service;

import java.util.List;

import com.manage.project.system.vending.domain.VendingUpgradeTask;

/**
 * 具体售货机的升级任务列 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface IVendingUpgradeTaskService 
{
	/**
     * 查询具体售货机的升级任务列信息
     * 
     * @param logid 具体售货机的升级任务列ID
     * @return 具体售货机的升级任务列信息
     */
	public VendingUpgradeTask selectVendingUpgradeTaskById(String logid);
	
	/**
     * 查询具体售货机的升级任务列列表
     * 
     * @param vendingUpgradeTask 具体售货机的升级任务列信息
     * @return 具体售货机的升级任务列集合
     */
	public List<VendingUpgradeTask> selectVendingUpgradeTaskAndVendingUpgradeList(VendingUpgradeTask vendingUpgradeTask);
	
	/**
     * 新增具体售货机的升级任务列
     * 
     * @param vendingUpgradeTask 具体售货机的升级任务列信息
     * @return 结果
     */
	public int insertVendingUpgradeTask(VendingUpgradeTask vendingUpgradeTask);
	
	/**
     * 修改具体售货机的升级任务列
     * 
     * @param vendingUpgradeTask 具体售货机的升级任务列信息
     * @return 结果
     */
	public int updateVendingUpgradeTask(VendingUpgradeTask vendingUpgradeTask);
		
	/**
     * 删除具体售货机的升级任务列信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingUpgradeTaskByIds(String ids);
	
	/**
     * 查询具体售货机的升级任务列列表
     * 
     * @param vendingUpgradeTask 具体售货机的升级任务列信息
     * @return 具体售货机的升级任务列集合
     */
	public List<VendingUpgradeTask> selectVendingUpgradeTaskList(VendingUpgradeTask vendingUpgradeTask);
	
}
