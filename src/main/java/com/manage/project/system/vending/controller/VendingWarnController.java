package com.manage.project.system.vending.controller;

import java.util.List;
import java.util.UUID;

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
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingWarn;
import com.manage.project.system.vending.service.IVendingWarnService;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 设备告警 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/vendingWarn")
public class VendingWarnController extends BaseController
{
	@Autowired
	private IVendingWarnService vendingWarnService;
	
	/**
	 * 查询设备告警列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingWarn vendingWarn)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			vendingWarn.setCorpId("");
		} else {
			vendingWarn.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		if("00".equals(vendingWarn.getWarnType())) {
			vendingWarn.setWarnType("");
		}
        List<VendingWarn> list = vendingWarnService.selectVendingWarnList(vendingWarn);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存设备告警
	 */
	@Log(title = "设备告警", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody VendingWarn vendingWarn)
	{		
		vendingWarn.setLogid(UUID.randomUUID().toString());
		vendingWarn.setCorpId(SystemUtil.getCorpId());
		return toAjax(vendingWarnService.insertVendingWarn(vendingWarn));
	}
	
	/**
	 * 修改保存设备告警
	 */
	@Log(title = "设备告警", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave( @RequestBody VendingWarn vendingWarn)
	{		
		return toAjax(vendingWarnService.updateVendingWarn(vendingWarn));
	}
	
	/**
	 * 删除设备告警
	 */
	@Log(title = "设备告警", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(vendingWarnService.deleteVendingWarnByIds(ids.getIds()));
	}
	
}
