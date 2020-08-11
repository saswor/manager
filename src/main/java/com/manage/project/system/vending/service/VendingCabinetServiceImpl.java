package com.manage.project.system.vending.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.vending.mapper.VendingCabinetMapper;
import com.manage.project.system.vending.domain.VendingCabinet;
import com.manage.project.system.vending.service.IVendingCabinetService;
import com.manage.common.support.Convert;
import com.manage.common.utils.security.ShiroUtils;

/**
 * 售货机挂载的货柜，主柜的挂载副柜 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingCabinetServiceImpl implements IVendingCabinetService 
{
	@Autowired
	private VendingCabinetMapper vendingCabinetMapper;

	/**
     * 查询售货机挂载的货柜，主柜的挂载副柜信息
     * 
     * @param logid 售货机挂载的货柜，主柜的挂载副柜ID
     * @return 售货机挂载的货柜，主柜的挂载副柜信息
     */
    @Override
	public VendingCabinet selectVendingCabinetById(String logid)
	{
	    return vendingCabinetMapper.selectVendingCabinetById(logid);
	}
	
	/**
     * 查询售货机挂载的货柜，主柜的挂载副柜列表
     * 
     * @param vendingCabinet 售货机挂载的货柜，主柜的挂载副柜信息
     * @return 售货机挂载的货柜，主柜的挂载副柜集合
     */
	@Override
	public List<VendingCabinet> selectVendingCabinetList(VendingCabinet vendingCabinet)
	{
	    return vendingCabinetMapper.selectVendingCabinetList(vendingCabinet);
	}
	
    /**
     * 新增售货机挂载的货柜，主柜的挂载副柜
     * 
     * @param vendingCabinet 售货机挂载的货柜，主柜的挂载副柜信息
     * @return 结果
     */
	@Override
	public int insertVendingCabinet(VendingCabinet vendingCabinet)
	{
		vendingCabinet.setCorpId(ShiroUtils.getUser().getCorpId());
		vendingCabinet.setLogid(UUID.randomUUID().toString());
	    return vendingCabinetMapper.insertVendingCabinet(vendingCabinet);
	}
	
	/**
     * 修改售货机挂载的货柜，主柜的挂载副柜
     * 
     * @param vendingCabinet 售货机挂载的货柜，主柜的挂载副柜信息
     * @return 结果
     */
	@Override
	public int updateVendingCabinet(VendingCabinet vendingCabinet)
	{
	    return vendingCabinetMapper.updateVendingCabinet(vendingCabinet);
	}

	/**
     * 删除售货机挂载的货柜，主柜的挂载副柜对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingCabinetByIds(String ids)
	{
		return vendingCabinetMapper.deleteVendingCabinetByIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据机型查询是否存在未删除的货柜
	 * 
	 * @param deviceIds
	 * @return
	 */
	@Override
	public Integer selectVendingCabinetListByDeviceIds(String[] deviceIds) {
		return vendingCabinetMapper.selectVendingCabinetListByDeviceIds(deviceIds);
	}
	
}
