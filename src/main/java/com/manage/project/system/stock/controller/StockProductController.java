package com.manage.project.system.stock.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.stock.domain.StockProduct;
import com.manage.project.system.stock.service.IStockProductService;
import com.manage.project.system.supply.vo.SupplyProductVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 商品库存量 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/stockProduct")
public class StockProductController extends BaseController
{
	@Autowired
	private IStockProductService stockProductService;
	
	/**
	 * 查询商品库存量列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(StockProduct stockProduct)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			stockProduct.setCorpId("");
		} else {
			stockProduct.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		// 设置租户id,宇宙星空查询所有
		stockProduct.setCorpId(SystemUtil.getCorpId());
        List<StockProduct> list = stockProductService.selectStockProductList(stockProduct);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存商品库存量
	 */
	@Log(title = "商品库存量", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody StockProduct stockProduct)
	{		
		return toAjax(stockProductService.insertStockProduct(stockProduct));
	}
	
	/**
	 * 修改保存商品库存量
	 */
	@Log(title = "商品库存量", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody StockProduct stockProduct)
	{		
		return toAjax(stockProductService.updateStockProduct(stockProduct));
	}
	
	/**
	 * 删除商品库存量
	 */
	@RequiresPermissions("module:stockProduct:remove")
	@Log(title = "商品库存量", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(stockProductService.deleteStockProductByIds(ids.getIds()));
	}
	
	/**
	 * 导出商品库存
	 */
	@Log(title = "导出商品库存", action = BusinessType.EXPORT)
	@PostMapping( "/exportExcel")
	@ResponseBody
	public AjaxResult exportExcel(@RequestBody StockProduct stockProduct) {

		try {

			ExcelUtil<StockProduct> util = new ExcelUtil<StockProduct>(StockProduct.class);
			stockProduct.setCorpId(SystemUtil.getCorpId());
	        List<StockProduct> list = stockProductService.selectStockProductList(stockProduct);
            return util.exportExcel(list, "Sheet1");
			
		} catch (Exception e) {
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
	
}
