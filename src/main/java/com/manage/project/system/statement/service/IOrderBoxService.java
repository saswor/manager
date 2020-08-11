package com.manage.project.system.statement.service;

import java.util.List;

import com.manage.project.system.statement.domain.OrderBox;
/**
 * 订单商品货道服务层接口
 * @author ldh
 * @title IOrderBoxService
 * @description TODO
 * @time 2019年1月17日下午2:47:04
 */
public interface IOrderBoxService {
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
