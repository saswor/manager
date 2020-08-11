package com.manage.project.system.stock.controller;

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
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.stock.domain.StockPpurchase;
import com.manage.project.system.stock.service.IStockPpurchaseService;
import com.manage.project.system.util.SystemUtil;

/**
 * 仓库采购记录 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/stockPpurchase")
public class StockPpurchaseController extends BaseController
{
	@Autowired
	private IStockPpurchaseService stockPpurchaseService;
	
	/**
	 * 查询仓库采购记录列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(StockPpurchase stockPpurchase)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			stockPpurchase.setCorpId("");
		} else {
			stockPpurchase.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<StockPpurchase> list = stockPpurchaseService.selectStockPpurchaseList(stockPpurchase);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存仓库采购记录
	 */
	@Log(title = "仓库采购记录", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody StockPpurchase stockPpurchase)
	{		
		return toAjax(stockPpurchaseService.insertStockPpurchase(stockPpurchase));
	}
	
	/**
	 * 修改保存仓库采购记录
	 */
	@Log(title = "仓库采购记录", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody StockPpurchase stockPpurchase)
	{		
		return toAjax(stockPpurchaseService.updateStockPpurchase(stockPpurchase));
	}
	
	/**
	 * 删除仓库采购记录
	 */
	@Log(title = "仓库采购记录", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(stockPpurchaseService.deleteStockPpurchaseByIds(ids.getIds()));
	}
	
}
