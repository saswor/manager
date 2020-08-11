package com.manage.project.system.vending.mapper;

import java.util.List;

import com.manage.project.system.vending.domain.VendingUpgradeTask;	

/**
 * 具体售货机的升级任务列 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface VendingUpgradeTaskMapper 
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
	public List<VendingUpgradeTask> selectVendingUpgradeTaskAndUpgradeList(VendingUpgradeTask vendingUpgradeTask);
	
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
     * 删除具体售货机的升级任务列
     * 
     * @param logid 具体售货机的升级任务列ID
     * @return 结果
     */
	public int deleteVendingUpgradeTaskById(String logid);
	
	/**
     * 批量删除具体售货机的升级任务列
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingUpgradeTaskByIds(String[] logids);

	/**
	 * 根据售货机编号查询未完成的升级任务
	 * 
	 * @param vendingUpgradeTask
	 * @return
	 */
	public List<VendingUpgradeTask> selectNotFinishVendingUpgradeTaskListBySiteId(VendingUpgradeTask vendingUpgradeTask);

	/**
	 * 根据升级编号删除记录
	 * 
	 * @param upgradeIds
	 * @return
	 */
	public int deleteVendingUpgradeTaskByUpgradeIds(String[] upgradeIds);
	
	/**
     * 查询具体售货机的升级任务列列表
     * 
     * @param vendingUpgradeTask 具体售货机的升级任务列信息
     * @return 具体售货机的升级任务列集合
     */
	public List<VendingUpgradeTask> selectVendingUpgradeTaskeList(VendingUpgradeTask vendingUpgradeTask);
	
	
}