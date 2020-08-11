package com.manage.project.system.vendingEvent.mapper;

import com.manage.project.system.vendingEvent.domain.VendingEvent;
import java.util.List;	

/**
 * 售货机的事件列 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface VendingEventMapper 
{
	/**
     * 查询售货机的事件列信息
     * 
     * @param logid 售货机的事件列ID
     * @return 售货机的事件列信息
     */
	public VendingEvent selectVendingEventById(String logid);
	
	/**
     * 查询售货机的事件列列表
     * 
     * @param vendingEvent 售货机的事件列信息
     * @return 售货机的事件列集合
     */
	public List<VendingEvent> selectVendingEventList(VendingEvent vendingEvent);
	
	/**
     * 新增售货机的事件列
     * 
     * @param vendingEvent 售货机的事件列信息
     * @return 结果
     */
	public int insertVendingEvent(VendingEvent vendingEvent);
	
	/**
     * 修改售货机的事件列
     * 
     * @param vendingEvent 售货机的事件列信息
     * @return 结果
     */
	public int updateVendingEvent(VendingEvent vendingEvent);
	
	/**
     * 删除售货机的事件列
     * 
     * @param logid 售货机的事件列ID
     * @return 结果
     */
	public int deleteVendingEventById(String logid);
	
	/**
     * 批量删除售货机的事件列
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingEventByIds(String[] logids);
	
}