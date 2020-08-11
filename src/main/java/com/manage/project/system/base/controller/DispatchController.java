package com.manage.project.system.base.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.base.domain.Dispatch;
import com.manage.project.system.base.service.IDispatchService;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 区域目录树管理，每个公司拥有自己的区域。 信息操作处理
 * 
 * @author xufeng
 */
@Controller
@RequestMapping("/system/dispatch")
public class DispatchController extends BaseController
{
	@Autowired
	private IDispatchService dispatchService;
	
	/**
	 * 查询区域目录树管理，每个公司拥有自己的区域。列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(Dispatch dispatch)
	{
		startPage();
        List<Dispatch> list = dispatchService.selectDispatchList(dispatch);
        return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 查询区域目录树管理，每个公司拥有自己的区域。列表
	 */
	@GetMapping("/getChild")
	@ResponseBody
	public AjaxResult getChild(String parentId)
	{
        List<Dispatch> list = dispatchService.selectDispatchByParentId(parentId);
        return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存区域目录树管理，每个公司拥有自己的区域。
	 */
	@Log(title = "区域目录树管理，每个公司拥有自己的区域。", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Dispatch dispatch)
	{		
		return toAjax(dispatchService.insertDispatch(dispatch));
	}
	
	/**
	 * 修改保存区域目录树管理，每个公司拥有自己的区域。
	 */
	@Log(title = "区域目录树管理，每个公司拥有自己的区域。", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Dispatch dispatch)
	{		
		return toAjax(dispatchService.updateDispatch(dispatch));
	}
	
	/**
	 * 删除区域目录树管理，每个公司拥有自己的区域。
	 */
	@Log(title = "区域目录树管理，每个公司拥有自己的区域。", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(dispatchService.deleteDispatchByIds(ids.getIds()));
	}
	
}
