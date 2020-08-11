package com.manage.project.system.report.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manage.common.utils.DateUtils;

/**
 * 报表工具类
 * 
 * @author zhangjiawei
 *
 */
public class ReportUtils {

	/**
	 * 报表没有数据的填充0
	 * 
	 * @param year 年
	 * @param month 月
	 * @param details 查询到的数据
	 * return 填充后的数据
	 */
	public static List<Map<String,Object>> fullWithZero(int year,int month,List<Map<String,Object>> details){
		int days = DateUtils.getDaysOfMonth(month, year);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> detail = new HashMap<String,Object>();
		//将list转为map,日期为key
		for (Map<String, Object> detailMap : details) {
			detail.put((String)detailMap.get("date"),detailMap.get("saleMoney"));
		}
		//遍历当月天数，空白记录填充0
		for (int i = 1; i <= days; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String date=year+"-"+month+"-"+i;
			map.put("date",date);
			if(detail.get(date)!=null){
				map.put("saleMoney",detail.get(date));
			}else{
				map.put("saleMoney",0.00);
			}
			list.add(map);
		}
		return list;
	}
}
