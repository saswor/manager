package com.manage.project.system.product.controller;

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
import com.manage.project.system.product.domain.ProductClassify;
import com.manage.project.system.product.service.IProductClassifyService;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 商品分类 信息操作处理
 * 
 * @author shicong
 * @date 2018-09-25
 */
@Controller
@RequestMapping("/system/productClassify")
public class ProductClassifyController extends BaseController
{
	@Autowired
	private IProductClassifyService productClassifyService;
	
	/**
	 * 查询商品分类列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(ProductClassify productClassify)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			productClassify.setCorpId("");
		} else {
			productClassify.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<ProductClassify> list = productClassifyService.selectProductClassifyList(productClassify);
        return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存商品分类
	 */
	@Log(title = "商品分类", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody ProductClassify productClassify)
	{		
		return toAjax(productClassifyService.insertProductClassify(productClassify));
	}

	
	/**
	 * 修改保存商品分类
	 */
	@Log(title = "商品分类", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody ProductClassify productClassify)
	{		
		return toAjax(productClassifyService.updateProductClassify(productClassify));
	}
	
	/**
	 * 删除商品分类
	 */
	@Log(title = "商品分类", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(productClassifyService.deleteProductClassifyByIds(ids.getIds()));
	}
	
	/**
	 * 模糊查询商品分类列表
	 */
	@GetMapping("/vaguelist")
	@ResponseBody
	public AjaxResult vaguelist(String classifyName)
	{
        List<ProductClassify> list = productClassifyService.selectProductClassifyByName(classifyName);
		return AjaxResult.success(list);
	}
}
