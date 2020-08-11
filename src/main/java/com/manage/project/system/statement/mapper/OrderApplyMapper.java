/**
 * 
 */
package com.manage.project.system.statement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.manage.project.system.report.vo.TotalVo;
import com.manage.project.system.statement.domain.OrderApply;
import com.manage.project.system.statement.vo.OrderApplyVo;
import com.manage.project.system.statement.vo.OrderBoxDownParamVo;
import com.manage.project.system.statement.vo.OrderBoxExcelVo;
import com.manage.project.system.statement.vo.OrderBoxVo;
import com.manage.project.system.statement.vo.OrderCancelVo;
import com.manage.project.system.statement.vo.OrderProductVo;
import com.manage.project.system.statement.vo.OrderVo;


/**
 * 订单对账数据层
 * 
 * @author zhangjiawei
 * @date 2018年10月17日
 * 
 */
public interface OrderApplyMapper {

	/**
	 * 根据编号查询订单
	 * 
	 * @param 订单编号
	 * @return 订单详情
	 */
	OrderVo selectOrderById(String logid);

	/**
	 * 查询订单列表
	 * 
	 * @param vo 封装的查询条件
	 * @return 订单列表
	 */
	List<OrderVo> selectOrderList(OrderVo vo);
	
	/**
	 * 修改订单列表
	 * 
	 * @param orderApply 订单详情
	 * @return
	 */
	int updateOrderApply(OrderApply orderApply);

	/**
	 * 获取累计销售信息
	 * 
	 * @param vo 查询条件
	 * @return 累计销售信息
	 */
	TotalVo selectTotalSale(OrderVo vo);

	/**
	 * 根据订单编号查询订单
	 * 
	 * @param orderId 订单编号
	 * @return 订单信息
	 */
	OrderVo selectOrderByOrderId(String orderId);

	public List<OrderApply> selectOrderApplyList(OrderApply orderApply);
	
	List<OrderVo> selectOrderProductList(OrderVo vo);

	/**
	 * 查询订单列表
	 * 
	 * @param vo 封装的查询条件
	 * @return 订单列表
	 */
	List<OrderVo> selectOrderApplyList(OrderVo vo);

	List<OrderProductVo> selectProductList(String orderId);
	List<OrderBoxVo> selectBoxList(String orderId);
	
	/**
	 * 下载订单
	 * @param siteId	售卖机id
	 * @param bdate	开始日期
	 * @param edate	结束日期
	 * @param corpId 商户编号
	 * @return
	 */
	public List<OrderBoxExcelVo> selectOrderBoxExcelList(OrderBoxDownParamVo param);

	/**
	 * 查询要取消的订单
	 * 
	 * @param orderCancelVo
	 * @return
	 */
	List<OrderApply> selectOrderForCancel(OrderCancelVo orderCancelVo);
}
