package com.manage.project.system.advert.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.manage.project.system.advert.domain.FavourableTime;
import com.manage.project.system.advert.service.IFavourableTimeService;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/favourableTime")
public class FavourableTimeController extends BaseController
{
	@Autowired
	private IFavourableTimeService favourableTimeService;
	
	/**
	 * 查询优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(FavourableTime favourableTime)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			favourableTime.setCorpId("");
		} else {
			favourableTime.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<FavourableTime> list = favourableTimeService.selectFavourableTimeList(favourableTime);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。
	 */
	@Log(title = "优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(FavourableTime favourableTime)
	{		
		return toAjax(favourableTimeService.insertFavourableTime(favourableTime));
	}
	
	/**
	 * 修改保存优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。
	 */
	@Log(title = "优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(FavourableTime favourableTime)
	{		
		return toAjax(favourableTimeService.updateFavourableTime(favourableTime));
	}
	
	/**
	 * 删除优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。
	 */
	@RequiresPermissions("module:favourableTime:remove")
	@Log(title = "优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(favourableTimeService.deleteFavourableTimeByIds(ids.getIds()));
	}
	
}
