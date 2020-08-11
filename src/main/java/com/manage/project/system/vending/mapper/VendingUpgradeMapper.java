package com.manage.project.system.vending.mapper;

import java.util.List;

import com.manage.project.system.vending.domain.VendingUpgrade;	

/**
 * 控制设备的升级，升级包括app升级、固件升级、视频软件升级 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface VendingUpgradeMapper 
{
	/**
     * 查询控制设备的升级，升级包括app升级、固件升级、视频软件升级信息
     * 
     * @param logid 控制设备的升级，升级包括app升级、固件升级、视频软件升级ID
     * @return 控制设备的升级，升级包括app升级、固件升级、视频软件升级信息
     */
	public VendingUpgrade selectVendingUpgradeById(String upgradeId);
	
	/**
     * 查询控制设备的升级，升级包括app升级、固件升级、视频软件升级列表
     * 
     * @param vendingUpgrade 控制设备的升级，升级包括app升级、固件升级、视频软件升级信息
     * @return 控制设备的升级，升级包括app升级、固件升级、视频软件升级集合
     */
	public List<VendingUpgrade> selectVendingUpgradeList(VendingUpgrade vendingUpgrade);
	
	/**
     * 新增控制设备的升级，升级包括app升级、固件升级、视频软件升级
     * 
     * @param vendingUpgrade 控制设备的升级，升级包括app升级、固件升级、视频软件升级信息
     * @return 结果
     */
	public int insertVendingUpgrade(VendingUpgrade vendingUpgrade);
	
	/**
     * 修改控制设备的升级，升级包括app升级、固件升级、视频软件升级
     * 
     * @param vendingUpgrade 控制设备的升级，升级包括app升级、固件升级、视频软件升级信息
     * @return 结果
     */
	public int updateVendingUpgrade(VendingUpgrade vendingUpgrade);
	
	/**
     * 删除控制设备的升级，升级包括app升级、固件升级、视频软件升级
     * 
     * @param logid 控制设备的升级，升级包括app升级、固件升级、视频软件升级ID
     * @return 结果
     */
	public int deleteVendingUpgradeById(String logid);
	
	/**
     * 批量删除控制设备的升级，升级包括app升级、固件升级、视频软件升级
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingUpgradeByIds(String[] logids);

	/**
	 * 查询未推送的升级任务
	 * 
	 * @param vendingUpgrade
	 * @return
	 */
	public List<VendingUpgrade> selectNotPushVendingUpgradeList(VendingUpgrade vendingUpgrade);
	
}