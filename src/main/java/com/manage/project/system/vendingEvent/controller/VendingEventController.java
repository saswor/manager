package com.manage.project.system.vendingEvent.controller;

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
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vendingEvent.domain.VendingEvent;
import com.manage.project.system.vendingEvent.service.IVendingEventService;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 售货机的事件列 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/vendingEvent")
public class VendingEventController extends BaseController
{
	@Autowired
	private IVendingEventService vendingEventService;
	
	/**
	 * 查询售货机的事件列列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingEvent vendingEvent)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			vendingEvent.setCorpId("");
		} else {
			vendingEvent.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		if("00".equals(vendingEvent.getEventType())) {
			vendingEvent.setEventType("");
		}
        List<VendingEvent> list = vendingEventService.selectVendingEventList(vendingEvent);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存售货机的事件列
	 */
	@Log(title = "售货机的事件列", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody VendingEvent vendingEvent)
	{		
		return toAjax(vendingEventService.insertVendingEvent(vendingEvent));
	}
	
	/**
	 * 修改保存售货机的事件列
	 */
	@Log(title = "售货机的事件列", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody VendingEvent vendingEvent)
	{		
		return toAjax(vendingEventService.updateVendingEvent(vendingEvent));
	}
	
	/**
	 * 删除售货机的事件列
	 */
	@Log(title = "售货机的事件列", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody  CommonVo ids)
	{		
		return toAjax(vendingEventService.deleteVendingEventByIds(ids.getIds()));
	}
	
}
