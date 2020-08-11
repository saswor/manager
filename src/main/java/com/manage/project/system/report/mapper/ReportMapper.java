/**
 * 
 */
package com.manage.project.system.report.mapper;

import java.util.List;
import java.util.Map;

import com.manage.project.system.report.vo.ReportVo;
import com.manage.project.system.report.vo.LineSaleMoneyVo;
import com.manage.project.system.report.vo.PointSaleMoneyVo;

/**
 * 线路销售额报表
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
public interface ReportMapper {

	/**
	 * 查询线路销售额列表
	 * 
	 * @param vo 查询条件
	 * @return 线路销售额列表
	 */
	List<LineSaleMoneyVo> selectLineSaleList(ReportVo vo);

	/**
	 * 查询线路每日详情
	 * 
	 * @param lineReportVo 查询条件
	 * @return 线路每日详情
	 */
	List<Map<String,Object>> selectLineSaleDetail(ReportVo lineReportVo);

	/**
	 * 查询线路报表(导出专用)
	 * 
	 * @param vo 查询条件
	 * @return 线路销售额列表
	 */
	List<LineSaleMoneyVo> selectLineSaleListForExport(ReportVo vo);

	/**
	 * 查询线路月报表
	 * 
	 * @param lineReportVo 查询条件
	 * @return 线路每月详情
	 */
	List<LineSaleMoneyVo> selectLineSaleMonth(ReportVo vo);

	/**
	 * 查询线路日报表
	 * 
	 * @param lineReportVo 查询条件
	 * @return 线路每日详情
	 */
	List<Map<String, Object>> selectLineSaleDay(ReportVo vo);

	/**
	 * 查询点位日报表
	 * 
	 * @param vo 查询条件
	 * @return 点位每日详情
	 */
	List<Map<String, Object>> selectPointSaleDay(ReportVo vo);

	/**
	 * 查询点位月报表
	 * 
	 * @param vo 查询条件
	 * @return 点位每月详情
	 */
	List<PointSaleMoneyVo> selectPointSaleMonth(ReportVo vo);

	/**
	 * 查询点位报表(导出专用)
	 * 
	 * @param vo 查询条件
	 * @return 点位销售额列表
	 */
	List<PointSaleMoneyVo> selectPointSaleListForExport(ReportVo vo);
	
	List<LineSaleMoneyVo> selectLineSaleCurMonth(ReportVo vo);

	List<PointSaleMoneyVo> selectPointSaleCurMonth(ReportVo vo);
	
	List<LineSaleMoneyVo> selectLineSaleCurMonthForExport(ReportVo vo);

	List<PointSaleMoneyVo> selectPointSaleCurMonthForExport(ReportVo vo);

}
