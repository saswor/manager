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
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.stock.domain.StockPinbound;
import com.manage.project.system.stock.service.IStockPinboundService;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 仓库采购记录 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/stockPinbound")
public class StockPinboundController extends BaseController
{
	@Autowired
	private IStockPinboundService stockPinboundService;
	
	/**
	 * 查询仓库采购记录列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(StockPinbound stockPinbound)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			stockPinbound.setCorpId("");
		} else {
			stockPinbound.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<StockPinbound> list = stockPinboundService.selectStockPinboundList(stockPinbound);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存仓库采购记录
	 */
	@Log(title = "仓库采购记录", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody StockPinbound stockPinbound)
	{		
		return toAjax(stockPinboundService.insertStockPinbound(stockPinbound));
	}

	/**
	 * 修改保存仓库采购记录
	 */
	@Log(title = "仓库采购记录", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody StockPinbound stockPinbound)
	{		
		return toAjax(stockPinboundService.updateStockPinbound(stockPinbound));
	}
	
	/**
	 * 删除仓库采购记录
	 */
	@Log(title = "仓库采购记录", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(stockPinboundService.deleteStockPinboundByIds(ids.getIds()));
	}
	
}
