/**
 * 
 */
package com.manage.project.system.report.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.project.system.report.service.IReportService;
import com.manage.project.system.report.util.ReportExcelUtils;
import com.manage.project.system.report.util.ReportUtils;
import com.manage.project.system.report.vo.ReportVo;
import com.manage.project.system.report.vo.LineSaleMoneyVo;
import com.manage.project.system.report.vo.PointSaleMoneyVo;
import com.manage.project.system.util.SystemUtil;

/**
 * 线路销售额报表
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
@Controller
@RequestMapping("/system/report")
public class ReportController extends BaseController{
	
	private Logger log=LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	private IReportService reportService;
	
	/**
	 * 对传入参数处理
	 */
	private AjaxResult paramHandle(ReportVo vo) {
		//将时间转换Date类型
		try {
			String date = vo.getDate();
			String[] dateArr = date.split("-");
			vo.setSyear(Integer.parseInt(dateArr[0]));
			vo.setSmonth(Integer.parseInt(dateArr[1]));
			//获取查询条件中的日期
			Calendar selectCal = Calendar.getInstance();
			selectCal.set(Calendar.YEAR, Integer.parseInt(dateArr[0]));
			selectCal.set(Calendar.MONTH, Integer.parseInt(dateArr[1])-1);
			selectCal.set(Calendar.DAY_OF_MONTH, 1);
			//获取最后能查询到的日期
			Calendar lastCal = Calendar.getInstance();
			lastCal.add(Calendar.DATE, -1);
			if(selectCal.after(lastCal)) {
				return AjaxResult.error("日期错误");						
			}
		}catch (Exception e) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			vo.setSyear(calendar.get(Calendar.YEAR));
			vo.setSmonth(calendar.get(Calendar.MONTH)+1);
		}
		//判断操作者身份
		if (SystemUtil.isZhilai()) {
			vo.setCorpId("");
		} else {
			vo.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		return null;
	}
	
	/**
	 * 查询线路销售额报表
	 */
	@GetMapping("/lineSaleList")
	@ResponseBody
	public AjaxResult lineSaleList(ReportVo vo) {
		AjaxResult ajaxResult = paramHandle(vo);
		if(ajaxResult!=null) {
			return ajaxResult;
		}
		startPage();
		List<LineSaleMoneyVo> list = reportService.selectLineSaleMonth(vo);
		TableDataInfo dataTable = getDataTable(list);
		for (LineSaleMoneyVo lineSaleMoneyVo : list) {
			String lineId = lineSaleMoneyVo.getLineId();
			vo.setLineId(lineId);
			vo.setLineName(null);
			List<Map<String,Object>> details = reportService.selectLineSaleDay(vo);		
			List<Map<String, Object>> map = ReportUtils.fullWithZero(vo.getSyear(), vo.getSmonth(), details);
			lineSaleMoneyVo.setDetails(map);
		}
		return AjaxResult.success(dataTable);
	}
	
	/**
	 * 查询点位销售额报表
	 */
	@GetMapping("/pointSaleList")
	@ResponseBody
	public AjaxResult pointSaleList(ReportVo vo) {
		AjaxResult ajaxResult = paramHandle(vo);
		if(ajaxResult!=null) {
			return ajaxResult;
		}
		startPage();
		List<PointSaleMoneyVo> list = reportService.selectPointSaleMonth(vo);
		TableDataInfo dataTable = getDataTable(list);
		for (PointSaleMoneyVo pointSaleMoneyVo : list) {
			String pointId = pointSaleMoneyVo.getPointId();
			vo.setPointId(pointId);
			vo.setPointName(null);
			List<Map<String,Object>> details = reportService.selectPointSaleDay(vo);
			List<Map<String, Object>> map = ReportUtils.fullWithZero(vo.getSyear(), vo.getSmonth(), details);
			pointSaleMoneyVo.setDetails(map);
		}
		return AjaxResult.success(dataTable);
	}
	
	/**
	 * 查询线路销售额报表
	 * @throws IOException 
	 */
	@PostMapping("/lineReportExport")
	@Log(title="导出线路销售额报表",action=BusinessType.EXPORT)
	@ResponseBody
	public AjaxResult lineReportExport(@RequestBody ReportVo vo,HttpServletResponse response) throws IOException {		
		AjaxResult ajaxResult = paramHandle(vo);
		if(ajaxResult!=null) {
			return ajaxResult;
		}
		FileInputStream fis = null;
		try {
			List<LineSaleMoneyVo> list = reportService.selectLineSaleMonth(vo);
			for (LineSaleMoneyVo lineSaleMoneyVo : list) {
				String lineId = lineSaleMoneyVo.getLineId();
				vo.setLineId(lineId);
				vo.setLineName(null);
				List<Map<String,Object>> details = reportService.selectLineSaleDay(vo);		
				List<Map<String, Object>> map = ReportUtils.fullWithZero(vo.getSyear(), vo.getSmonth(), details);
				lineSaleMoneyVo.setDetails(map);
			}
			String filePath = ReportExcelUtils.lineReportToExcel(list);	

			return AjaxResult.success(filePath);
		} catch (Exception e) {
			log.error("导出线路报表失败,时间"+DateUtils.getTime(),e);
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
//	public AjaxResult lineReportExport(@RequestBody ReportVo vo,HttpServletResponse response) throws IOException {		
//		AjaxResult ajaxResult = paramHandle(vo);
//		if(ajaxResult!=null) {
//			return ajaxResult;
//		}
//		FileInputStream fis = null;
//		try {
//			List<LineSaleMoneyVo> list = reportService.selectLineSaleListForExport(vo);
//			for (LineSaleMoneyVo lineSaleMoneyVo : list) {
//				List<Map<String, Object>> details = lineSaleMoneyVo.getDetails();
//				List<Map<String, Object>> map = ReportUtils.fullWithZero(vo.getSyear(), vo.getSmonth(), details);
//				lineSaleMoneyVo.setDetails(map);
//			}
//			String filePath = ReportExcelUtils.lineReportToExcel(list);	
//			/*fis= new FileInputStream(filePath);
//			response.setHeader("Content-Disposition", "attachment; filename=线路报表.xls");
//			IOUtils.copy(fis, response.getOutputStream());
//			fis.close();*/
//			return AjaxResult.success(filePath);
//		} catch (Exception e) {
//			/*if(fis!=null) {
//				fis.close();
//			}*/
//			return error("导出Excel失败，请联系网站管理员！");
//		}
//	}
	
	/**
	 * 查询线路销售额报表
	 * @throws IOException 
	 */
	@PostMapping("/pointReportExport")
	@Log(title="导出点位销售额报表",action=BusinessType.EXPORT)
	@ResponseBody
	public AjaxResult pointReportExport(@RequestBody ReportVo vo,HttpServletResponse response) throws IOException {		
		AjaxResult ajaxResult = paramHandle(vo);
		if(ajaxResult!=null) {
			return ajaxResult;
		}
		FileInputStream fis = null;
		try {
			List<PointSaleMoneyVo> list = reportService.selectPointSaleMonth(vo);
			for (PointSaleMoneyVo pointSaleMoneyVo : list) {
				String pointId = pointSaleMoneyVo.getPointId();
				vo.setPointId(pointId);
				vo.setPointName(null);
				List<Map<String,Object>> details = reportService.selectPointSaleDay(vo);
				List<Map<String, Object>> map = ReportUtils.fullWithZero(vo.getSyear(), vo.getSmonth(), details);
				pointSaleMoneyVo.setDetails(map);
			}
			String filePath = ReportExcelUtils.pointReportToExcel(list);	
			return AjaxResult.success(filePath);
		} catch (Exception e) {
			log.error("导出点位报表失败,时间"+DateUtils.getTime(),e);
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
//	public AjaxResult pointReportExport(@RequestBody ReportVo vo,HttpServletResponse response) throws IOException {		
//		AjaxResult ajaxResult = paramHandle(vo);
//		if(ajaxResult!=null) {
//			return ajaxResult;
//		}
//		FileInputStream fis = null;
//		try {
//			List<PointSaleMoneyVo> list = reportService.selectPointSaleListForExport(vo);			
//			for (PointSaleMoneyVo pointSaleMoneyVo : list) {
//				List<Map<String, Object>> details = pointSaleMoneyVo.getDetails();
//				List<Map<String, Object>> map = ReportUtils.fullWithZero(vo.getSyear(), vo.getSmonth(), details);
//				pointSaleMoneyVo.setDetails(map);
//			}
//			String filePath = ReportExcelUtils.pointReportToExcel(list);	
//			/*fis= new FileInputStream(filePath);
//			response.setHeader("Content-Disposition", "attachment; filename=点位报表.xls");
//			IOUtils.copy(fis, response.getOutputStream());
//			fis.close();*/
//			return AjaxResult.success(filePath);
//		} catch (Exception e) {
//			/*if(fis!=null) {
//				fis.close();
//			}*/
//			return error("导出Excel失败，请联系网站管理员！");
//		}
//	}
}
