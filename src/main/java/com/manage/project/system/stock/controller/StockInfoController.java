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
import com.manage.project.system.stock.domain.StockInfo;
import com.manage.project.system.stock.domain.StockWarehouse;
import com.manage.project.system.stock.service.IStockInboundService;
import com.manage.project.system.stock.service.IStockInfoService;
import com.manage.project.system.stock.service.IStockPurchaseService;
import com.manage.project.system.stock.service.IStockWarehouseService;
import com.manage.project.system.supply.service.ISupplyOrderService;
import com.manage.project.system.util.SystemUtil;

import net.sf.ehcache.distribution.RMICacheReplicatorFactory;

import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 仓库记录 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/stockInfo")
public class StockInfoController extends BaseController
{
	@Autowired
	private IStockInfoService stockInfoService;
	
	@Autowired
	private IStockWarehouseService stockWarehouseService;
	
	@Autowired
	private IStockPurchaseService stockPurchaseService;
	
	@Autowired
	private IStockInboundService stockInboundService;
	
	@Autowired
	private ISupplyOrderService supplyOrderService;
	
	/**
	 * 查询仓库记录列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(StockInfo stockInfo)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			stockInfo.setCorpId("");
		} else {
			stockInfo.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		stockInfo.setCorpId(SystemUtil.getCorpId());
        List<StockInfo> list = stockInfoService.selectStockInfoList(stockInfo);
        /*for (StockInfo stockInfoShow : list) {
        	String stockId = stockInfoShow.getStockId();
        	stockInfoShow.setStockId(stockId.split("_")[0]);
		}*/
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存仓库
	 */
	@Log(title = "新增保存仓库", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody StockInfo stockInfo)
	{		
		return toAjax(stockInfoService.insertStockInfo(stockInfo));
	}
	
	/**
	 * 修改保存仓库
	 */
	@Log(title = "仓库入库记录", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody StockInfo stockInfo)
	{		
		return toAjax(stockInfoService.updateStockInfo(stockInfo));
	}
	
	/**
	 * 删除仓库
	 */
	@Log(title = "删除仓库", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{	
		try {
			int result = stockInfoService.deleteStockInfoByIds(ids.getIds());
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("当前仓库中有商品存在");
			}else if(result==3) {
				return AjaxResult.error("当前仓库有未完成的采购单");
			}else if(result==4) {
				return AjaxResult.error("当前仓库有未完成的入库单");
			}else if(result==5) {
				return AjaxResult.error("当前仓库有未完成的补货单");
			}else {
				return AjaxResult.error("删除失败");
			}
		}catch (Exception e) {
			return AjaxResult.error("删除失败");
		}
		
	}
	
}
