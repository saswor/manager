package com.manage.framework.web.domain;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 接口返回信息
 * @author xufeng
 *
 */
public class ApiResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private Zhead zhead;	// 头部
	private Object zbody;	// 消息体
	public Zhead getZhead() {
		return zhead;
	}
	public void setZhead(Zhead zhead) {
		this.zhead = zhead;
	}
	
	/**
     * 返回错误消息
     * 
     * @return 错误消息
     */
    public static ApiResult error()
    {
    	Zhead zhead = new Zhead(Zhead.ERROR, "操作失败!");    	
    	ApiResult result = new ApiResult();
    	result.setZhead(zhead);
        return result;
    }
    
    public static ApiResult error(String msg)
    {
    	Zhead zhead = new Zhead(Zhead.ERROR, msg);    	
    	ApiResult result = new ApiResult();
    	result.setZhead(zhead);
        return result;
    }
    
    public static ApiResult success()
    {
    	Zhead zhead = new Zhead(Zhead.SUCCESS, "操作成功!");    	
    	ApiResult result = new ApiResult();
    	result.setZhead(zhead);
        return result;
    }
    
    public static ApiResult success(String msg)
    {
    	Zhead zhead = new Zhead(Zhead.SUCCESS, msg);    	
    	ApiResult result = new ApiResult();
    	result.setZhead(zhead);
        return result;
    }
    
	public Object getZbody() {
		return zbody;
	}
	public void setZbody(Object zbody) {
		this.zbody = zbody;
	}
	
	public static void main(String[] args) {
		ApiResult a = new ApiResult();
		Zhead zhead = new Zhead("success");
		a.setZhead(zhead);
		a.setZbody(new Zhead());
		System.out.println(JSONObject.toJSONString(a));
	}
}
