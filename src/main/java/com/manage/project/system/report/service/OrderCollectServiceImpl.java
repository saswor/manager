/**
 * 
 */
package com.manage.project.system.report.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.report.mapper.OrderCollectMapper;
import com.manage.project.system.report.vo.OrderCollectVo;
import com.manage.project.system.report.vo.OrderDetailVo;
import com.manage.project.system.report.vo.TotalVo;

/**
 * 订单汇总服务层实现
 * 
 * @author zhangjiawei
 * @date 2018年10月9日
 * 
 */
@Service
public class OrderCollectServiceImpl implements IOrderCollectService{

	@Autowired
	private OrderCollectMapper orderCollectMapper;

	/**
	 * 按日查询营销总额
	 * 
	 * @param vo 查询条件
	 * @return 日销售总额
	 */
	@Override
	public TotalVo selectTotalByDay(OrderCollectVo vo) {
		return orderCollectMapper.selectTotalByDay(vo);
	}

	/**
	 * 按月查询营销总额
	 * 
	 * @param vo 查询条件
	 * @return 月销售总额
	 */
	@Override
	public TotalVo selectTotalByMonth(OrderCollectVo vo) {
//		Calendar calendar = Calendar.getInstance();
//		int curMonth = calendar.get(Calendar.MONTH)+1;
//		//如果是当前月份,查询日报表
//		if(curMonth==vo.getMonth()) {
//			return orderCollectMapper.selectTotalByCurMonth(vo);
//		}
		return orderCollectMapper.selectTotalByMonth(vo);
	}

	/**
	 * 按年查询营销总额
	 * 
	 * @param vo 查询条件
	 * @return 年销售总额
	 */
	@Override
	public TotalVo selectTotalByYear(OrderCollectVo vo) {
//		Calendar calendar = Calendar.getInstance();
//		int curYear = calendar.get(Calendar.YEAR);
//		//如果是当前年,查询日报表
//		if(curYear==vo.getYear()) {
//			return orderCollectMapper.selectTotalByCurYear(vo);
//		}
		return orderCollectMapper.selectTotalByYear(vo);
	}

	/**
	 * 按日查询详细营销信息
	 * 
	 * @param vo 查询条件
	 * @return 日销售详细信息
	 */
	@Override
	public List<TotalVo> selectIncomeDetailByDay(OrderCollectVo vo) {
		return orderCollectMapper.selectIncomeDetailByDay(vo);
	}

	/**
	 * 按月查询详细营销信息
	 * 
	 * @param vo 查询条件
	 * @return 日销售详细信息
	 */
	@Override
	public List<TotalVo> selectIncomeDetailByMonth(OrderCollectVo vo) {
//		Calendar calendar = Calendar.getInstance();
//		int curMonth = calendar.get(Calendar.MONTH)+1;
//		//如果是当前月份,查询日报表
//		if(curMonth==vo.getMonth()) {
//			return orderCollectMapper.selectIncomeDetailByCurMonth(vo);
//		}
		return orderCollectMapper.selectIncomeDetailByMonth(vo);
	}

	/**
	 * 按年查询详细营销信息
	 * 
	 * @param vo 查询条件
	 * @return 年销售详细信息
	 */
	@Override
	public List<TotalVo> selectIncomeDetailByYear(OrderCollectVo vo) {
//		Calendar calendar = Calendar.getInstance();
//		int curYear = calendar.get(Calendar.YEAR);
//		//如果是当前年,查询日报表
//		if(curYear==vo.getYear()) {
//			return orderCollectMapper.selectIncomeDetailByCurYear(vo);
//		}
		return orderCollectMapper.selectIncomeDetailByYear(vo);
	}

	/**
	 * 按日查询详细订单信息
	 * 
	 * @param vo 查询条件
	 * @return 日订单详细信息
	 */
	@Override
	public List<OrderDetailVo> selectOrderDetailByDay(OrderCollectVo vo) {
		return orderCollectMapper.selectOrderDetailByDay(vo);
	}

	/**
	 * 按月查询详细订单信息
	 * 
	 * @param vo 查询条件
	 * @return 月订单详细信息
	 */
	@Override
	public List<OrderDetailVo> selectOrderDetailByMonth(OrderCollectVo vo) {
		return orderCollectMapper.selectOrderDetailByMonth(vo);
	}

	/**
	 * 按月查询详细订单信息
	 * 
	 * @param vo 查询条件
	 * @return 月订单详细信息
	 */
	@Override
	public List<OrderDetailVo> selectOrderDetailByYear(OrderCollectVo vo) {
		return orderCollectMapper.selectOrderDetailByYear(vo);
	}

	/**
	 * 查询订单详情
	 * 
	 * @param vo
	 * @return
	 */
	@Override
	public List<OrderDetailVo> selectOrderDetail(OrderCollectVo vo) {
		return orderCollectMapper.selectOrderDetail(vo);
	}

	/**
	 * 查询季度营销总额
	 * 
	 * @param vo
	 * @return
	 */
	@Override
	public TotalVo selectTotalByQuarter(OrderCollectVo vo) {
		return orderCollectMapper.selectTotalByQuarter(vo);
	}

	/**
	 * 查询半年营销总额
	 * 
	 * @param vo
	 * @return
	 */
	@Override
	public TotalVo selectTotalByHalfYear(OrderCollectVo vo) {
		return orderCollectMapper.selectTotalByHalfYear(vo);
	}

	/**
	 * 按季度查询详细营销信息
	 * 
	 * @param vo 查询条件
	 * @return 年销售详细信息
	 */
	@Override
	public List<TotalVo> selectIncomeDetailByQuarter(OrderCollectVo vo) {
		return orderCollectMapper.selectIncomeDetailByQuarter(vo);
	}

	/**
	 * 按半年查询详细营销信息
	 * 
	 * @param vo 查询条件
	 * @return 年销售详细信息
	 */
	@Override
	public List<TotalVo> selectIncomeDetailByHalfYear(OrderCollectVo vo) {
		return orderCollectMapper.selectIncomeDetailByHalfYear(vo);
	}

}
