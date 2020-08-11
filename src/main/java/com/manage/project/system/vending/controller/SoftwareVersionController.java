package com.manage.project.system.vending.controller;

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

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.SoftwareVersion;
import com.manage.project.system.vending.domain.VendingCabinet;
import com.manage.project.system.vending.service.ISoftwareVersionService;

/**
 * 软件版本信息 控制层
 * 
 * @author zhangjiawei
 *
 */
@Controller
@RequestMapping("/system/softwareVersion")
public class SoftwareVersionController extends BaseController{
	
	private Logger log = LoggerFactory.getLogger(SoftwareVersionController.class);
	
	@Autowired
	private ISoftwareVersionService softwareVersionService;

	/**
	 * 查询售货机的软件版本信息
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(SoftwareVersion softwareVersion)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			softwareVersion.setCorpId("");
		} else {
			softwareVersion.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<SoftwareVersion> list = softwareVersionService.selectSoftwareVersionList(softwareVersion);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 查询售货机的软件版本信息详情
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(String versionId)
	{
        SoftwareVersion softwareVersion = softwareVersionService.selectSoftwareVersionById(versionId);
		return AjaxResult.success(softwareVersion);
	}
	
	/**
	 * 新增保存售货机挂载的货柜，主柜的挂载副柜
	 */
	@Log(title = "售货机版本信息", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody SoftwareVersion softwareVersion)
	{		
		try {
			int result = softwareVersionService.insertSoftwareVersion(softwareVersion);
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("版本号不能为空");
			}else if(result==3) {
				return AjaxResult.error("版本号已存在");
			}else if(result==4) {
				return AjaxResult.error("版本类型不能为空");
			}else if(result==5) {
				return AjaxResult.error("版本文件不能为空");
			}else if(result==6) {
				return AjaxResult.error("版本文件保存失败");
			}else {
				return AjaxResult.error("保存失败");
			}
		}catch (Exception e) {
			log.error("新增版本信息失败,时间:"+DateUtils.getTime(),e);
			return AjaxResult.error("保存失败");
		}
	}
	
	/**
	 * 修改保存售货机挂载的货柜，主柜的挂载副柜
	 */
	@Log(title = "售货机版本信息", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody SoftwareVersion softwareVersion)
	{		
		try {
			int result = softwareVersionService.updateSoftwareVersion(softwareVersion);
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("版本号不能为空");
			}else if(result==3) {
				return AjaxResult.error("版本号已存在");
			}else if(result==4) {
				return AjaxResult.error("版本类型不能为空");
			}else if(result==5) {
				return AjaxResult.error("版本文件不能为空");
			}else if(result==6) {
				return AjaxResult.error("版本文件保存失败");
			}else {
				return AjaxResult.error("修改失败");
			}
		}catch (Exception e) {
			log.error("修改版本信息失败,时间:"+DateUtils.getTime(),e);
			return AjaxResult.error("修改失败");
		}
	}
	
	/**
	 * 删除售货机挂载的货柜，主柜的挂载副柜
	 */
	@Log(title = "售货机版本信息", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{	
		try {
			int result = softwareVersionService.deleteSoftwareVersion(ids.getIds());
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("版本号不能为空");
			}else if(result==3) {
				return AjaxResult.error("版本软件已经加入升级任务");
			}else {
				return AjaxResult.error("删除失败");
			}
		}catch (Exception e) {
			log.error("删除版本信息失败,时间:"+DateUtils.getTime(),e);
			return AjaxResult.error("删除失败");
		}
	}
}
