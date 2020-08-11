package com.manage.project.system.vending.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingLogfile;
import com.manage.project.system.vending.service.IVendingLogfileService;

@Controller
@RequestMapping("/system/vendingLogfile")
public class VendingLogfileController extends BaseController{
	
	@Autowired
	private IVendingLogfileService vendingLogfileService;
	
	private Logger log = LoggerFactory.getLogger(VendingLogfileController.class);
	
	/**
	 * 查询售货机的软件版本信息
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingLogfile vendingLogfile) {
		startPage();
		if (SystemUtil.isZhilai()) {
			vendingLogfile.setCorpId("");
		} else {
			vendingLogfile.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<VendingLogfile> list = vendingLogfileService.selectVendingLogfileList(vendingLogfile);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 下载日志文件
	 */
	@PostMapping("/download")
	@ResponseBody
	public AjaxResult download(@RequestBody VendingLogfile vendingLogfile) {
		try {
			return vendingLogfileService.download(vendingLogfile);
		} catch (IOException e) {
			log.error("下载日志文件失败:",e);
			return AjaxResult.error("下载日志文件失败");
		}
		
	}

	@Log(title = "售货机的基本，主柜，副柜，货道，商品", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody VendingLogfile vendingLogfile) {
		try {
			int result = vendingLogfileService.insertVendingLogfile(vendingLogfile);
			if(result==1) {
				return AjaxResult.success("已通知终端上传日志");
			}else if(result==2) {
				return AjaxResult.error("售货机不存在");
			}else if(result==3) {
				return AjaxResult.error("开始时间和结束时间不能为空");
			}else if(result==4) {
				return AjaxResult.error("开始时间不能迟于结束时间");
			}else if(result==5) {
				return AjaxResult.error("开始时间和结束时间相差不能超过一个半小时");
			}else if(result==6) {
				return AjaxResult.error("通知终端上传日志失败");
			}else {
				return AjaxResult.error("上传日志失败");
			}
		}catch (Exception e) {
			log.error("上传日志失败,时间:"+DateUtils.getTime()+",上传的信息:"+JSONObject.toJSONString(vendingLogfile),e);
			return AjaxResult.error("上传日志失败");
		}
	}
}
