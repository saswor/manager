package com.manage.project.system.vending.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.common.support.Convert;
import com.manage.project.system.vending.domain.VendingState;
import com.manage.project.system.vending.mapper.VendingStateMapper;

/**
 * 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class VendingStateServiceImpl implements IVendingStateService 
{
	@Autowired
	private VendingStateMapper vendingStateMapper;

	/**
     * 查询保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     * 
     * @param logid 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。ID
     * @return 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     */
    @Override
	public VendingState selectVendingStateById(String siteId)
	{
	    return vendingStateMapper.selectVendingStateById(siteId);
	}
	
	/**
     * 查询保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。列表
     * 
     * @param vendingState 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     * @return 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。集合
     */
	@Override
	public List<VendingState> selectVendingStateList(VendingState vendingState)
	{
	    return vendingStateMapper.selectVendingStateList(vendingState);
	}
	
    /**
     * 新增保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。
     * 
     * @param vendingState 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     * @return 结果
     */
	@Override
	public int insertVendingState(VendingState vendingState)
	{
	    return vendingStateMapper.insertVendingState(vendingState);
	}
	
	/**
     * 修改保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。
     * 
     * @param vendingState 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。信息
     * @return 结果
     */
	@Override
	public int updateVendingState(VendingState vendingState)
	{
	    return vendingStateMapper.updateVendingState(vendingState);
	}

	/**
     * 删除保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingStateByIds(String ids)
	{
		return vendingStateMapper.deleteVendingStateByIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据售货机编号查询售货机状态
	 * 
	 * @param siteId 售货机编号
	 * @return
	 */
	@Override
	public VendingState selectVendingStateBySiteId(String siteId) {
		return vendingStateMapper.selectVendingStateBySiteId(siteId);
	}
	
}
