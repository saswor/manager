package com.manage.framework.web.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 操作消息提醒
 * 
 */
public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    /**
     * 初始化一个新创建的 Message 对象
     */
    public AjaxResult()
    {
    }

    /**
     * 返回错误消息
     * 
     * @return 错误消息
     */
    public static AjaxResult error()
    {
        return error(1, "操作失败");
    }

    /**
     * 返回错误消息
     * 
     * @param msg 内容
     * @return 错误消息
     */
    public static AjaxResult error(String msg)
    {
        return error(500, msg);
    }

    /**
     * 返回错误消息
     * 
     * @param code 错误码
     * @param msg 内容
     * @return 错误消息
     */
    public static AjaxResult error(int code, String msg)
    {
        AjaxResult json = new AjaxResult();
        Map m = new HashMap();
        m.put("retMsg", msg);
        m.put("reTCode", String.valueOf(code));
        json.put("zhead", m);

        return json;
    }

    /**
     * 返回成功消息
     * 
     * @param msg 内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg)
    {
        AjaxResult json = new AjaxResult();        
        Map m = new HashMap();
        m.put("retMsg", msg);
        m.put("reTCode", "0000");
        json.put("zhead", m);
        return json;
    }
    
    /**
     * 返回成功消息
     * 
     * @return 成功消息
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("操作成功");
    }
    
    /**
     * 返回成功消息
     * 
     * @return 成功消息
     */
    public static AjaxResult success(Object datas)
    {
    	AjaxResult json = AjaxResult.success("操作成功");
    	Map zbody = new HashMap();
    	zbody.put("reTCode", "0000");
    	zbody.put("datas", datas);
    	
    	json.put("zbody", zbody);
    	return json;
    }
    
    /**
     * 判断是否成功
     * 
     * @return 成功消息
     */
    public String getMsg()
    {
    	Map map = (Map)get("zhead");
    	return (String)map.get("retMsg");
    }
    
    /**
     * 获取msg
     * 
     * @return 成功消息
     */
    public boolean isSuccess()
    {
    	Map map = (Map)get("zhead");
    	Object reTCode = map.get("reTCode");
    	if("0000".equals(reTCode)) {
    		return true;
    	}else {
    		return false;
    	}
    }

    /**
     * 返回成功消息
     * 
     * @param key 键值
     * @param value 内容
     * @return 成功消息
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
    
    /**
     * 放入返回体
     * @param key
     * @param value
     * @return
     */
    public AjaxResult putBody(String key, Object value) {
    	Object zbody = super.get("zbody");
    	Map body;
    	if (zbody == null) {
    		body = new HashMap();    		
    	} else {
    		body = (HashMap)super.get("zbody");
    	}
    	body.put(key, value);
    	super.put("zbody", body);
    	return this;
    }
    
    /**
     * 放入返回体中的datas部分
     * @param key
     * @param value
     * @return
     */
    public AjaxResult putBodyDatas(String reTCode, String key, Object value) {
    	Object zbody = super.get("zbody");
    	Map body;
    	if (zbody == null) {
    		body = new HashMap();    		
    	} else {
    		body = (HashMap)super.get("zbody");
    	}
    	body.put(key, value);
    	super.put("zbody", body);
    	return this;
    }
    
    /**
     * 返回datas
     * @return
     */
    public Object getBodyDatas() {
    	Map map = (Map)get("zbody");
    	if(map!=null) {
    		return map.get("datas");
    	}else {
    		return null;
    	}
    }
}
