package com.manage.project.system.statement.mapper;

import java.util.List;

import com.manage.project.system.statement.domain.OrderBox;

/**
 * 订单商品货道数据层
 * @author ldh
 * @title OrderBoxMapper
 * @description TODO
 * @time 2019年1月17日上午11:17:30
 */
public interface OrderBoxMapper 
{
	/**
     * 查询记录每个商品的货道出库情况信息
     * @param proboxId 商品货道编号
     * @return 订单商品货道信息
     */
	public OrderBox selectOrderBoxById(String proboxId);
	
	/**
     * 查询订单商品货道列表
     * 
     * @param OrderBox 订单商品货道
     * @return 订单商品货道集合
     */
	public List<OrderBox> selectOrderBoxList(OrderBox orderBox);
}