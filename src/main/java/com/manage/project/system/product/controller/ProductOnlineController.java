package com.manage.project.system.product.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.CacheUtils;
import com.manage.common.utils.StringUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.project.system.product.domain.ProductClassify;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.product.domain.ProductOnline;
import com.manage.project.system.product.service.IProductOnlineService;
import com.manage.project.system.product.vo.ProductOnlineVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 记录在线购买的商品 信息操作处理
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/productOnline")
public class ProductOnlineController extends BaseController{
	
	@Autowired
	private IProductOnlineService productOnlineService;
	
	/**
	 * 查询记录在线购买的商品列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(ProductOnlineVo vo)
	{
		if(SystemUtil.isZhilai()) {
			vo.setCorpId("");
		}else {
			vo.setCorpId(SystemUtil.getCorpId());
		}
		startPage();
        List<ProductOnlineVo> list = productOnlineService.selectProductOnlineList(vo);
        TableDataInfo dataTable = getDataTable(list);
        //设置包装类型名称和分类名称
        for (ProductOnlineVo productOnlineVo : list) {
        	productOnlineVo.setBagTypeName(SystemUtil.parseBagType(productOnlineVo.getBagType()));
        	String typeId = productOnlineVo.getTypeId();
        	if(StringUtils.isNotEmpty(typeId)) {
        		ProductClassify productClassify = SystemUtil.getProductClassify(productOnlineVo.getTypeId());
        		if(productClassify!=null) {
        			productOnlineVo.setTypeName(productClassify.getClassifyName());
        		}
        	}
		}
		return AjaxResult.success(dataTable);
	}

	/**
	 * 新增保存记录在线购买的商品
	 */
	@Log(title = "记录在线购买的商品", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProductOnline productOnline)
	{		
		return toAjax(productOnlineService.insertProductOnline(productOnline));
	}

	/**
	 * 修改保存记录在线购买的商品
	 */
	@RequiresPermissions("module:productOnline:edit")
	@Log(title = "记录在线购买的商品", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProductOnline productOnline)
	{		
		return toAjax(productOnlineService.updateProductOnline(productOnline));
	}
	
	/**
	 * 删除记录在线购买的商品
	 */
	@RequiresPermissions("module:productOnline:remove")
	@Log(title = "记录在线购买的商品", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(productOnlineService.deleteProductOnlineByIds(ids));
	}
	
}
