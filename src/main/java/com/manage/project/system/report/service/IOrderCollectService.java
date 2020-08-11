/**
 * 
 */
package com.manage.project.system.report.service;

import java.util.List;
import com.manage.project.system.report.vo.OrderCollectVo;
import com.manage.project.system.report.vo.OrderDetailVo;
import com.manage.project.system.report.vo.TotalVo;

/**
 * 订单汇总服务层接口
 * 
 * @author zhangjiawei
 * @date 2018年10月9日
 * 
 */
public interface IOrderCollectService {

	/**
	 * 按日查询营销总额
	 * 
	 * @param vo 查询条件
	 * @return 日销售总额
	 */
	TotalVo selectTotalByDay(OrderCollectVo vo);

	/**
	 * 按月查询营销总额
	 * 
	 * @param vo 查询条件
	 * @return 月销售总额
	 */
	TotalVo selectTotalByMonth(OrderCollectVo vo);

	/**
	 * 按年查询营销总额
	 * 
	 * @param vo 查询条件
	 * @return 年销售总额
	 */
	TotalVo selectTotalByYear(OrderCollectVo vo);

	/**
	 * 按日查询详细营销信息
	 * 
	 * @param vo 查询条件
	 * @return 日销售详细信息
	 */
	List<TotalVo> selectIncomeDetailByDay(OrderCollectVo vo);

	/**
	 * 按月查询详细营销信息
	 * 
	 * @param vo 查询条件
	 * @return 月销售详细信息
	 */
	List<TotalVo> selectIncomeDetailByMonth(OrderCollectVo vo);

	/**
	 * 按年查询详细营销信息
	 * 
	 * @param vo 查询条件
	 * @return 年销售详细信息
	 */
	List<TotalVo> selectIncomeDetailByYear(OrderCollectVo vo);

	/**
	 * 按日查询详细订单信息
	 * 
	 * @param vo 查询条件
	 * @return 日订单详细信息
	 */
	List<OrderDetailVo> selectOrderDetailByDay(OrderCollectVo vo);

	/**
	 * 按月查询详细订单信息
	 * 
	 * @param vo 查询条件
	 * @return 月订单详细信息
	 */
	List<OrderDetailVo> selectOrderDetailByMonth(OrderCollectVo vo);

	/**
	 * 按年查询详细订单信息
	 * 
	 * @param vo 查询条件
	 * @return 年订单详细信息
	 */
	List<OrderDetailVo> selectOrderDetailByYear(OrderCollectVo vo);

	/**
	 * 查询订单详情
	 * 
	 * @param vo
	 * @return
	 */
	List<OrderDetailVo> selectOrderDetail(OrderCollectVo vo);

	/**
	 * 查询季度营销总额
	 * 
	 * @param vo
	 * @return
	 */
	TotalVo selectTotalByQuarter(OrderCollectVo vo);

	/**
	 * 查询半年营销总额
	 * 
	 * @param vo
	 * @return
	 */
	TotalVo selectTotalByHalfYear(OrderCollectVo vo);

	/**
	 * 按季度查询详细营销信息
	 * 
	 * @param vo 查询条件
	 * @return 年销售详细信息
	 */
	List<TotalVo> selectIncomeDetailByQuarter(OrderCollectVo vo);

	/**
	 * 按半年查询详细营销信息
	 * 
	 * @param vo 查询条件
	 * @return 年销售详细信息
	 */
	List<TotalVo> selectIncomeDetailByHalfYear(OrderCollectVo vo);

}
