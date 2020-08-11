package com.manage.project.system.base.mapper;

import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public interface SystemMapper {
	/**
	 * 生成id  
	 * @param paramMap	corpId, 数据库表名tableName
	 */
	void getGenderById(Map<String, Object> paramMap);

	void getCallGenOrderBase(Map<String, Object> paramMap);
	public  void getCallPointDay(Map<String,Object> map);
	public  void getCallPointMonth(Map<String,Object> map) ;
	public  void getPointYear(Map<String,Object> map) ;
	public  void getCallLineDay(Map<String,Object> map) ;
	public  void getCallLineMonth(Map<String,Object> map) ;
	public  void getCallPointYear(Map<String,Object> map) ;
	public  void getCallLineYear(Map<String,Object> map) ;
	public  void getCallPtimeDay(Map<String,Object> map) ;
	public  void getCallPtimeMonth(Map<String,Object> map) ;
	public  void getCallPtimeWeek(Map<String,Object> map);
	public  void getCallVtimeDay(Map<String,Object> map) ;
	public  void getCallVtimeMonth(Map<String,Object> map) ;
	public  void getCallVtimeWeek(Map<String,Object> map) ;
	public  void getCallReportBoard(Map<String,Object> map) ;
	public  void getCallReportDsale(Map<String,Object> map) ;
	public  void getCallReportMsale(Map<String,Object> map) ;
	public  void getCallReportOsale(Map<String,Object> map);

	void getCallOsaleDay(Map<String, Object> map);

	void getCallOsaleDayHistory(Map<String, Object> map);

	void getCallOsaleOther(Map<String, Object> map);

	void getCallOsaleOtherHistory(Map<String, Object> map);

	void getCallVtimeQuarter(Map<String, Object> map);
	void getCallVtimeHalfYear(Map<String, Object> map);
	void getCallVtimeYear(Map<String, Object> map);
}
