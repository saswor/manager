package com.manage.project.system.vending.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingCmd;
import com.manage.project.system.vending.service.IVendingCmdService;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 售货机命令，按站点队列执行 信息操作处理
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/vendingCmd")
public class VendingCmdController extends BaseController
{
	
	@Autowired
	private IVendingCmdService vendingCmdService;
	
	private Logger log = LoggerFactory.getLogger(VendingCmdController.class);
	
	/**
	 * 查询售货机命令，按站点队列执行列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingCmd vendingCmd)
	{
		startPage();
		vendingCmd.setCorpId(SystemUtil.getCorpId());
        List<VendingCmd> list = vendingCmdService.selectVendingCmdList(vendingCmd);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存售货机命令，按站点队列执行
	 */
	@Log(title = "售货机命令，按站点队列执行", action = BusinessType.INSERT)
	@PostMapping("/remoteController")
	@ResponseBody
	public AjaxResult remoteController(@RequestBody VendingCmd vendingCmd)
	{	
		try {
			int result = vendingCmdService.insertVendingCmdQuick(vendingCmd.getCmdCode(),vendingCmd,ShiroUtils.getCorpId());
			if(result==1) {
				return AjaxResult.success("命令下发成功");
			}else {
				return AjaxResult.error("命令下发失败");
			}
		}catch (Exception e) {
			log.error("命令下发失败,售货机:"+vendingCmd.getCmdCode()+",命令:"+vendingCmd.getCmd()+",时间:"+DateUtils.getTime(),e);
			return AjaxResult.error("命令下发失败");
		}
		
	}
	
	/**
	 * 新增保存售货机命令，按站点队列执行
	 */
	@Log(title = "售货机命令，按站点队列执行", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody VendingCmd vendingCmd)
	{		
		return toAjax(vendingCmdService.insertVendingCmd(vendingCmd));
	}
	
	/**
	 * 修改保存售货机命令，按站点队列执行
	 */
	@Log(title = "售货机命令，按站点队列执行", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody VendingCmd vendingCmd)
	{		
		return toAjax(vendingCmdService.updateVendingCmd(vendingCmd));
	}
	
	/**
	 * 删除售货机命令，按站点队列执行
	 */
	@Log(title = "售货机命令，按站点队列执行", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(vendingCmdService.deleteVendingCmdByIds(ids.getIds()));
	}
	
}
