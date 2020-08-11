/**
 * 
 */
package com.manage.project.system.report.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.excel.ExportExcelUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.report.service.ISaleAnalyzeService;
import com.manage.project.system.report.util.ChartUtils;
import com.manage.project.system.report.vo.ChartVo;
import com.manage.project.system.report.vo.RankVo;
import com.manage.project.system.report.vo.SaleAnalyzeVo;
import com.manage.project.system.util.SystemUtil;

/**
 * 畅销分析
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
@Controller
@RequestMapping("/system/saleAnalyze")
public class SaleAnalyzeController extends BaseController{

	@Autowired
	private ISaleAnalyzeService saleAnalyzeService;
	
	private Logger log = LoggerFactory.getLogger(SaleAnalyzeController.class);
	
	/**
	 * 线路排名
	 */
	@GetMapping("/showLineRank")
	@ResponseBody
	@Deprecated
	public AjaxResult showLineRank(SaleAnalyzeVo vo) {
		vo.setSaleType("3");
		vo.setOrderByColumn("saleNum");
		paramHandler(vo);
		List<RankVo> list = saleAnalyzeService.selectLineTopTen(vo);
		ChartVo chartVo = ChartUtils.rankToChartData(list);
		chartVo.setType(2);
		return AjaxResult.success(chartVo);
	}
	
	/**
	 * 点位排名
	 */
	@GetMapping("/showPointRank")
	@ResponseBody
	@Deprecated
	public AjaxResult showPointRank(SaleAnalyzeVo vo) {
		vo.setSaleType("2");
		vo.setOrderByColumn("saleMoney");
		paramHandler(vo);
		List<RankVo> list = saleAnalyzeService.selectPointRank(vo);
/*		if(StringUtils.isEmpty(list)) {
			return AjaxResult.error("查询不到数据");
		}*/
		return AjaxResult.success(list);
	}
	
	/**
	 * 商品排名
	 */
	@GetMapping("/showProductRank")
	@ResponseBody
	@Deprecated
	public AjaxResult showProductRank(SaleAnalyzeVo vo) {
		vo.setSaleType("1");
		vo.setOrderByColumn("saleNum");
		paramHandler(vo);
		List<Map<String,Object>> list = saleAnalyzeService.selectProductTopTen(vo);
		return AjaxResult.success(list);
	}
	
	/**
	 * 线路排名
	 */
	@GetMapping("/lineRank")
	@ResponseBody
	public AjaxResult lineRank(SaleAnalyzeVo vo) {
		List<RankVo> list = saleAnalyzeService.selectLineRank(vo);
		ChartVo chartVo = ChartUtils.rankToChartData(list);
		chartVo.setType(2);
		return AjaxResult.success(chartVo);
	}
	
	/**
	 * 点位排名
	 */
	@GetMapping("/pointRank")
	@ResponseBody
	public AjaxResult pointRank(SaleAnalyzeVo vo) {
		List<RankVo> list = saleAnalyzeService.selectPointRank(vo);
		return AjaxResult.success(list);
	}
	
	/**
	 * 商品排名
	 */
	@GetMapping("/productRank")
	@ResponseBody
	public AjaxResult productRank(SaleAnalyzeVo vo) {
		List<RankVo> list = saleAnalyzeService.selectProductRank(vo);
		List<Map<String,String>> arrayList = new ArrayList<Map<String,String>>();
		DecimalFormat df = new DecimalFormat("#.00");
		double curPercent=0.0;
		if(!StringUtils.isEmpty(list)) {
			//保存饼图数据格式
			for (RankVo rankVo : list) {
				if(rankVo!=null) {
					Map<String, String> map = new HashMap<String,String>();
					map.put("name", rankVo.getName());
					map.put("value", df.format(rankVo.getPercent()));
					curPercent+=rankVo.getPercent();
					arrayList.add(map);
				}	
			}
			if(Math.abs(curPercent - 1)>=0.01) {
				Map<String, String> map = new HashMap<String,String>();
				map.put("name", "其他");
				map.put("value", df.format(1-curPercent));
				arrayList.add(map);
			}
		}
		return AjaxResult.success(arrayList);
	}
	
	private void paramHandler(SaleAnalyzeVo vo) {
		vo.setCorpId(SystemUtil.getCorpId());
		LocalDate now = LocalDate.now();
		LocalDate lastDay = now.minusDays(1);
		String dateType = vo.getDateType();
		if(StringUtils.isEmpty(dateType)) {
			vo.setDateType("0");
		}
		switch (vo.getDateType()) {
		//当天排名,获取当天日期
		case "0":
			vo.setYear(now.getYear());
			vo.setMonth(now.getMonthValue());
			vo.setDay(now.getDayOfMonth());
			break;
		//月度季度年度排名,获取前一天记录
		case "1":
		case "2":
		case "3":
			vo.setYear(lastDay.getYear());
			vo.setMonth(lastDay.getMonthValue());
			vo.setDay(lastDay.getDayOfMonth());
			break;
		default:
			vo.setYear(now.getYear());
			vo.setMonth(now.getMonthValue());
			vo.setDay(now.getDayOfMonth());
			break;
		}
	}
	
	/**
	 * 线路排名导出
	 */
	@PostMapping("/lineRankExport")
	@ResponseBody
	public AjaxResult lineRankExport(SaleAnalyzeVo vo) {
		try {
			ExcelUtil<RankVo> util = new ExcelUtil<RankVo>(RankVo.class);
			List<RankVo> list = saleAnalyzeService.selectLineRank(vo);
			AjaxResult result = util.exportExcel(list, "线路排名");
			return AjaxResult.success(result);
		}catch (Exception e) {
			log.error("导出线路排名出错:",e);
			return AjaxResult.error("导出线路排名出错,请联系管理员");
		}
	}
	
	/**
	 * 点位排名导出
	 */
	@PostMapping("/pointRankExport")
	@ResponseBody
	public AjaxResult pointRankExport(SaleAnalyzeVo vo) {
		try {
			ExcelUtil<RankVo> util = new ExcelUtil<RankVo>(RankVo.class);
			List<RankVo> list = saleAnalyzeService.selectPointRank(vo);
			AjaxResult result = util.exportExcel(list, "点位排名");
			return AjaxResult.success(result);
		}catch (Exception e) {
			log.error("导出点位排名出错:",e);
			return AjaxResult.error("导出线路排名出错,请联系管理员");
		}
	}
	
	/**
	 * 商品排名导出
	 */
	@PostMapping("/productRankExport")
	@ResponseBody
	public AjaxResult productRankExport(SaleAnalyzeVo vo) {
		try {
			ExcelUtil<RankVo> util = new ExcelUtil<RankVo>(RankVo.class);
			List<RankVo> list = saleAnalyzeService.selectProductRank(vo);
			AjaxResult result = util.exportExcel(list, "商品排名");
			return AjaxResult.success(result);
		}catch (Exception e) {
			log.error("导出商品位排名出错:",e);
			return AjaxResult.error("导出商品排名出错,请联系管理员");
		}
	}
}
