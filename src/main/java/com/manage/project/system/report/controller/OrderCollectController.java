package com.manage.project.system.report.controller;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.manage.common.utils.StringUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.report.constant.ReportConstant;
import com.manage.project.system.report.service.IOrderCollectService;
import com.manage.project.system.report.util.ChartUtils;
import com.manage.project.system.report.vo.ChartVo;
import com.manage.project.system.report.vo.IncomeDetailVo;
import com.manage.project.system.report.vo.OrderCollectVo;
import com.manage.project.system.report.vo.OrderDetailVo;
import com.manage.project.system.report.vo.TotalVo;
import com.manage.project.system.util.SystemUtil;

/**
 * 订单汇总信息
 * 
 * @author zhangjiawei
 * @date 2018-10-09
 */
@Controller
@RequestMapping("/system/orderCollect")
public class OrderCollectController extends BaseController{

	@Autowired
	private IOrderCollectService orderCollectService;
	
	private Logger log = LoggerFactory.getLogger(OrderCollectController.class);
	/**
	 * 对传入参数处理
	 */
	@Deprecated
	public AjaxResult paramHandle(OrderCollectVo vo) {
		String reportType = vo.getReportType();
		if(StringUtils.isEmpty(reportType)) {
			vo.setReportType("1");
		}
		//获取日期
		try {
			String date=vo.getDate();
			String[] dateSplit = date.split("-");
			String day="";
			String month="";
			String year="";
			Calendar selectCal = Calendar.getInstance();
			Calendar lastCal = Calendar.getInstance();
			switch(reportType) {
				case "1":
					day=dateSplit[2];
					vo.setDay(Integer.parseInt(day)); 
					month=dateSplit[1];
					vo.setMonth(Integer.parseInt(month));
					year=dateSplit[0];
					vo.setYear(Integer.parseInt(year));
					//获取查询条件中的日期					
					selectCal.set(Calendar.YEAR, Integer.parseInt(year));
					selectCal.set(Calendar.MONTH, Integer.parseInt(month)-1);
					selectCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
					//获取最后能查询到的日期					
					lastCal.add(Calendar.DATE, -1);
					if(selectCal.after(lastCal)) {
						System.out.println("查询时间"+selectCal.getTime());
						System.out.println("最后时间"+lastCal.getTime());
						return AjaxResult.error("日期错误,日报表最多只能查询到前一天的记录");
					}
					break;
				case "2":
					month=dateSplit[1];
					vo.setMonth(Integer.parseInt(month));
					year=dateSplit[0];
					vo.setYear(Integer.parseInt(year));
					//获取查询条件中的日期
					selectCal.set(Calendar.YEAR, Integer.parseInt(year));
					selectCal.set(Calendar.MONTH, Integer.parseInt(month)-1);
					selectCal.set(Calendar.DAY_OF_MONTH, 1);
					//获取最后能查询到的日期
					lastCal.add(Calendar.DATE, -1);
					if(selectCal.after(lastCal)) {
						return AjaxResult.error("日期错误,月报表最多只能查询到前一天的记录");						
					}
					break;
				case "3":
				case "4":
					Integer quarter = vo.getQuarter();
					if(quarter==null||quarter==0) {
						return AjaxResult.error("请选择季度");
					}
				case "5":
					Integer halfYear = vo.getHalfYear();
					if(halfYear==null||halfYear==0) {
						return AjaxResult.error("请选择半年");
					}
					year=dateSplit[0];
					vo.setYear(Integer.parseInt(year));
					//获取查询条件中的日期
					selectCal.set(Calendar.YEAR, Integer.parseInt(year));
					selectCal.set(Calendar.MONTH, 0);
					selectCal.set(Calendar.DAY_OF_MONTH, 1);
					//获取最后能查询到的日期
					lastCal.add(Calendar.DATE, -1);
					if(selectCal.after(lastCal)) {
						return AjaxResult.error("日期错误,年报表最多只能查询到前一天的记录");
					}
					break;
				default:
					day=dateSplit[2];
					vo.setDay(Integer.parseInt(day)); 
					month=dateSplit[1];
					vo.setMonth(Integer.parseInt(month));
					year=dateSplit[0];
					vo.setYear(Integer.parseInt(year));  
					//获取查询条件中的日期					
					selectCal.set(Calendar.YEAR, Integer.parseInt(year));
					selectCal.set(Calendar.MONTH, Integer.parseInt(month));
					selectCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
					//获取最后能查询到的日期					
					lastCal.add(Calendar.DATE, -1);
					if(selectCal.after(lastCal)) {
						return AjaxResult.error("日期错误,日报表最多只能查询到前一天的记录");
					}
			}
		}catch (Exception e) {
			Calendar calendar = Calendar.getInstance();
			if("2".equals(reportType)) {
				calendar.add(Calendar.MONTH, -1);
			}else if("3".equals(reportType)) {
				calendar.add(Calendar.YEAR, -1);
			}else {
				calendar.add(Calendar.DATE, -1);
			}
			vo.setYear(calendar.get(Calendar.YEAR));
			vo.setMonth(calendar.get(Calendar.MONTH)+1);
			vo.setDay(calendar.get(Calendar.DATE));
		}
		//获取身份来源
		if (SystemUtil.isZhilai()) {
			vo.setCorpId("");
		} else {
			vo.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		return null;
	}
	
	/**
	 * 对传入参数处理
	 */
	public AjaxResult handelParam(OrderCollectVo vo) {
		String reportType = vo.getReportType();
		if(StringUtils.isEmpty(reportType)) {
			vo.setReportType("1");
		}
		Integer day=0;
		Integer month=0;
		Integer year=0;
		//获取日期
		try {
			String date=vo.getDate();
			LocalDate now = LocalDate.now();
			LocalDate lastDay = now.minusDays(1);
			//日报表
			if("1".equals(reportType)) {
				if(StringUtils.isNotEmpty(date)) {
					LocalDate selectDate = LocalDate.parse(date);
					if(selectDate.isAfter(lastDay)) {
						return AjaxResult.error("最多只能查询前一天的数据"); 
					}
					day=selectDate.getDayOfMonth();
					month=selectDate.getMonthValue();
					year=selectDate.getYear();
				}else {
					day=lastDay.getDayOfMonth();
					month=lastDay.getMonthValue();
					year=lastDay.getYear();
				}
			}else {
				//月报表
				if("2".equals(reportType)) {
					if(StringUtils.isNotEmpty(date)) {
						String[] dateSplit = date.split("-");
						year=Integer.parseInt(dateSplit[0]);
						month=Integer.parseInt(dateSplit[1]);
						LocalDate selectDate = LocalDate.of(year, month, 1);
						if(selectDate.isAfter(lastDay)) {
							return AjaxResult.error("当前时间:"+now.toString()+",最多只能查询"+lastDay.getYear()+"年"+lastDay.getMonthValue()+"月的数据"); 
						}						
					}else {
						day=lastDay.getDayOfMonth();
						month=lastDay.getMonthValue();
						year=lastDay.getYear();
					}
				}else if("3".equals(reportType)||"4".equals(reportType)||"5".equals(reportType)) {
					//年报表,季度报表和半年报表
					if(StringUtils.isNotEmpty(date)) {
						year=Integer.parseInt(date);
						LocalDate selectDate = LocalDate.of(year, 1, 1);
						if(selectDate.isAfter(lastDay)) {
							return AjaxResult.error("当前时间:"+now.toString()+",最多只能查询"+lastDay.getYear()+"年的数据"); 
						}
					}else {
						day=lastDay.getDayOfMonth();
						month=lastDay.getMonthValue();
						year=lastDay.getYear();
					}
					//季度报表,默认查询第一季度
					if("4".equals(reportType)) {
						if(vo.getQuarter()==null||vo.getQuarter()==0) {
							vo.setQuarter(1);
						}
					}
					//半年报表,默认查询上半年
					if("5".equals(reportType)) {
						if(vo.getHalfYear()==null||vo.getHalfYear()==0) {
							vo.setHalfYear(1);
						}
					}
				}else {
					return AjaxResult.error("无效的报表类型"); 
				}
			}
		}catch (Exception e) {
			log.error("订单汇总参数处理异常:",e);
			return AjaxResult.error("日期格式错误"); 
		}
		//获取身份来源
		vo.setCorpId(SystemUtil.getCorpId());
		vo.setYear(year);
		vo.setMonth(month);
		vo.setDay(day);
		return AjaxResult.success();
	}

	
	/**
	 * 累计销售数据
	 */
	@GetMapping("/totalReview")
	@ResponseBody
	public AjaxResult totalReviewByDay(OrderCollectVo vo) {
		AjaxResult ajaxResult = handelParam(vo);
		if(!ajaxResult.isSuccess()) {
			return ajaxResult;
		}
		String reportType=vo.getReportType();
		TotalVo totalVo = new TotalVo();		
		switch (reportType) {
		case "1":
			totalVo = orderCollectService.selectTotalByDay(vo);			
			break;
		case "2":
			totalVo = orderCollectService.selectTotalByMonth(vo);			
			break;
		case "3":
			totalVo = orderCollectService.selectTotalByYear(vo);			
			break;
		case "4":
			totalVo = orderCollectService.selectTotalByQuarter(vo);			
			break;
		case "5":
			totalVo = orderCollectService.selectTotalByHalfYear(vo);			
			break;
		default:
			totalVo = orderCollectService.selectTotalByDay(vo);			
			break;
		}
		if(totalVo!=null) {
			totalVo.setReportType(vo.getReportType());
			return AjaxResult.success(totalVo);
		}else {
			totalVo = new TotalVo();
			totalVo.setProfit(0D);
			totalVo.setSaleNum(0);
			totalVo.setSaleMoney(0D);
			totalVo.setReportType(vo.getReportType());
			return AjaxResult.success(totalVo);
		}
		
	}
	
	/**
	 * 营收分析
	 */
	@GetMapping("/incomeAnalyze")
	@ResponseBody
	public AjaxResult incomeAnalyzeByDay(OrderCollectVo vo) {
		AjaxResult ajaxResult = handelParam(vo);
		if(!ajaxResult.isSuccess()) {
			return ajaxResult;
		}
		String reportType=vo.getReportType();
		IncomeDetailVo incomeDetailVo = new IncomeDetailVo();
		List<TotalVo> list = new ArrayList<TotalVo>();
		switch (reportType) {
		case "1":
			list = orderCollectService.selectIncomeDetailByDay(vo);			
			break;
		case "2":
			list = orderCollectService.selectIncomeDetailByMonth(vo);			
			break;
		case "3":
			list = orderCollectService.selectIncomeDetailByYear(vo);			
			break;
		case "4":
			list = orderCollectService.selectIncomeDetailByQuarter(vo);			
			break;
		case "5":
			list = orderCollectService.selectIncomeDetailByHalfYear(vo);			
			break;
		default:
			list = orderCollectService.selectIncomeDetailByDay(vo);			
			break;
		}
		incomeDetailVo.setReportType(vo.getReportType());
		incomeDetailVo.setList(list);
		ChartVo chartData = ChartUtils.incomeDetailToChartData(incomeDetailVo,vo.getYear(),vo.getMonth(),vo.getDay());
		return AjaxResult.success(chartData);
	}
	
	
	
	/**
	 * 订单详情页面
	 */
	@GetMapping("/orderDetail")
	@ResponseBody
	public AjaxResult orderDetail(OrderCollectVo vo) {
		String reportType = vo.getReportType();
//		if(StringUtils.isEmpty(reportType)) {
//			vo.setReportType("1");
//		}
		//获取日期
		try {
			String date=vo.getDate();
			String[] dateSplit = date.split("-");
			String day="";
			String month="";
			String year="";
			Calendar selectCal = Calendar.getInstance();
			Calendar lastCal = Calendar.getInstance();
			switch(reportType) {
				case "1":
					day=dateSplit[2];
					vo.setDay(Integer.parseInt(day)); 
					month=dateSplit[1];
					vo.setMonth(Integer.parseInt(month));
					year=dateSplit[0];
					vo.setYear(Integer.parseInt(year));
					break;
				case "2":
					month=dateSplit[1];
					vo.setMonth(Integer.parseInt(month));
					year=dateSplit[0];
					vo.setYear(Integer.parseInt(year));
					break;
				case "3":
				case "4":
				case "5":
					year=dateSplit[0];
					vo.setYear(Integer.parseInt(year));
					break;
				default:
			}
		}catch (Exception e) {
			log.error("订单详情日期解析错误:",e);
		}
		//获取身份来源
		if (SystemUtil.isZhilai()) {
			vo.setCorpId("");
		} else {
			vo.setCorpId(ShiroUtils.getUser().getCorpId());
		}
//		AjaxResult ajaxResult = paramHandle(vo);
//		if(ajaxResult!=null) {
//			return ajaxResult;
//		}
//		String reportType=vo.getReportType();
//		List<OrderDetailVo> list=null;
		startPage();
		List<OrderDetailVo> list=orderCollectService.selectOrderDetail(vo);
//		switch (reportType) {
//		case "1":
//			list = orderCollectService.selectOrderDetailByDay(vo);			
//			break;
//		case "2":
//			list = orderCollectService.selectOrderDetailByMonth(vo);			
//			break;
//		case "3":
//			list = orderCollectService.selectOrderDetailByYear(vo);			
//			break;
//		default:
//			list = orderCollectService.selectOrderDetailByDay(vo);			
//			break;
//		}
		TableDataInfo dataTable = getDataTable(list);
		for (OrderDetailVo orderDetailVo : list) {
			orderDetailVo.setPointName(SystemUtil.getVendingPointNameFromCache(orderDetailVo.getPointId()));
			orderDetailVo.setPayStateName(SystemUtil.parsePayState(orderDetailVo.getPayState()));
			orderDetailVo.setPayTypeName(SystemUtil.parsePayType(orderDetailVo.getPayType()));
			orderDetailVo.setOutStateName(SystemUtil.parseBoxState(orderDetailVo.getOutState()));
			orderDetailVo.setReturnTypeName(SystemUtil.parseReturnType(orderDetailVo.getReturnType()));
		}
		return AjaxResult.success(dataTable);
	}
	
	/**
	 * 导出订单明细
	 */
	@Log(title = "订单明细", action = BusinessType.EXPORT)
	@PostMapping( "/exportExcel")
	@ResponseBody
	public AjaxResult exportExcel(@RequestBody OrderCollectVo vo) {	
		AjaxResult ajaxResult = paramHandle(vo);
		if(ajaxResult!=null) {
			return ajaxResult;
		}
		try {
			ExcelUtil<OrderDetailVo> util = new ExcelUtil<OrderDetailVo>(OrderDetailVo.class);
			String reportType=vo.getReportType();
			List<OrderDetailVo> list=null;
			switch (reportType) {
			case "1":
				list = orderCollectService.selectOrderDetailByDay(vo);			
				break;
			case "2":
				list = orderCollectService.selectOrderDetailByMonth(vo);			
				break;
			case "3":
				list = orderCollectService.selectOrderDetailByYear(vo);			
				break;
			default:
				list = orderCollectService.selectOrderDetailByDay(vo);			
				break;
			}
			if(list.size()>10000) {
				list = list.subList(0, 10000);
			}
			for (OrderDetailVo orderDetailVo : list) {
				orderDetailVo.setPointName(SystemUtil.getVendingPointNameFromCache(orderDetailVo.getPointId()));
				orderDetailVo.setPayStateName(SystemUtil.parsePayState(orderDetailVo.getPayState()));
				orderDetailVo.setPayTypeName(SystemUtil.parsePayType(orderDetailVo.getPayType()));
				orderDetailVo.setOutStateName(SystemUtil.parseBoxState(orderDetailVo.getOutState()));
				orderDetailVo.setReturnTypeName(SystemUtil.parseReturnType(orderDetailVo.getReturnType()));
			}
            return util.exportExcel(list, "OrderDetail");
			
		} catch (Exception e) {
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
}
