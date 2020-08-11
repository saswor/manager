/**
 * 
 */
package com.manage.project.system.report.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.manage.common.utils.StringUtils;
import com.manage.project.system.report.vo.ChartVo;
import com.manage.project.system.report.vo.IncomeDetailVo;
import com.manage.project.system.report.vo.RankVo;
import com.manage.project.system.report.vo.TotalVo;

/**
 * 图表工具类
 * 
 * @author zhangjiawei
 * @date 2018年10月16日
 * 
 */
public class ChartUtils {

	/**
	 * 将营销分析数据转换图表vo
	 * 
	 * @param vo营销分析vo
	 * @return 图表vo
	 */
	/*public static ChartVo incomeDetailToChartData(IncomeDetailVo vo) {
		ChartVo chartVo = new ChartVo();
		if(vo==null) {
			return null;
		}
		if(vo.getReportType()==3) {
			chartVo.setType(2);
		}else {
			chartVo.setType(1);
		}
		List<TotalVo> list = vo.getList();
		List<Double> saleMoneys = chartVo.getSaleMoney();
		List<Integer> saleNums = chartVo.getSaleNum();
		List<Double> profits = chartVo.getProfit();
		List<String> coordinate = chartVo.getCoordinate();
		for (TotalVo totalVo : list) {
			if(totalVo!=null) {
				
				coordinate.add(totalVo.getTime());
				saleMoneys.add(totalVo.getSaleMoney());
				saleNums.add(totalVo.getSaleNum());
				profits.add(totalVo.getProfit());
			}
		}
		return chartVo;
	}*/
	
	/**
	 * 将营销分析数据转换图表vo
	 * 
	 * @param vo营销分析vo
	 * @return 图表vo
	 */
	/*public static ChartVo incomeDetailToChartData(IncomeDetailVo vo,int year,int month,int day) {
		ChartVo chartVo = new ChartVo();
		if(vo==null) {
			return null;
		}
		List<TotalVo> list = vo.getList();
		List<Double> saleMoneys = chartVo.getSaleMoney();
		List<Integer> saleNums = chartVo.getSaleNum();
		List<Double> profits = chartVo.getProfit();
		List<String> coordinate = chartVo.getCoordinate();
		Calendar calendar = Calendar.getInstance();
		if(year!=0) {
			calendar.set(Calendar.YEAR, year);
		}
		if(month!=0) {
			calendar.set(Calendar.MONTH, month-1);
		}
		if(day!=0) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
		}
		//根据报表类型对坐标横轴赋值
		switch (vo.getReportType()) {
		case 1:
			for (int i = 1; i <= 24; i++) {
				coordinate.add(i+":00");
			}
			chartVo.setType(1);
			break;
		case 2:
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH,0);
			for (int i = 1; i <= calendar.get(Calendar.DAY_OF_MONTH); i++) {
				coordinate.add(String.valueOf(i));
			}
			chartVo.setType(1);
			break;
		case 3:
			for (int i = 1; i <= 12; i++) {
				coordinate.add(calendar.get(Calendar.YEAR)+"-"+i);
			}
			chartVo.setType(2);
			break;
		default:
			for (int i = 1; i <= 24; i++) {
				coordinate.add(i+":00");
			}
			chartVo.setType(1);
			break;
		}
		//将结果转为map,以横坐标为key
		Map<String, TotalVo> map = new HashMap<String,TotalVo>();
		for (TotalVo totalVo : list) {
			if(totalVo!=null) {
				String key=totalVo.getTime();
				if(vo.getReportType()==1) {
					key = key.substring(11, 16);
				}
				map.put(key, totalVo);
			}
		}
		//根据横坐标从map中取出结果
		for (int i = 0; i < coordinate.size(); i++) {
			TotalVo totalVo = map.get(coordinate.get(i));
			if(totalVo!=null) {
				saleMoneys.add(totalVo.getSaleMoney());
				saleNums.add(totalVo.getSaleNum());
				profits.add(totalVo.getProfit());
			}else {
				saleMoneys.add(0.0);
				saleNums.add(0);
				profits.add(0.0);
			}
		}
		return chartVo;
	}*/
	
	/**
	 * 将营销分析数据转换图表vo
	 * 
	 * @param vo营销分析vo
	 * @return 图表vo
	 */
	public static ChartVo incomeDetailToChartData(IncomeDetailVo vo,int year,int month,int day) {
		ChartVo chartVo = new ChartVo();
		if(vo==null) {
			return null;
		}		
		List<Double> saleMoneys = chartVo.getSaleMoney();
		List<Integer> saleNums = chartVo.getSaleNum();
		List<Double> profits = chartVo.getProfit();
		List<String> coordinate = chartVo.getCoordinate();
		List<TotalVo> list = vo.getList();
		for (TotalVo totalVo : list) {
			saleMoneys.add(totalVo.getSaleMoney());
			saleNums.add(totalVo.getSaleNum());
			profits.add(totalVo.getProfit());
			coordinate.add(totalVo.getTime().trim());
		}
		return chartVo;
	}
		
	/**
	 * 将畅销分析数据转换图表vo
	 * 
	 * @param vo营销分析vo
	 * @return 图表vo
	 */
	public static ChartVo rankToChartData(List<RankVo> list) {
		ChartVo chartVo = new ChartVo();		
		//List<Double> saleMoneys = chartVo.getSaleMoney();
		List<Integer> saleNums = chartVo.getSaleNum();
		//List<Double> profits = chartVo.getProfit();
		List<String> coordinate = chartVo.getCoordinate();
		for (RankVo vo : list) {
			if(vo!=null) {
				coordinate.add(vo.getName());
				//saleMoneys.add(vo.getSaleMoney());
				saleNums.add(vo.getSaleNum());
				//profits.add(vo.getProfit());
			}
		}
		return chartVo;
	}
}
