package com.manage.project.system.vending.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingState;
import com.manage.project.system.vending.domain.VendingUpgrade;
import com.manage.project.system.vending.domain.VendingUpgradeTask;
import com.manage.project.system.vending.service.IVendingService;
import com.manage.project.system.vending.service.IVendingStateService;
import com.manage.project.system.vending.service.IVendingUpgradeService;
import com.manage.project.system.vending.service.IVendingUpgradeTaskService;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 具体售货机的升级任务列 信息操作处理
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/vendingUpgradeTask")
public class VendingUpgradeTaskController extends BaseController
{
	
	@Autowired
	private IVendingUpgradeTaskService vendingUpgradeTaskService;
	
	@Autowired
	private IVendingUpgradeService vendingUpgradeService;
	
	@Autowired
	private IVendingStateService vendingStateService;
	
	@Autowired
	private IVendingService vendingService;
	
	/**
	 * 查询具体售货机的升级任务列列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingUpgradeTask vendingUpgradeTask)
	{
		startPage();
		vendingUpgradeTask.setCorpId(SystemUtil.getCorpId());
        List<VendingUpgradeTask> list = vendingUpgradeTaskService.selectVendingUpgradeTaskAndVendingUpgradeList(vendingUpgradeTask);
        for (VendingUpgradeTask upgradeTask : list) {
        	String siteId = upgradeTask.getSiteId();
        	Vending vending = vendingService.selectVendingBySiteId(siteId);
        	if(vending!=null) {
        		upgradeTask.setLineName(vending.getLineName());
        		upgradeTask.setFactoryName(SystemUtil.parseFactoryId(vending.getFactoryId()));
        		upgradeTask.setNetSateName(SystemUtil.parseNetSate(vending.getNetSate()));
        	}
        	VendingState vendingState = vendingStateService.selectVendingStateBySiteId(siteId);
        	if(vendingState!=null) {
        		upgradeTask.setSeqId(vendingState.getSeqId());
        		upgradeTask.setSignalValue(vendingState.getSignalValue());
        	}
		}
		return AjaxResult.success(getDataTable(list));
	}

	
	/**
	 * 新增保存具体售货机的升级任务列
	 */
	@Log(title = "具体售货机的升级任务列", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VendingUpgradeTask vendingUpgradeTask)
	{		
		int result = vendingUpgradeTaskService.insertVendingUpgradeTask(vendingUpgradeTask);
		return AjaxResult.success();
	}

	/**
	 * 修改保存具体售货机的升级任务列
	 */
	@Log(title = "具体售货机的升级任务列", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VendingUpgradeTask vendingUpgradeTask)
	{		
		return toAjax(vendingUpgradeTaskService.updateVendingUpgradeTask(vendingUpgradeTask));
	}
	
	/**
	 * 删除具体售货机的升级任务列
	 */
	@Log(title = "具体售货机的升级任务列", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(vendingUpgradeTaskService.deleteVendingUpgradeTaskByIds(ids));
	}
	
}
