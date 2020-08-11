package com.manage.project.system.index.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.manage.project.system.index.domain.ReportDsale;
import com.manage.project.system.index.domain.ReportMsale;
import com.manage.project.system.index.domain.ReportOsale;
import com.manage.project.system.index.vo.TieVo;
import com.manage.project.system.index.vo.column.SaleNumVo;
import com.manage.project.system.index.vo.column.SaleVo;
import com.manage.project.system.index.vo.column.YsColChartVo;
import com.manage.project.system.index.vo.column.YsColumnChartVo;
import com.manage.project.system.index.vo.line.TodaySummaryVo;

public class IndexUtil {
	/**
	 * 组装营收分析查询 柱状图
	 * @param list
	 * @return
	 */
	public static YsColChartVo assembleColumn(List<ReportMsale> list) {
		YsColChartVo ysColChartVo = new YsColChartVo();
		List<String> months = new ArrayList<String>();	// 月份
		List data = new ArrayList();	// 包括销售额,销售量,净利润
		
		List<Float> salesList = new ArrayList<Float>();	// 销售额
		List<Integer> salesNumList = new ArrayList<Integer>();	// 销售量
		List<Float> profitList = new ArrayList<Float>();	// 净利润
		for (ReportMsale m : list) {
			months.add(m.getSyear()+"-"+m.getSmonth());
			salesList.add(m.getSaleMoney());
			salesNumList.add(m.getSaleNum());
			profitList.add(m.getProfit());
		}
		ysColChartVo.setMonths(months);
		ysColChartVo.setProfitList(profitList);
		ysColChartVo.setSalesList(salesList);
		ysColChartVo.setSalesNumList(salesNumList);
		return ysColChartVo;
		
//		SaleVo sale = new SaleVo();
//		sale.setName("销售额");
//		sale.setType("bar");
//		sale.setData(salesList);
//		
//		SaleVo profit = new SaleVo();
//		profit.setName("净利润");
//		profit.setType("line");
//		profit.setData(profitList);
//		
//		SaleNumVo saleNum = new SaleNumVo();
//		saleNum.setName("销售量");
//		saleNum.setType("bar");
//		saleNum.setData(salesNumList);
//		
//		data.add(sale);
//		data.add(saleNum);
//		data.add(profit);
//		
//		ysColumnChartVo.setMonths(months);
//		ysColumnChartVo.setData(data);
//		
//		return ysColumnChartVo;
	}
	
	/**
	 * 组装今日销售汇总查询 线状图
	 * @param list
	 * @return
	 */
	public static TodaySummaryVo assembleLine(List<ReportDsale> list) {
		TodaySummaryVo todaySummaryVo = new TodaySummaryVo();
		List<String> times = new ArrayList<String>();	// 时间
		
		List<Float> salesList = new ArrayList<Float>();	// 销售额
		List<Integer> salesNumList = new ArrayList<Integer>();	// 销售量
		for (ReportDsale m : list) {
			times.add(m.getEtime());
			salesList.add(m.getSaleMoney());
			salesNumList.add(m.getSaleNum());
		}
		todaySummaryVo.setTimes(times);
		todaySummaryVo.setSales(salesList);
		todaySummaryVo.setSalesNum(salesNumList);
	
		return todaySummaryVo;
	}
	
	
	/**
	 * 组装今日销售商品top10 饼状图
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> assembleTie(List<ReportOsale> roList) {
//		TieVo tv = new TieVo();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (ReportOsale ro : roList) {
//			List r = new ArrayList();
//			r.add(ro.getSaleName());
//			r.add(ro.getSaleNum());
//			list.add(r);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", ro.getSaleName());
			map.put("value", ro.getSaleNum());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 将空白的月份填充0
	 * 
	 * @param list 源列表
	 * @param months 月份
	 * @return
	 */
	public static List<ReportMsale> fillingZero(List<ReportMsale> list,List<String> months){
		//将结果转为map,以时间为key
		HashMap<String, ReportMsale> map = new HashMap<String,ReportMsale>();	
		for (ReportMsale reportMsale : list) {
			StringBuilder sb = new StringBuilder();
			String key=sb.append(reportMsale.getSyear()).append("-").append(reportMsale.getSmonth()).toString();
			map.put(key, reportMsale);
		}
		//重新封装结果,没有的月份设置为0
		List<ReportMsale> resultList = new ArrayList<ReportMsale>();
		for (int i=0;i<months.size() ;i++) {
			ReportMsale reportMsale = map.get(months.get(i));
			if(reportMsale!=null) {
				resultList.add(reportMsale);
			}else {
				reportMsale = new ReportMsale();
				String[] splitDate = months.get(i).split("-");
				reportMsale.setSyear(Integer.parseInt(splitDate[0]));
				reportMsale.setSmonth(Integer.parseInt(splitDate[1]));
				reportMsale.setSaleMoney(0F);
				reportMsale.setSaleNum(0);
				reportMsale.setProfit(0F);
				resultList.add(reportMsale);
			}
		}
		return resultList;
	}
	
	public static void main(String[] args) {
		List<ReportOsale> roList = new ArrayList<ReportOsale>();
		ReportOsale ro = new ReportOsale();
		ro.setSaleName("可乐");
		ro.setSaleNum(22);
		roList.add(ro);
		
//		List<List> list = new ArrayList<List>();
//		for (ReportOsale ro1 : roList) {
//			List r = new ArrayList();
//			r.add(ro1.getSaleName());
//			r.add(ro1.getSaleNum());
//			list.add(r);
////			Map<String, Integer> map = new HashMap<String, Integer>();
////			map.put(ro.getSaleName(), ro.getSaleNum());
////			list.add(map);
//		}
		System.out.println(JSON.toJSONString(assembleTie(roList)));;
	}
}
