package com.manage.project.system.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.CacheUtils;
import com.manage.common.utils.StringUtils;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.util.BussinessCacheService;

//@Controller
//@RequestMapping("/system/cache")
//public class CacheController extends BaseController{
//	
//	private static final Logger log = LoggerFactory.getLogger(CacheController.class);
//
//	@GetMapping("getCache")
//	@ResponseBody
//	public AjaxResult getCache(String key) {
//		try {
//			if(StringUtils.isNotEmpty(key)) {
//				Object object = CacheUtils.get(key);
//				if(object!=null) {
//					return AjaxResult.success(object);
//				}else {
//					return AjaxResult.error("缓存不存在");
//				}
//			}else {
//				return AjaxResult.error("key值不能为空");
//			}
//		}catch (Exception e) {
//			return AjaxResult.error("程序错误");
//		}
//	}
//	
//	@PostMapping("putCache")
//	@ResponseBody
//	public AjaxResult putCache(String key,Object value) {
//		try {
//			if(StringUtils.isNotEmpty(key)) {
//				if(value!=null) {
//					CacheUtils.put(key, value);
//					return AjaxResult.success();
//				}else {
//					return AjaxResult.error("value值不能为空");
//				}
//			}else {
//				return AjaxResult.error("key值不能为空");
//			}
//		}catch (Exception e) {
//			return AjaxResult.error("程序错误");
//		}
//	}
//	
//}
