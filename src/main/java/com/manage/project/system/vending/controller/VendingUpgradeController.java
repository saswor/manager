package com.manage.project.system.vending.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.exception.vending.VendingNotExitException;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingUpgrade;
import com.manage.project.system.vending.domain.VendingUpgradeTask;
import com.manage.project.system.vending.service.IVendingUpgradeService;
import com.manage.project.system.vending.service.IVendingUpgradeTaskService;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 控制设备的升级，升级包括app升级、固件升级、视频软件升级 信息操作处理
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/vendingUpgrade")
public class VendingUpgradeController extends BaseController
{
	
	@Autowired
	private IVendingUpgradeService vendingUpgradeService;
	
	@Autowired
	private IVendingUpgradeTaskService vendingUpgradeTaskService;
	
	private Logger log = LoggerFactory.getLogger(VendingUpgradeController.class);

	
	/**
	 * 查询控制设备的升级，升级包括app升级、固件升级、视频软件升级列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingUpgrade vendingUpgrade)
	{
		startPage();
		vendingUpgrade.setCorpId(SystemUtil.getCorpId());
        List<VendingUpgrade> list = vendingUpgradeService.selectVendingUpgradeList(vendingUpgrade);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 查询控制设备的升级，升级包括app升级、固件升级、视频软件升级列表
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(String upgradeId)
	{
        VendingUpgrade vendingUpgrade = vendingUpgradeService.selectVendingUpgradeById(upgradeId);
        VendingUpgradeTask vendingUpgradeTask = new VendingUpgradeTask();
        vendingUpgradeTask.setUpgradeId(upgradeId);
        List<VendingUpgradeTask> vendingUpgradeTaskList = vendingUpgradeTaskService.selectVendingUpgradeTaskAndVendingUpgradeList(vendingUpgradeTask);
        vendingUpgrade.setVendingUpgradeTaskList(vendingUpgradeTaskList);
		return AjaxResult.success(vendingUpgrade);
	}

	/**
	 * 新增保存控制设备的升级，升级包括app升级、固件升级、视频软件升级
	 */
	@Log(title = "控制设备的升级，升级包括app升级、固件升级、视频软件升级", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody VendingUpgrade vendingUpgrade)
	{	
		try {
			int result = vendingUpgradeService.insertVendingUpgrade(vendingUpgrade);
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("升级名称不能为空");
			}else if(result==3) {
				return AjaxResult.error("升级名称已存在");
			}else if(result==4) {
				return AjaxResult.error("升级类型不能为空");
			}else if(result==5) {
				return AjaxResult.error("延迟升级的延迟时间不能为空");
			}else if(result==6) {
				return AjaxResult.error("下发的售货机不能为空");
			}else if(result==7) {
				return AjaxResult.error("下发的售货机未认证");
			}else if(result==8) {
				return AjaxResult.error("下发的售货机已删除");
			}else if(result==9) {
				return AjaxResult.error("下发的售货机存在待升级的任务");
			}else {
				return AjaxResult.error("新增升级任务失败");
			}
		}catch (VendingNotExitException e) {
			log.error("新增升级任务失败,售货机不存在:",e);
			return AjaxResult.error("新增升级任务失败,售货机不存在");
		}catch (Exception e) {
			log.error("新增升级任务失败:",e);
			return AjaxResult.error("新增升级任务失败");
		}
	}
	
	/**
	 * 修改保存控制设备的升级，升级包括app升级、固件升级、视频软件升级
	 */
	@Log(title = "控制设备的升级，升级包括app升级、固件升级、视频软件升级", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody VendingUpgrade vendingUpgrade)
	{		
		try {
			int result = vendingUpgradeService.updateVendingUpgrade(vendingUpgrade);
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("升级名称不能为空");
			}else if(result==3) {
				return AjaxResult.error("升级名称已存在");
			}else if(result==4) {
				return AjaxResult.error("升级类型不能为空");
			}else if(result==5) {
				return AjaxResult.error("延迟升级的延迟时间不能为空");
			}else if(result==6) {
				return AjaxResult.error("下发的售货机不能为空");
			}else if(result==7) {
				return AjaxResult.error("下发的售货机未认证");
			}else if(result==8) {
				return AjaxResult.error("下发的售货机已删除");
			}else if(result==9) {
				return AjaxResult.error("下发的售货机存在待升级的任务");
			}else if(result==10) {
				return AjaxResult.error("原升级是否已经推送终端,不能修改");
			}else {
				return AjaxResult.error("修改升级任务失败");
			}
		}catch (VendingNotExitException e) {
			log.error("新增升级任务失败,售货机不存在:",e);
			return AjaxResult.error("新增升级任务失败,售货机不存在");
		}catch (Exception e) {
			log.error("修改升级任务失败:",e);
			return AjaxResult.error("修改升级任务失败");
		}
	}
	
	/**
	 * 删除控制设备的升级，升级包括app升级、固件升级、视频软件升级
	 */
	@Log(title = "控制设备的升级，升级包括app升级、固件升级、视频软件升级", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		try {
			int result = vendingUpgradeService.deleteVendingUpgradeByIds(ids.getIds());
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("当前未选中任何一项");
			}else if(result==3) {
				return AjaxResult.error("要删除的升级信息已经推送终端,无法删除");
			}else {
				return AjaxResult.error("删除升级任务失败");
			}
		}catch (Exception e) {
			log.error("删除升级任务失败:",e);
			return AjaxResult.error("删除升级任务失败");
		}
	}
	
}
