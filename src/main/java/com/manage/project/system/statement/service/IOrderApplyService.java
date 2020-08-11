/**
 * 
 */
package com.manage.project.system.statement.service;

import java.util.List;

import com.manage.project.system.report.vo.TotalVo;
import com.manage.project.system.statement.domain.OrderApply;
import com.manage.project.system.statement.vo.OrderApplyVo;
import com.manage.project.system.statement.vo.OrderBoxDownParamVo;
import com.manage.project.system.statement.vo.OrderBoxExcelVo;
import com.manage.project.system.statement.vo.OrderBoxVo;
import com.manage.project.system.statement.vo.OrderProductVo;
import com.manage.project.system.statement.vo.OrderVo;

/**
 * 订单对账服务层接口
 * 
 * @author zhangjiawei
 * @date 2018年10月17日
 * 
 */
public interface IOrderApplyService {

	/**
	 * 查询记录客户购买的商品信息
     * 
     * @param logid 记录客户购买的商品ID
     * @return 记录客户购买的商品信息
     */
	public OrderVo selectOrderById(String logid);
	
	/**
	 * 查询记录客户购买的商品列表
     * 
     * @param vo 记录客户购买的商品信息
     * @return 记录客户购买的商品集合
     */
	public List<OrderVo> selectOrderList(OrderVo vo);
	
	/**
	 * 查询记录客户购买的订单列表
     * 
     * @param vo 记录客户购买的订单信息
     * @return 记录客户购买的订单集合
     */
	public List<OrderVo> selectOrderApplyList(OrderVo vo);

	/**
	 * 获取累计销售信息
	 * 
	 * @param vo 查询条件
	 * @return 累计销售信息
	 */
	public TotalVo selectTotalSale(OrderVo vo);

	/**
	 * 根据订单编号查询订单
	 * 
	 * @param orderId 订单编号
	 * @return 订单信息
	 */
	public OrderVo selectOrderByOrderId(String orderId);
	
	public List<OrderVo> selectOrderProductList(OrderVo vo);
	
	public List<OrderProductVo> selectProductList(String orderId);
	public List<OrderBoxVo> selectBoxList(String orderId);
	
	/**
	 * 导出订单
	 * 
	 * @param param
	 * @return
	 */
	public List<OrderBoxExcelVo> selectOrderBoxExcelList(OrderBoxDownParamVo param);
}
