package com.manage.project.system.supply.service;

import java.util.List;

import com.manage.project.system.supply.domain.SupplyVending;
import com.manage.project.system.supply.mapper.SupplyVendingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.common.support.Convert;

/**
 * 补货配置的售货机 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class SupplyVendingServiceImpl implements ISupplyVendingService 
{
	@Autowired
	private SupplyVendingMapper supplyVendingMapper;

	/**
     * 查询补货配置的售货机信息
     * 
     * @param logid 补货配置的售货机ID
     * @return 补货配置的售货机信息
     */
    @Override
	public SupplyVending selectSupplyVendingById(String logid)
	{
	    return supplyVendingMapper.selectSupplyVendingById(logid);
	}
	
	/**
     * 查询补货配置的售货机列表
     * 
     * @param supplyVending 补货配置的售货机信息
     * @return 补货配置的售货机集合
     */
	@Override
	public List<SupplyVending> selectSupplyVendingList(SupplyVending supplyVending)
	{
	    return supplyVendingMapper.selectSupplyVendingList(supplyVending);
	}
	
    /**
     * 新增补货配置的售货机
     * 
     * @param supplyVending 补货配置的售货机信息
     * @return 结果
     */
	@Override
	public int insertSupplyVending(SupplyVending supplyVending)
	{
	    return supplyVendingMapper.insertSupplyVending(supplyVending);
	}
	
	/**
     * 修改补货配置的售货机
     * 
     * @param supplyVending 补货配置的售货机信息
     * @return 结果
     */
	@Override
	public int updateSupplyVending(SupplyVending supplyVending)
	{
	    return supplyVendingMapper.updateSupplyVending(supplyVending);
	}

	/**
     * 删除补货配置的售货机对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSupplyVendingByIds(String ids)
	{
		return supplyVendingMapper.deleteSupplyVendingByIds(Convert.toStrArray(ids));
	}
	public SupplyVending selectSupplyVendingBySiteId(String siteId){
		return supplyVendingMapper.selectSupplyVendingBySiteId(siteId);
	}

	public int deleteSupplyVendingBySupplyIds(String ids){
		return supplyVendingMapper.deleteSupplyVendingBySupplyIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据站点id和补货配置编号查询站点补货配置
	 * 
	 * @param supplyVending 封装补货配置编号和站点编号
	 * @return 站点补货配置
	 */
	@Override
	public SupplyVending selectSupplyVendingBySiteIdAndSupplyId(SupplyVending supplyVending) {
		return supplyVendingMapper.selectSupplyVendingBySiteIdAndSupplyId(supplyVending);
	}
}
