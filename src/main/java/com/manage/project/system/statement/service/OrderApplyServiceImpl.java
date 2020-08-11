/**
 * 
 */
package com.manage.project.system.statement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.project.system.report.vo.TotalVo;
import com.manage.project.system.statement.mapper.OrderApplyMapper;
import com.manage.project.system.statement.vo.OrderBoxDownParamVo;
import com.manage.project.system.statement.vo.OrderBoxExcelVo;
import com.manage.project.system.statement.vo.OrderBoxVo;
import com.manage.project.system.statement.vo.OrderProductVo;
import com.manage.project.system.statement.vo.OrderVo;

/**
 * @author zhangjiawei
 * @date 2018年10月17日
 * 
 */
@Service
public class OrderApplyServiceImpl implements IOrderApplyService{

	@Autowired
	private OrderApplyMapper orderApplyMapper;
	
	/**
	 * 查询订单详细信息
     * 
     * @param logid 订单ID
     * @return 订单详细信息
     */
    @Override
	public OrderVo selectOrderById(String logid)
	{
	    return orderApplyMapper.selectOrderById(logid);
	}
	
	/**
	 * 查询订单列表
     * 
     * @param vo 封装的查询条件
     * @return 订单列表
     */
	@Override
	public List<OrderVo> selectOrderList(OrderVo vo)
	{
	    return orderApplyMapper.selectOrderList(vo);
	}

	/**
	 * 获取累计销售信息
	 * 
	 * @param vo 查询条件
	 * @return 累计销售信息
	 */
	@Override
	public TotalVo selectTotalSale(OrderVo vo) {
		return orderApplyMapper.selectTotalSale(vo);
	}

	/**
	 * 根据订单编号查询订单
	 * 
	 * @param orderId 订单编号
	 * @return 订单信息
	 */
	@Override
	public OrderVo selectOrderByOrderId(String orderId) {
		return orderApplyMapper.selectOrderByOrderId(orderId);
	}

	@Override
	public List<OrderVo> selectOrderApplyList(OrderVo vo) {
		
		return orderApplyMapper.selectOrderApplyList(vo);
	}

	@Override
	public List<OrderVo> selectOrderProductList(OrderVo vo) {
		
		return orderApplyMapper.selectOrderProductList(vo);
	}

	@Override
	public List<OrderProductVo> selectProductList(String orderId) {
		
		return orderApplyMapper.selectProductList(orderId);
	}

	@Override
	public List<OrderBoxVo> selectBoxList(String orderId) {
		
		return orderApplyMapper.selectBoxList(orderId);
	}

	@Override
	public List<OrderBoxExcelVo> selectOrderBoxExcelList(OrderBoxDownParamVo param) {
		return orderApplyMapper.selectOrderBoxExcelList(param);
	}

}
