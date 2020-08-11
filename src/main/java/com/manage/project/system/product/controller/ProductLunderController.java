package com.manage.project.system.product.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.project.system.product.domain.ProductLunder;
import com.manage.project.system.product.domain.ProductUnder;
import com.manage.project.system.product.service.IProductLunderService;
import com.manage.project.system.product.vo.ProductLunderVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vendingPoint.domain.VendingDistrict;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 站点货道商品下架 信息操作处理
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/productLunder")
public class ProductLunderController extends BaseController{
	
	@Autowired
	private IProductLunderService productLunderService;
	
	private Logger log = LoggerFactory.getLogger(ProductLunderController.class);

	/**
	 * 查询站点货道商品下架列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(ProductLunderVo vo){
		if(SystemUtil.isZhilai()) {
			vo.setCorpId("");
		}else {
			vo.setCorpId(SystemUtil.getCorpId());
		}
		startPage();
		vo.setCurState("2");
		List<ProductLunderVo> list = productLunderService.selectProductLunderVoList(vo);
		TableDataInfo dataTable = getDataTable(list);
		for (ProductLunderVo productLunderVo : list) {
			VendingDistrict district = SystemUtil.getVendingDistrictFromCache(productLunderVo.getDistrictId());
			if(district!=null) {
				productLunderVo.setDistrictCode(district.getCode());
			}
			VendingLine line = SystemUtil.getVendingLineFromCache(productLunderVo.getLineId());
			if(line!=null) {
				productLunderVo.setLineCode(line.getCode());
			}
		}
		return AjaxResult.success(dataTable);
	}

	/**
	 * 新增保存站点货道商品下架
	 */
	@Log(title = "站点货道商品下架", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProductLunder productLunder)
	{		
		return toAjax(productLunderService.insertProductLunder(productLunder));
	}
	
	/**
	 * 修改保存站点货道商品下架
	 */
	@Log(title = "站点货道商品下架", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProductLunder productLunder)
	{		
		return toAjax(productLunderService.updateProductLunder(productLunder));
	}
	
	/**
	 * 删除站点货道商品下架
	 */
	@Log(title = "站点货道商品下架", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(productLunderService.deleteProductLunderByIds(ids));
	}
	
	/**
	 * 查询商品下架列表
	 */
	@Log(title = "站点货道商品下架导出", action = BusinessType.EXPORT)
	@PostMapping("/exportExcel")
	@ResponseBody
	public AjaxResult exportExcel(@RequestBody ProductLunderVo vo)
	{
		if(SystemUtil.isZhilai()) {
			vo.setCorpId("");
		}else {
			vo.setCorpId(SystemUtil.getCorpId());
		}
		vo.setCurState("2");
		try {
			List<ProductLunderVo> list = productLunderService.selectProductLunderVoList(vo);
			for (ProductLunderVo productLunderVo : list) {
				VendingDistrict district = SystemUtil.getVendingDistrictFromCache(productLunderVo.getDistrictId());
				if(district!=null) {
					productLunderVo.setDistrictCode(district.getCode());
					productLunderVo.setDistrictName(district.getName());
				}
				VendingLine line = SystemUtil.getVendingLineFromCache(productLunderVo.getLineId());
				if(line!=null) {
					productLunderVo.setLineCode(line.getCode());
					productLunderVo.setLineName(line.getName());
				}
			}
			ExcelUtil<ProductLunderVo> excelUtil = new ExcelUtil<ProductLunderVo>(ProductLunderVo.class);
			return excelUtil.exportExcel(list, "下架记录");
		}catch (Exception e) {
			log.error("导出下架列表失败,时间:"+DateUtils.getTime(),e);
			return AjaxResult.error("导出下架记录失败,请联系管理员");
		}
		

	}
}
