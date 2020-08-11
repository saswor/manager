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

import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingPlane;
import com.manage.project.system.vending.service.IVendingPlaneService;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 售货机货道 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/vendingPlane")
public class VendingPlaneController extends BaseController
{
	@Autowired
	private IVendingPlaneService vendingPlaneService;

	/**
	 * 查询售货机货道列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(VendingPlane vendingPlane)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			vendingPlane.setCorpId("");
		} else {
			vendingPlane.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<VendingPlane> list = vendingPlaneService.selectVendingPlaneList(vendingPlane);
		return getDataTable(list);
	}
	
	/**
	 * 新增保存售货机货道
	 */
	@Log(title = "售货机货道", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody VendingPlane vendingPlane)
	{		
		return toAjax(vendingPlaneService.insertVendingPlane(vendingPlane));
	}
	
	/**
	 * 修改保存售货机货道
	 */
	@Log(title = "售货机货道", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody VendingPlane vendingPlane)
	{		
		return toAjax(vendingPlaneService.updateVendingPlane(vendingPlane));
	}
	
	/**
	 * 删除售货机货道
	 */
	@Log(title = "售货机货道", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(vendingPlaneService.deleteVendingPlaneByIds(ids.getIds()));
	}
	
}
