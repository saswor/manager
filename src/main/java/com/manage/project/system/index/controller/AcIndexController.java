package com.manage.project.system.index.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.index.service.IndexService;
import com.manage.project.system.index.util.IndexUtil;
import com.manage.project.system.index.domain.ReportMsale;
import com.manage.project.system.index.vo.column.YsColChartVo;
import com.manage.project.system.index.vo.reqparam.AnalysisSelectVo;
import com.manage.project.system.util.SystemUtil;

@Controller
@RequestMapping("/system/index")
public class AcIndexController extends BaseController {
	@Autowired
	private IndexService indexService;
	
	/**
	 * 首页营收分析查询
	 * @return
	 * @throws ParseException 
	 */
	@GetMapping("/selectMSaleSummry")
    @ResponseBody
	public AjaxResult selectMSaleSummry(String bdate, String edate, int type) throws ParseException {
		List<String> months = new ArrayList<String>();
		if (type == 0) {
			if (StringUtils.isEmpty(edate)) {
				edate = DateUtils.sdf.format(new Date());
			}
			months = DateUtils.getListMonths(bdate, edate);
		} else if (type == 1)  {	// 近半年
			months = DateUtils.getListMonths(6);
		} else if (type == 2)  {	// 近1年
			months = DateUtils.getListMonths(12);
		}
		String corpId = "";
		if (!SystemUtil.isZhilai()) {
			corpId = ShiroUtils.getUser().getCorpId();
		}
		List<ReportMsale> list = indexService.selectReportMsaleByDate(corpId, months);
		//将没有数据的月份填充0
		List<ReportMsale> resultList = IndexUtil.fillingZero(list, months);
		YsColChartVo yvo = IndexUtil.assembleColumn(resultList);
		return AjaxResult.success(yvo);
	}
	
//	/**
//	 * 站点地图
//	 * @param type 0请求国家 1请求省2请求市3请求区
//	 * @return
//	 */
//	@GetMapping("/findAreaByPoint")
//    @ResponseBody
//	public AjaxResult findAreaByPoint(String type, String id) {
//		List<Dispatch> list = indexService.findAreaByPoing(type, id);
//		return AjaxResult.success(list);
//	}
}
