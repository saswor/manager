package com.manage.framework.web.controller;

import com.manage.framework.web.domain.AjaxResult;
import com.manage.framework.web.domain.ApiResult;

/**
 * 对外提供api的controller基类
 * @author xufeng
 *
 */
public class BaseApiController {

	/**
     * 返回失败消息
     */
    public ApiResult error(String message)
    {
        return ApiResult.error(message);
    }
    
    /**
     * 返回失败消息
     */
    public ApiResult error()
    {
        return ApiResult.error();
    }
    
    public ApiResult success(String message)
    {
        return ApiResult.success(message);
    }
    
    public ApiResult success()
    {
        return ApiResult.success();
    }
}
