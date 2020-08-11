package com.manage.project.system.vending.service;

import java.util.List;

import com.manage.project.system.vending.domain.VendingCmd;

/**
 * 售货机命令，按站点队列执行 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface IVendingCmdService 
{
	/**
     * 查询售货机命令，按站点队列执行信息
     * 
     * @param logid 售货机命令，按站点队列执行ID
     * @return 售货机命令，按站点队列执行信息
     */
	public VendingCmd selectVendingCmdById(String logid);
	
	/**
     * 查询售货机命令，按站点队列执行列表
     * 
     * @param vendingCmd 售货机命令，按站点队列执行信息
     * @return 售货机命令，按站点队列执行集合
     */
	public List<VendingCmd> selectVendingCmdList(VendingCmd vendingCmd);
	
	/**
     * 新增售货机命令，按站点队列执行
     * 
     * @param vendingCmd 售货机命令，按站点队列执行信息
     * @return 结果
     */
	public int insertVendingCmd(VendingCmd vendingCmd);
	
	/**
     * 修改售货机命令，按站点队列执行
     * 
     * @param vendingCmd 售货机命令，按站点队列执行信息
     * @return 结果
     */
	public int updateVendingCmd(VendingCmd vendingCmd);
		
	/**
     * 删除售货机命令，按站点队列执行信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingCmdByIds(String ids);
	
	/**
     * 新增售货机命令，按站点队列执行
     * 
     * @param siteId 售货机编号
     * @param vendingCmd 售货机命令，按站点队列执行信息
     * @return 结果
     */
	public int insertVendingCmdQuick(String siteId,VendingCmd vendingCmd,String corpId);
	
}
