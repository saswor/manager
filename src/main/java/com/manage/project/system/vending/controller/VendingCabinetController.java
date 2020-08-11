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
import com.manage.project.system.vending.domain.VendingCabinet;
import com.manage.project.system.vending.service.IVendingCabinetService;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 售货机挂载的货柜，主柜的挂载副柜 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/vendingCabinet")
public class VendingCabinetController extends BaseController
{
	@Autowired
	private IVendingCabinetService vendingCabinetService;
	
	/**
	 * 查询售货机挂载的货柜，主柜的挂载副柜列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(VendingCabinet vendingCabinet)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			vendingCabinet.setCorpId("");
		} else {
			vendingCabinet.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<VendingCabinet> list = vendingCabinetService.selectVendingCabinetList(vendingCabinet);
		return getDataTable(list);
	}
	
	/**
	 * 新增保存售货机挂载的货柜，主柜的挂载副柜
	 */
	@Log(title = "售货机挂载的货柜，主柜的挂载副柜", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody VendingCabinet vendingCabinet)
	{		
		return toAjax(vendingCabinetService.insertVendingCabinet(vendingCabinet));
	}
	
	/**
	 * 修改保存售货机挂载的货柜，主柜的挂载副柜
	 */
	@Log(title = "售货机挂载的货柜，主柜的挂载副柜", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody VendingCabinet vendingCabinet)
	{		
		return toAjax(vendingCabinetService.updateVendingCabinet(vendingCabinet));
	}
	
	/**
	 * 删除售货机挂载的货柜，主柜的挂载副柜
	 */
	@Log(title = "售货机挂载的货柜，主柜的挂载副柜", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(vendingCabinetService.deleteVendingCabinetByIds(ids.getIds()));
	}
	
}
