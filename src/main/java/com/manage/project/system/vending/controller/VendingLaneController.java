package com.manage.project.system.vending.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingLane;
import com.manage.project.system.vending.service.IVendingLaneService;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 售货机货道 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/vendingLane")
public class VendingLaneController extends BaseController
{
	@Autowired
	private IVendingLaneService vendingLaneService;
	
	/**
	 * 查询售货机货道列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingLane vendingLane)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			vendingLane.setCorpId("");
		} else {
			vendingLane.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<VendingLane> list = vendingLaneService.selectVendingLaneList(vendingLane);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存售货机货道
	 */
	@Log(title = "售货机货道", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VendingLane vendingLane)
	{		
		return toAjax(vendingLaneService.insertVendingLane(vendingLane));
	}
	
	/**
	 * 修改保存售货机货道
	 */
	@Log(title = "售货机货道", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VendingLane vendingLane)
	{		
		return toAjax(vendingLaneService.updateVendingLane(vendingLane));
	}
	
	/**
	 * 删除售货机货道
	 */
	@Log(title = "售货机货道", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(vendingLaneService.deleteVendingLaneByIds(ids.getIds()));
	}
	
}
