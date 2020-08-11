/**
 * 
 */
package com.manage.project.system.report.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.project.system.report.mapper.ReportMapper;
import com.manage.project.system.report.vo.ReportVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vendingPoint.domain.VendingPoint;
import com.manage.project.system.vendingPoint.mapper.VendingPointMapper;
import com.manage.project.system.report.vo.LineSaleMoneyVo;
import com.manage.project.system.report.vo.PointSaleMoneyVo;

/**
 * 线路销售报表服务层实现
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
@Service
public class ReportServiceImpl implements IReportService{

	@Autowired
	private ReportMapper reportMapper;
	
	@Autowired
	private VendingPointMapper vendingPointMapper;

	/**
	 * 查询线路销售额
	 * 
	 * @param vo 查询条件
	 * @return 线路销售额列表
	 */
	@Override
	public List<LineSaleMoneyVo> selectLineSaleList(ReportVo vo) {
		return reportMapper.selectLineSaleList(vo);
	}

	/**
	 * 查询线路每日详情
	 * 
	 * @param vo 查询条件
	 * @return 线路每日详情
	 */
	@Override
	public List<Map<String,Object>> selectLineSaleDetail(ReportVo vo) {
		return reportMapper.selectLineSaleDetail(vo);
	}

	/**
	 * 查询线路报表(导出专用)
	 * 
	 * @param vo 查询条件
	 * @return 线路销售额列表
	 */
	@Override
	public List<LineSaleMoneyVo> selectLineSaleListForExport(ReportVo vo) {
		Calendar calendar = Calendar.getInstance();
		int curMonth = calendar.get(Calendar.MONTH)+1;
		//如果是当前月份,查询日报表
		if(curMonth==vo.getSmonth()) {
			return reportMapper.selectLineSaleCurMonthForExport(vo);
		}
		return reportMapper.selectLineSaleListForExport(vo);
	}

	/**
	 * 查询线路月报表
	 * 
	 * @param vo 查询条件
	 * @return 线路每月详情
	 */
	@Override
	public List<LineSaleMoneyVo> selectLineSaleMonth(ReportVo vo) {
		Calendar calendar = Calendar.getInstance();
		int curMonth = calendar.get(Calendar.MONTH)+1;
		//如果是当前月份,查询日报表
		if(curMonth==vo.getSmonth()) {
			List<LineSaleMoneyVo> list = reportMapper.selectLineSaleCurMonth(vo);
			for (LineSaleMoneyVo lineSaleMoneyVo : list) {
				//查询线路下的点位数
				VendingPoint vendingPoint = new VendingPoint();
				vendingPoint.setLineId(lineSaleMoneyVo.getLineId());
				List<VendingPoint> pointList = vendingPointMapper.selectVendingPoint(vendingPoint);
				if(pointList!=null) {
					lineSaleMoneyVo.setPointNum(pointList.size());
				}else {
					lineSaleMoneyVo.setPointNum(0);
				}
			}
			return list;
		}
		return reportMapper.selectLineSaleMonth(vo);
	}

	/**
	 * 查询线路日报表
	 * 
	 * @param vo 查询条件
	 * @return 线路每日详情
	 */
	@Override
	public List<Map<String, Object>> selectLineSaleDay(ReportVo vo) {
		return reportMapper.selectLineSaleDay(vo);
	}

	/**
	 * 查询点位日报表
	 * 
	 * @param vo 查询条件
	 * @return 点位每日详情
	 */
	@Override
	public List<Map<String, Object>> selectPointSaleDay(ReportVo vo) {
		return reportMapper.selectPointSaleDay(vo);
	}

	/**
	 * 查询点位月报表
	 * 
	 * @param vo 查询条件
	 * @return 点位每月详情
	 */
	@Override
	public List<PointSaleMoneyVo> selectPointSaleMonth(ReportVo vo) {
		Calendar calendar = Calendar.getInstance();
		int curMonth = calendar.get(Calendar.MONTH)+1;
		//如果是当前月份,查询日报表
		if(curMonth==vo.getSmonth()) {
			return reportMapper.selectPointSaleCurMonth(vo);
		}
		return reportMapper.selectPointSaleMonth(vo);
	}

	/**
	 * 查询点位报表(导出专用)
	 * 
	 * @param vo 查询条件
	 * @return 点位销售额列表
	 */
	@Override
	public List<PointSaleMoneyVo> selectPointSaleListForExport(ReportVo vo) {
		Calendar calendar = Calendar.getInstance();
		int curMonth = calendar.get(Calendar.MONTH)+1;
		//如果是当前月份,查询日报表
		if(curMonth==vo.getSmonth()) {
			return reportMapper.selectPointSaleCurMonthForExport(vo);
		}
		return reportMapper.selectPointSaleListForExport(vo);
	}

}
