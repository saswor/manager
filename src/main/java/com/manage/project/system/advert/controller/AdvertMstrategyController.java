package com.manage.project.system.advert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.advert.domain.AdvertMstrategy;
import com.manage.project.system.advert.service.IAdvertMstrategyService;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 广告配置 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/advertMstrategy")
public class AdvertMstrategyController extends BaseController
{
	@Autowired
	private IAdvertMstrategyService advertMstrategyService;
	
	/**
	 * 查询广告配置列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(AdvertMstrategy advertMstrategy)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			advertMstrategy.setCorpId("");
		} else {
			advertMstrategy.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<AdvertMstrategy> list = advertMstrategyService.selectAdvertMstrategyList(advertMstrategy);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存广告配置
	 */
	@Log(title = "广告配置", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AdvertMstrategy advertMstrategy)
	{		
		return toAjax(advertMstrategyService.insertAdvertMstrategy(advertMstrategy));
	}
	
	/**
	 * 修改保存广告配置
	 */
	@Log(title = "广告配置", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AdvertMstrategy advertMstrategy)
	{		
		return toAjax(advertMstrategyService.updateAdvertMstrategy(advertMstrategy));
	}
	
	/**
	 * 删除广告配置
	 */
	@Log(title = "广告配置", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(advertMstrategyService.deleteAdvertMstrategyByIds(ids.getIds()));
	}
	
}
