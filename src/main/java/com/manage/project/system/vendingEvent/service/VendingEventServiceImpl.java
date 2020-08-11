package com.manage.project.system.vendingEvent.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.vendingEvent.mapper.VendingEventMapper;
import com.manage.project.system.vendingEvent.domain.VendingEvent;
import com.manage.project.system.vendingEvent.service.IVendingEventService;
import com.manage.common.support.Convert;

/**
 * 售货机的事件列 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingEventServiceImpl implements IVendingEventService 
{
	@Autowired
	private VendingEventMapper vendingEventMapper;

	/**
     * 查询售货机的事件列信息
     * 
     * @param logid 售货机的事件列ID
     * @return 售货机的事件列信息
     */
    @Override
	public VendingEvent selectVendingEventById(String logid)
	{
	    return vendingEventMapper.selectVendingEventById(logid);
	}
	
	/**
     * 查询售货机的事件列列表
     * 
     * @param vendingEvent 售货机的事件列信息
     * @return 售货机的事件列集合
     */
	@Override
	public List<VendingEvent> selectVendingEventList(VendingEvent vendingEvent)
	{
	    return vendingEventMapper.selectVendingEventList(vendingEvent);
	}
	
    /**
     * 新增售货机的事件列
     * 
     * @param vendingEvent 售货机的事件列信息
     * @return 结果
     */
	@Override
	public int insertVendingEvent(VendingEvent vendingEvent)
	{
	    return vendingEventMapper.insertVendingEvent(vendingEvent);
	}
	
	/**
     * 修改售货机的事件列
     * 
     * @param vendingEvent 售货机的事件列信息
     * @return 结果
     */
	@Override
	public int updateVendingEvent(VendingEvent vendingEvent)
	{
	    return vendingEventMapper.updateVendingEvent(vendingEvent);
	}

	/**
     * 删除售货机的事件列对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingEventByIds(String ids)
	{
		return vendingEventMapper.deleteVendingEventByIds(Convert.toStrArray(ids));
	}
	
}
