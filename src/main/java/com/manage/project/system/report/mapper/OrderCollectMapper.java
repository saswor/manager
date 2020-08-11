package com.manage.project.system.report.mapper;

import java.util.List;

import com.manage.project.system.report.vo.OrderCollectVo;
import com.manage.project.system.report.vo.OrderDetailVo;
import com.manage.project.system.report.vo.TotalVo;


/**
 * 线路销售额报表数据层
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
public interface OrderCollectMapper {

	/**
	 * 按日查询点位统计数据
	 * 
	 * @param vo  查询条件
	 * @return	点位销售统计数据
	 */
	TotalVo selectTotalByDay(OrderCollectVo vo);

	/**
	 * 按月查询点位统计数据
	 * 
	 * @param vo	查询条件
	 * @return	点位销售统计数据
	 */
	TotalVo selectTotalByMonth(OrderCollectVo vo);

	/**
	 * 按年查询点位统计数据
	 * 
	 * @param vo	查询条件
	 * @return	点位销售统计数据
	 */
	TotalVo selectTotalByYear(OrderCollectVo vo);

	/**
	 * 按日查询订单详细信息
	 * 
	 * @param vo	查询条件
	 * @return	订单详情列表
	 */
	List<OrderDetailVo> selectOrderDetailByDay(OrderCollectVo vo);

	/**
	 * 按月查询订单详细信息
	 * 
	 * @param vo	查询条件
	 * @return	订单详情列表
	 */
	List<OrderDetailVo> selectOrderDetailByMonth(OrderCollectVo vo);

	/**
	 * 按年查询订单详细信息
	 * 
	 * @param vo	查询条件
	 * @return	订单详情列表	 
	 */
	List<OrderDetailVo> selectOrderDetailByYear(OrderCollectVo vo);

	/**
	 * 按日查询营收详情信息
	 * 
	 * @param vo 查询条件
	 * @return	营收详情信息列表
	 */
	List<TotalVo> selectIncomeDetailByDay(OrderCollectVo vo);

	/**
	 * 按月查询营收详情信息
	 * 
	 * @param vo 查询条件
	 * @return	营收详情信息列表	 
	 */
	List<TotalVo> selectIncomeDetailByMonth(OrderCollectVo vo);

	/**
	 * 按年查询营收详情信息
	 * 
	 * @param vo 查询条件
	 * @return	营收详情信息列表	 
	 */
	List<TotalVo> selectIncomeDetailByYear(OrderCollectVo vo);

	TotalVo selectTotalByCurMonth(OrderCollectVo vo);
	
	TotalVo selectTotalByCurYear(OrderCollectVo vo);

	List<TotalVo> selectIncomeDetailByCurMonth(OrderCollectVo vo);
	
	List<TotalVo> selectIncomeDetailByCurYear(OrderCollectVo vo);

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