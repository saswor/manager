/**
 * 
 */
package com.manage.project.system.server.domain;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.manage.project.system.base.controller.PayConfigController;

/**
 * 发送和接收服务器报文实体类
 * 
 * @author zhangjiawei
 * @date 2018年11月2日
 * 
 */
public class ServerMessage {
	
	private static final Logger log = LoggerFactory.getLogger(ServerMessage.class);
	//封装报文参数
	private Map<String, Map<String,Map<String,Object>>> map;
	
	public static final String SUCCESS = "0000";

	public ServerMessage() {
		super();
		map = new HashMap<String, Map<String,Map<String,Object>>>();
		Map<String ,Object> zhead = new HashMap<String ,Object>();
		Map<String ,Map<String ,Object>> zmsg = new HashMap<String ,Map<String ,Object>>();
		zmsg.put("ZHEAD", zhead);
		map.put("ZMSG", zmsg);
	}
	
	private ServerMessage(Map<String ,Object> zhead,Map<String ,Object> zbody) {
		super();
		map = new HashMap<String, Map<String,Map<String,Object>>>();
		Map<String ,Map<String ,Object>> zmsg = new HashMap<String ,Map<String ,Object>>();
		zmsg.put("ZHEAD", zhead);
		if(zbody!=null) {
			zmsg.put("ZBODY", zbody);
		}
		map.put("ZMSG", zmsg);
	}
	
	//获取请求报文
	private Map<String ,Map<String ,Object>> getZmsg(){
		return map.get("ZMSG");
	}
	
	//获取公共参数
	private Map<String ,Object> getZhead() {
		return (Map<String ,Object>)map.get("ZMSG").get("ZHEAD");
	}
	
	//获取私有参数
	public Map<String ,Object> getZbody() {
		return (Map<String ,Object>)map.get("ZMSG").get("ZBODY");
		
	}
	
	//设置公共参数
	public void putZhead(String key ,Object value) {
		getZhead().put(key, value);
	}
	
	//设置交易请求平台代码
	public void putBCode(String bCode) {
		putZhead("BCode", bCode);
	}
	
	//设置交易接口的编码
	public void putTCode(String tCode) {
		putZhead("TCode", tCode);
	}
	
	//设置交易版本代号
	public void putVersion(String version) {
		putZhead("Version", version);
	}
	
	//设置服务器共有参数,如果是查询接口则由此内容
	public void putIStart(String iStart) {
		putZhead("IStart", iStart);
	}
	
	//设置私有参数
	public void putZbody(String key,Object value) {
		Map<String, Object> zbody = getZbody();
		if(zbody==null) {
			zbody=new HashMap<String, Object>();
		}
		zbody.put(key, value);
		getZmsg().put("ZBODY", zbody);
	}
	
	//设置私有参数
	public void setZbody(Map<String, Object> zbody) {
		getZmsg().put("ZBODY", zbody);
	}
	
	//获取公共参数
	public Object getZhead(String key) {
		return getZhead().get(key);
	}
	
	//获取返回代码
	public String getRetCode() {
		return (String)getZhead("RetCode");
	}
	
	//获取返回信息
	public String getRetMsg() {
		return (String)getZhead("RetMsg");
	}
	
	//获取总计数量,如果是查询接口则有此内容
	public int getTotNum() {
		int totNum = -1;
		try {
			if(getZhead("TotNum") instanceof Integer) {
				return (int)getZhead("TotNum");
			}else if(getZhead("TotNum") instanceof String){
				return Integer.parseInt((String)getZhead("TotNum"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return totNum;
	}
	
	//获取私有参数内容
	public Object getZbody(String key) {
		Map<String, Object> zbody = getZbody();
		if(zbody!=null) {
			return getZbody().get(key);
		}else {
			return null;
		}		
	}
	
	//请求是否成功
	public boolean success() {
		return SUCCESS.equals(getRetCode());
	}
	
	//将报文封装成json
	public String toJson() {
		return JSONObject.toJSONString(map);
	}
	
	//将json封装成报文
	public static ServerMessage jsonToMessage(String json) {
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			JSONObject zmsg = jsonObject.getJSONObject("ZMSG");
			Map<String,Object> zhead = zmsg.getObject("ZHEAD",Map.class);
			Map<String,Object> zbody = zmsg.getObject("ZBODY",Map.class);
			return new ServerMessage(zhead, zbody);
		}catch (Exception e) {
			return null;
		}	
	}
}
