package com.manage.project.system.vending.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.vending.mapper.VendingWarnMapper;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingWarn;
import com.manage.project.system.vending.service.IVendingWarnService;
import com.manage.common.support.Convert;

/**
 * 设备告警 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingWarnServiceImpl implements IVendingWarnService 
{
	@Autowired
	private VendingWarnMapper vendingWarnMapper;

	/**
     * 查询设备告警信息
     * 
     * @param logid 设备告警ID
     * @return 设备告警信息
     */
    @Override
	public VendingWarn selectVendingWarnById(String logid)
	{
	    return vendingWarnMapper.selectVendingWarnById(logid);
	}
	
	/**
     * 查询设备告警列表
     * 
     * @param vendingWarn 设备告警信息
     * @return 设备告警集合
     */
	@Override
	public List<VendingWarn> selectVendingWarnList(VendingWarn vendingWarn)
	{
	    return vendingWarnMapper.selectVendingWarnList(vendingWarn);
	}
	
    /**
     * 新增设备告警
     * 
     * @param vendingWarn 设备告警信息
     * @return 结果
     */
	@Override
	public int insertVendingWarn(VendingWarn vendingWarn)
	{
		vendingWarn.setEventId(SystemUtil.getSeqId(vendingWarn.getCorpId(), "as_vending_warn"));
	    return vendingWarnMapper.insertVendingWarn(vendingWarn);
	}
	
	/**
     * 修改设备告警
     * 
     * @param vendingWarn 设备告警信息
     * @return 结果
     */
	@Override
	public int updateVendingWarn(VendingWarn vendingWarn)
	{
	    return vendingWarnMapper.updateVendingWarn(vendingWarn);
	}

	/**
     * 删除设备告警对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingWarnByIds(String ids)
	{
		return vendingWarnMapper.deleteVendingWarnByIds(Convert.toStrArray(ids));
	}
	
}
