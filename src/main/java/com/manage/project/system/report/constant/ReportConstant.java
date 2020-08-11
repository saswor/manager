package com.manage.project.system.report.constant;

import java.util.HashMap;
import java.util.Map;

public class ReportConstant {
	
	//请求的方法 1:查询总额 2:查询时间段销售额
	public static final int Mehtod_Total = 1;
	public static final int Mehtod_Time = 2;
	
	//营收分析报表类型 1:日报表 2:月报表 3:年报表
	public static final int REPORT_TYPE_1 = 1;
	public static final int REPORT_TYPE_2 = 2;
	public static final int REPORT_TYPE_3 = 3;
	
	//营收分析报表查询维度 1:线路 2:点位 3:商品
	public static final int REPORT_KIND_1 = 1;
	public static final int REPORT_KIND_2 = 2;
	public static final int REPORT_KIND_3 = 3;
	
	public static final Map<String,String> REPORT_MAP = new HashMap<String,String>();
	
	static {
		//总计销售情况处理方式
		//线路日报表
		REPORT_MAP.put(Mehtod_Total+"_"+REPORT_TYPE_1+"_"+REPORT_KIND_1, "selectLineTotalViewByDay");
		//点位日报表
		REPORT_MAP.put(Mehtod_Total+"_"+REPORT_TYPE_1+"_"+REPORT_KIND_2, "selectPointTotalViewByDay");
		//商品日报表
		REPORT_MAP.put(Mehtod_Total+"_"+REPORT_TYPE_1+"_"+REPORT_KIND_3, "selectProductTotalViewByDay");
		
		//线路月报表
		REPORT_MAP.put(Mehtod_Total+"_"+REPORT_TYPE_2+"_"+REPORT_KIND_1, "selectLineTotalViewByMonth");
		//点位月报表
		REPORT_MAP.put(Mehtod_Total+"_"+REPORT_TYPE_2+"_"+REPORT_KIND_2, "selectPointTotalViewByMonth");
		//商品月报表
		REPORT_MAP.put(Mehtod_Total+"_"+REPORT_TYPE_2+"_"+REPORT_KIND_3, "selectProductTotalViewByMonth");

		//线路年报表
		REPORT_MAP.put(Mehtod_Total+"_"+REPORT_TYPE_3+"_"+REPORT_KIND_1, "selectLineTotalViewByYear");
		//点位年报表
		REPORT_MAP.put(Mehtod_Total+"_"+REPORT_TYPE_3+"_"+REPORT_KIND_2, "selectPointTotalViewByYear");
		//商品年报表
		REPORT_MAP.put(Mehtod_Total+"_"+REPORT_TYPE_3+"_"+REPORT_KIND_3, "selectProductTotalViewByYear");
		
		//分时间段销售情况处理方式
		//线路日报表
		REPORT_MAP.put(Mehtod_Time+"_"+REPORT_TYPE_1+"_"+REPORT_KIND_1, "selectVendingDetailByDay");
		//点位日报表
		REPORT_MAP.put(Mehtod_Time+"_"+REPORT_TYPE_1+"_"+REPORT_KIND_2, "selectVendingDetailByDay");
		//商品日报表
		REPORT_MAP.put(Mehtod_Time+"_"+REPORT_TYPE_1+"_"+REPORT_KIND_3, "selectProductDetailByDay");
		
		//线路月报表
		REPORT_MAP.put(Mehtod_Time+"_"+REPORT_TYPE_2+"_"+REPORT_KIND_1, "selectVendingDetailByMonth");
		//点位月报表
		REPORT_MAP.put(Mehtod_Time+"_"+REPORT_TYPE_2+"_"+REPORT_KIND_2, "selectVendingDetailByMonth");
		//商品月报表
		REPORT_MAP.put(Mehtod_Time+"_"+REPORT_TYPE_2+"_"+REPORT_KIND_3, "selectProductDetailByMonth");
		
		//线路年报表
		REPORT_MAP.put(Mehtod_Time+"_"+REPORT_TYPE_3+"_"+REPORT_KIND_1, "selectVendingDetailByYear");
		//点位年报表
		REPORT_MAP.put(Mehtod_Time+"_"+REPORT_TYPE_3+"_"+REPORT_KIND_2, "selectVendingDetailByYear");
		//商品年报表
		REPORT_MAP.put(Mehtod_Time+"_"+REPORT_TYPE_3+"_"+REPORT_KIND_3, "selectProductDetailByYear");
				
	}
}
