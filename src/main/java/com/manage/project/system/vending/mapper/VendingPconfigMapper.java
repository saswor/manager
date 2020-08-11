package com.manage.project.system.vending.mapper;

import com.manage.project.system.vending.domain.VendingPconfig;
import java.util.List;	

/**
 * 售货机配货模板 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface VendingPconfigMapper 
{
	/**
     * 查询售货机配货模板信息
     * 
     * @param logid 售货机配货模板logID
     * @return 售货机配货模板信息
     */
	public VendingPconfig selectVendingPconfigById(String logid);
	
	/**
     * 查询售货机配货模板信息
     * 
     * @param mConfigId 售货机配货模板ID
     * @return 售货机配货模板信息
     */
	public VendingPconfig selectVendingPconfigByMconfigId(String mConfigId);
	
	/**
     * 查询售货机配货模板列表
     * 
     * @param vendingPconfig 售货机配货模板信息
     * @return 售货机配货模板集合
     */
	public List<VendingPconfig> selectVendingPconfigList(VendingPconfig vendingPconfig);
	
	/**
     * 新增售货机配货模板
     * 
     * @param vendingPconfig 售货机配货模板信息
     * @return 结果
     */
	public int insertVendingPconfig(VendingPconfig vendingPconfig);
	
	/**
     * 修改售货机配货模板
     * 
     * @param vendingPconfig 售货机配货模板信息
     * @return 结果
     */
	public int updateVendingPconfig(VendingPconfig vendingPconfig);
	
	/**
     * 删除售货机配货模板
     * 
     * @param logid 售货机配货模板ID
     * @return 结果
     */
	public int deleteVendingPconfigById(String logid);
	
	/**
     * 批量删除售货机配货模板
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingPconfigByIds(String[] logids);

	/**
	 * 查询绑定了该机型的配货模板
	 * 
	 * @param deviceIds
	 * @return
	 */
	public int selectVendingPconfigListByDeviceIds(String[] deviceIds);
}