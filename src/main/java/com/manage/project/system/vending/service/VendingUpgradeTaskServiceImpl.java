package com.manage.project.system.vending.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.common.support.Convert;
import com.manage.project.system.vending.domain.VendingUpgradeTask;
import com.manage.project.system.vending.mapper.VendingUpgradeTaskMapper;

/**
 * 具体售货机的升级任务列 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class VendingUpgradeTaskServiceImpl implements IVendingUpgradeTaskService 
{
	@Autowired
	private VendingUpgradeTaskMapper vendingUpgradeTaskMapper;

	/**
     * 查询具体售货机的升级任务列信息
     * 
     * @param logid 具体售货机的升级任务列ID
     * @return 具体售货机的升级任务列信息
     */
    @Override
	public VendingUpgradeTask selectVendingUpgradeTaskById(String logid)
	{
	    return vendingUpgradeTaskMapper.selectVendingUpgradeTaskById(logid);
	}
	
	/**
     * 查询具体售货机的升级任务列列表
     * 
     * @param vendingUpgradeTask 具体售货机的升级任务列信息
     * @return 具体售货机的升级任务列集合
     */
	@Override
	public List<VendingUpgradeTask> selectVendingUpgradeTaskAndVendingUpgradeList(VendingUpgradeTask vendingUpgradeTask)
	{
	    return vendingUpgradeTaskMapper.selectVendingUpgradeTaskAndUpgradeList(vendingUpgradeTask);
	}
	
    /**
     * 新增具体售货机的升级任务列
     * 
     * @param vendingUpgradeTask 具体售货机的升级任务列信息
     * @return 结果
     */
	@Override
	public int insertVendingUpgradeTask(VendingUpgradeTask vendingUpgradeTask)
	{
		return vendingUpgradeTaskMapper.insertVendingUpgradeTask(vendingUpgradeTask);
	}
	
	/**
     * 修改具体售货机的升级任务列
     * 
     * @param vendingUpgradeTask 具体售货机的升级任务列信息
     * @return 结果
     */
	@Override
	public int updateVendingUpgradeTask(VendingUpgradeTask vendingUpgradeTask)
	{
	    return vendingUpgradeTaskMapper.updateVendingUpgradeTask(vendingUpgradeTask);
	}

	/**
     * 删除具体售货机的升级任务列对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingUpgradeTaskByIds(String ids)
	{
		return vendingUpgradeTaskMapper.deleteVendingUpgradeTaskByIds(Convert.toStrArray(ids));
	}

	/**
     * 查询具体售货机的升级任务列列表
     * 
     * @param vendingUpgradeTask 具体售货机的升级任务列信息
     * @return 具体售货机的升级任务列集合
     */
	@Override
	public List<VendingUpgradeTask> selectVendingUpgradeTaskList(VendingUpgradeTask vendingUpgradeTask) {
		return vendingUpgradeTaskMapper.selectVendingUpgradeTaskeList(vendingUpgradeTask);
	}
	
	
}
