package com.manage.project.system.vending.service;

import com.manage.project.system.vending.domain.VendingWarn;
import java.util.List;

/**
 * 设备告警 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IVendingWarnService 
{
	/**
     * 查询设备告警信息
     * 
     * @param logid 设备告警ID
     * @return 设备告警信息
     */
	public VendingWarn selectVendingWarnById(String logid);
	
	/**
     * 查询设备告警列表
     * 
     * @param vendingWarn 设备告警信息
     * @return 设备告警集合
     */
	public List<VendingWarn> selectVendingWarnList(VendingWarn vendingWarn);
	
	/**
     * 新增设备告警
     * 
     * @param vendingWarn 设备告警信息
     * @return 结果
     */
	public int insertVendingWarn(VendingWarn vendingWarn);
	
	/**
     * 修改设备告警
     * 
     * @param vendingWarn 设备告警信息
     * @return 结果
     */
	public int updateVendingWarn(VendingWarn vendingWarn);
		
	/**
     * 删除设备告警信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingWarnByIds(String ids);
	
}
