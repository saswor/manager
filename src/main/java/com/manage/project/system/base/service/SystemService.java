package com.manage.project.system.base.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.manage.common.utils.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.project.system.base.mapper.SystemMapper;

@Service
public class SystemService {
	@Autowired
	private SystemMapper systemMapper;
	
	/**
	 * 生成id
	 * @param corpId
	 * @param tableName
	 * @return
	 */
	public String getGenderById(String corpId, String tableName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("corpId", corpId);
		paramMap.put("tableName", tableName);
		systemMapper.getGenderById(paramMap);
		Object o = paramMap.get("seqId");
		if (o == null) {
			return "";
		}
		BigDecimal b = (BigDecimal)o;		
		return b.toString();
	}

	public void getCallGenOrderBase(Map<String, Object> map) {
		 systemMapper.getCallGenOrderBase(map);
	}


	
	private static String autoGenericCode(String code, int num) {
        String result = "";
        result = String.format("%0" + num + "d", Integer.parseInt(code));

        return result;
    }

	public  void getCallPointDay(Map<String,Object> map) {
		systemMapper.getCallPointDay(map);
	}
	public  void getCallPointMonth(Map<String,Object> map) {
		systemMapper.getCallPointMonth(map);
	}
	public  void getPointYear(Map<String,Object> map) {
		systemMapper.getPointYear(map);
	}
	public  void getCallLineDay(Map<String,Object> map) {
		systemMapper.getCallLineDay(map);
	}
	public  void getCallLineMonth(Map<String,Object> map) {
		systemMapper.getCallLineMonth(map);
	}
	public  void getCallPointYear(Map<String,Object> map) {
		systemMapper.getCallPointYear(map);
	}
	public  void getCallLineYear(Map<String,Object> map) {
		systemMapper.getCallLineYear(map);
	}
	public  void getCallPtimeDay(Map<String,Object> map) {
		systemMapper.getCallPtimeDay(map);
	}
	public  void getCallPtimeMonth(Map<String,Object> map) {
		systemMapper.getCallPtimeMonth(map);
	}
	public  void getCallPtimeWeek(Map<String,Object> map) {
		systemMapper.getCallPtimeWeek(map);
	}
	public  void getCallVtimeDay(Map<String,Object> map) {
		systemMapper.getCallVtimeDay(map);
	}
	public  void getCallVtimeMonth(Map<String,Object> map) {
		systemMapper.getCallVtimeMonth(map);
	}
	public  void getCallVtimeWeek(Map<String,Object> map) {
		systemMapper.getCallVtimeWeek(map);
	}
	public  void getCallReportBoard(Map<String,Object> map) {
		systemMapper.getCallReportBoard(map);
	}
	public  void getCallReportDsale(Map<String,Object> map) {
		systemMapper.getCallReportDsale(map);
	}
	public  void getCallReportMsale(Map<String,Object> map) {
		systemMapper.getCallReportMsale(map);
	}
	public  void getCallReportOsale(Map<String,Object> map) {
		systemMapper.getCallReportOsale(map);
	}

	public void getCallOsaleDay(Map<String, Object> map) {
		systemMapper.getCallOsaleDay(map);
	}
	public void getCallOsaleDayHistory(Map<String, Object> map) {
		systemMapper.getCallOsaleDayHistory(map);
	}
	public void getCallOsaleOther(Map<String, Object> map) {
		systemMapper.getCallOsaleOther(map);		
	}
	public void getCallOsaleOtherHistory(Map<String, Object> map) {
		systemMapper.getCallOsaleOtherHistory(map);
	}

	public void getCallVtimeQuarter(Map<String, Object> map) {
		systemMapper.getCallVtimeQuarter(map);
	}
	public void getCallVtimeHalfYear(Map<String, Object> map) {
		systemMapper.getCallVtimeHalfYear(map);
	}
	public void getCallVtimeYear(Map<String, Object> map) {
		systemMapper.getCallVtimeYear(map);
	}
}
