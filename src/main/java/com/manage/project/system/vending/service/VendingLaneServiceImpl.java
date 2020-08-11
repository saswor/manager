package com.manage.project.system.vending.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.vending.mapper.VendingLaneMapper;
import com.manage.project.system.vending.domain.VendingLane;
import com.manage.project.system.vending.service.IVendingLaneService;
import com.manage.common.support.Convert;

/**
 * 售货机货道 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingLaneServiceImpl implements IVendingLaneService 
{
	@Autowired
	private VendingLaneMapper vendingLaneMapper;

	/**
     * 查询售货机货道信息
     * 
     * @param logid 售货机货道ID
     * @return 售货机货道信息
     */
    @Override
	public VendingLane selectVendingLaneById(String logid)
	{
	    return vendingLaneMapper.selectVendingLaneById(logid);
	}
	
	/**
     * 查询售货机货道列表
     * 
     * @param vendingLane 售货机货道信息
     * @return 售货机货道集合
     */
	@Override
	public List<VendingLane> selectVendingLaneList(VendingLane vendingLane)
	{
	    return vendingLaneMapper.selectVendingLaneList(vendingLane);
	}
	
    /**
     * 新增售货机货道
     * 
     * @param vendingLane 售货机货道信息
     * @return 结果
     */
	@Override
	public int insertVendingLane(VendingLane vendingLane)
	{
	    return vendingLaneMapper.insertVendingLane(vendingLane);
	}
	
	/**
     * 修改售货机货道
     * 
     * @param vendingLane 售货机货道信息
     * @return 结果
     */
	@Override
	public int updateVendingLane(VendingLane vendingLane)
	{
	    return vendingLaneMapper.updateVendingLane(vendingLane);
	}

	/**
     * 删除售货机货道对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingLaneByIds(String ids)
	{
		return vendingLaneMapper.deleteVendingLaneByIds(Convert.toStrArray(ids));
	}

	@Override
	public List<VendingLane> selectVendingLaneByCabinetId(String cabinetId) {
		return vendingLaneMapper.selectVendingLaneByCabinetId(cabinetId);
	}
	
}
