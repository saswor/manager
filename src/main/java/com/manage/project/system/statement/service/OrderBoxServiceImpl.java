package com.manage.project.system.statement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.project.system.statement.domain.OrderBox;
import com.manage.project.system.statement.mapper.OrderBoxMapper;
/**
 * 订单商品货道服务层接口实现
 * @author ldh
 * @title IOrderBoxServiceImpl
 * @description TODO
 * @time 2019年1月17日下午2:49:56
 */
@Service
public class OrderBoxServiceImpl implements IOrderBoxService {
    @Autowired
    private OrderBoxMapper orderBoxMapper;
	/**
	 * 查询记录每个商品的货道出库情况信息
	 */
    @Override
	public OrderBox selectOrderBoxById(String proboxId) {
		return orderBoxMapper.selectOrderBoxById(proboxId);
	}
    /**
     * 查询订单商品货道列表
     */
	@Override
	public List<OrderBox> selectOrderBoxList(OrderBox orderBox) {
		return orderBoxMapper.selectOrderBoxList(orderBox);
	}

}
