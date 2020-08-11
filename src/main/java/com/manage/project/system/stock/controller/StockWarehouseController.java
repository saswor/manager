package com.manage.project.system.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.stock.domain.StockProduct;
import com.manage.project.system.stock.domain.StockWarehouse;
import com.manage.project.system.stock.service.IStockWarehouseService;
import com.manage.project.system.util.SystemUtil;

/**
 * 仓库商品库存存量 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/stockWarehouse")
public class StockWarehouseController extends BaseController
{
	@Autowired
	private IStockWarehouseService stockWarehouseService;
	
	@GetMapping("/listSurvey")
	@ResponseBody
	public AjaxResult listSurvey(StockWarehouse stockWarehouse)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			stockWarehouse.setCorpId("");
		} else {
			stockWarehouse.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		stockWarehouse.setCorpId(SystemUtil.getCorpId());
        List<StockWarehouse> list = stockWarehouseService.selectStockWarehouse(stockWarehouse);
		return AjaxResult.success(getDataTable(list));
	}
	
	@GetMapping("/getAllByStockId")
	@ResponseBody
	public AjaxResult getAllByStockId(StockWarehouse stockWarehouse)
	{
		stockWarehouse.setCorpId(SystemUtil.getCorpId());
        List<StockWarehouse> list = stockWarehouseService.selectStockWarehouse(stockWarehouse);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 查询仓库商品库存存量列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(StockWarehouse stockWarehouse)
	{
		startPage();
		stockWarehouse.setCorpId(SystemUtil.getCorpId());
        List<StockWarehouse> list = stockWarehouseService.selectStockWarehouseList(stockWarehouse);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存仓库商品库存存量
	 */
	@Log(title = "仓库商品库存存量", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody StockWarehouse stockWarehouse)
	{		
		return toAjax(stockWarehouseService.insertStockWarehouse(stockWarehouse));
	}
	
	/**
	 * 修改保存仓库商品库存存量
	 */
	@Log(title = "仓库商品库存存量", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody StockWarehouse stockWarehouse)
	{		
		return toAjax(stockWarehouseService.updateStockWarehouse(stockWarehouse));
	}
	
	/**
	 * 删除仓库商品库存存量
	 */
	@Log(title = "仓库商品库存存量", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(stockWarehouseService.deleteStockWarehouseByIds(ids.getIds()));
	}
	
	/**
	 * 导出商品库存
	 */
	@Log(title = "导出商品库存", action = BusinessType.EXPORT)
	@PostMapping( "/exportExcel")
	@ResponseBody
	public AjaxResult exportExcel(@RequestBody StockWarehouse stockWarehouse) {

		try {

			ExcelUtil<StockWarehouse> util = new ExcelUtil<StockWarehouse>(StockWarehouse.class);
			stockWarehouse.setCorpId(SystemUtil.getCorpId());
	        List<StockWarehouse> list = stockWarehouseService.selectStockWarehouseList(stockWarehouse);
            return util.exportExcel(list, "仓库商品库存");
			
		} catch (Exception e) {
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
	
}
