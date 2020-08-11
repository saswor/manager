package com.manage.project.system.vending.service;

import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.vending.domain.VendingPconfig;
import com.manage.project.system.vending.vo.PconfigSaveVo;

import java.io.IOException;
import java.util.List;

/**
 * 售货机配货模板 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IVendingPconfigService 
{
	/**
     * 查询售货机配货模板信息
     * 
     * @param logid 售货机配货模板ID
     * @return 售货机配货模板信息
     */
	public VendingPconfig selectVendingPconfigById(String logid);
	
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
	public int insertVendingPconfig(PconfigSaveVo pconfigSaveVo);
	
	/**
     * 修改售货机配货模板
     * 
     * @param vendingPconfig 售货机配货模板信息
     * @return 结果
     */
	public int updateVendingPconfig(PconfigSaveVo pconfigSaveVo);
		
	/**
     * 删除售货机配货模板信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingPconfigByIds(String ids);
	
	/**
     * 查询售货机配货模板信息
     * 
     * @param mConfigId 售货机配货模板ID
     * @return 售货机配货模板信息
     */
	public VendingPconfig selectVendingPconfigByMconfigId(String mConfigId);
	
	/**
	 * 关联
	 * @param mConfigId
	 * @param siteId
	 * @return
	 */
	public int relation(String mConfigId, String cabinetId);

	/**
	 * 导出配货模板
	 * 
	 * @param pconfigSaveVo
	 * @return
	 */
	public AjaxResult export(PconfigSaveVo pconfigSaveVo) throws IOException;

	/**
	 * 查询绑定了该机型的配货模板
	 * 
	 * @param deviceIds
	 * @return
	 */
	public int selectVendingPconfigListByDeviceIds(String[] deviceIds);
}
