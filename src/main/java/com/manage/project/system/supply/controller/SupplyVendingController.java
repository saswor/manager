package com.manage.project.system.supply.controller;

import java.util.List;

import com.manage.project.system.supply.domain.SupplyVending;
import com.manage.project.system.supply.service.ISupplyVendingService;
import com.manage.project.system.util.SystemUtil;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 补货配置的售货机 信息操作处理
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/supply/supplyVending")
public class SupplyVendingController extends BaseController
{
    private String prefix = "module/supplyVending";
	
	@Autowired
	private ISupplyVendingService supplyVendingService;
	
	@RequiresPermissions("module:supplyVending:view")
	@GetMapping()
	public String supplyVending()
	{
	    return prefix + "/supplyVending";
	}
	
	/**
	 * 查询补货配置的售货机列表
	 */
	@RequiresPermissions("module:supplyVending:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SupplyVending supplyVending)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			supplyVending.setCorpId("");
		} else {
			supplyVending.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<SupplyVending> list = supplyVendingService.selectSupplyVendingList(supplyVending);
		return getDataTable(list);
	}
	
	/**
	 * 新增补货配置的售货机
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存补货配置的售货机
	 */
	@RequiresPermissions("module:supplyVending:add")
	@Log(title = "补货配置的售货机", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SupplyVending supplyVending)
	{		
		return toAjax(supplyVendingService.insertSupplyVending(supplyVending));
	}

	/**
	 * 修改补货配置的售货机
	 */
	@GetMapping("/edit/{logid}")
	public String edit(@PathVariable("logid") String logid, ModelMap mmap)
	{
		SupplyVending supplyVending = supplyVendingService.selectSupplyVendingById(logid);
		mmap.put("supplyVending", supplyVending);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存补货配置的售货机
	 */
	@RequiresPermissions("module:supplyVending:edit")
	@Log(title = "补货配置的售货机", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SupplyVending supplyVending)
	{		
		return toAjax(supplyVendingService.updateSupplyVending(supplyVending));
	}
	
	/**
	 * 删除补货配置的售货机
	 */
	@RequiresPermissions("module:supplyVending:remove")
	@Log(title = "补货配置的售货机", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(supplyVendingService.deleteSupplyVendingByIds(ids));
	}
	
}
