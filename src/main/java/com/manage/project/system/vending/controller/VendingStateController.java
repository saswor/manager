package com.manage.project.system.vending.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.poi.ExcelUtil;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.project.system.product.domain.ProductClassify;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingState;
import com.manage.project.system.vending.service.IVendingService;
import com.manage.project.system.vending.service.IVendingStateService;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。 信息操作处理
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/vendingState")
public class VendingStateController extends BaseController
{
	
	@Autowired
	private IVendingStateService vendingStateService;
	
	@Autowired
	private IVendingService vendingService;
	
	/**
	 * 查询保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingState vendingState)
	{
		vendingState.setCorpId(SystemUtil.getCorpId());
 		startPage();
        List<VendingState> list = vendingStateService.selectVendingStateList(vendingState);
        for (VendingState state : list) {
        	String siteId = state.getSiteId(); 
        	Vending vending = vendingService.selectVendingBySiteId(siteId);
        	if(vending!=null) {
        		state.setPlatType(vending.getPlatType());
        		state.setNetSate(vending.getNetSate());
        	}
		}
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 查询设备详情
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult list(String siteId)
	{
        VendingState vendingState = vendingStateService.selectVendingStateBySiteId(siteId); 
        Vending vending = vendingService.selectVendingBySiteId(siteId);
    	if(vending!=null) {
    		vendingState.setPlatType(vending.getPlatType());
    		vendingState.setNetSate(vending.getNetSate());
    	}
		return AjaxResult.success(vendingState);
	}
	

	/**
	 * 新增保存保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。
	 */
	@Log(title = "保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VendingState vendingState)
	{		
		return toAjax(vendingStateService.insertVendingState(vendingState));
	}

	/**
	 * 修改保存保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。
	 */
	@Log(title = "保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VendingState vendingState)
	{		
		return toAjax(vendingStateService.updateVendingState(vendingState));
	}
	
	/**
	 * 删除保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。
	 */
	@Log(title = "保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(vendingStateService.deleteVendingStateByIds(ids));
	}
	
	/**
	 * 新增保存保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。
	 */
	@Log(title = "导出设备状态", action = BusinessType.INSERT)
	@PostMapping("/exportVendingState")
	@ResponseBody
	public AjaxResult exportVendingState(@RequestBody VendingState vendingState)
	{		
		try {
			ExcelUtil<VendingState> util = new ExcelUtil<VendingState>(VendingState.class);
			vendingState.setCorpId(SystemUtil.getCorpId());
	        List<VendingState> list = vendingStateService.selectVendingStateList(vendingState);
	        for (VendingState state : list) {
	        	String siteId = state.getSiteId(); 
	        	Vending vending = vendingService.selectVendingBySiteId(siteId);
	        	if(vending!=null) {
	        		state.setPlatType(vending.getPlatType());
	        		state.setNetSate(vending.getNetSate());
	        		state.setPlatTypeName(state.getPlatTypeName());
	        		state.setNetSateName(state.getNetSateName());
	        		state.setScreenTypeName(state.getScreenTypeName());
	        		state.setVBaseband(state.getVBaseband());
	        	}
			}
			
            return util.exportExcel(list, "设备信息");
			
		} catch (Exception e) {
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
	
}
