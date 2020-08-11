package com.manage.project.system.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.bean.BeanUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.Constant;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.product.domain.ProductUnder;
import com.manage.project.system.product.service.IProductInfoService;
import com.manage.project.system.product.service.IProductUnderService;
import com.manage.project.system.product.vo.UnderVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.domain.VendingStock;
import com.manage.project.system.vending.service.IVendingLanepService;
import com.manage.project.system.vending.service.IVendingStockService;
import com.manage.project.system.vending.vo.UnderProductVo;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 商品下架 信息操作处理
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/productUnder")
public class ProductUnderController extends BaseController
{
	
	@Autowired
	private IProductUnderService productUnderService;
	@Autowired
	private IProductInfoService productInfoService;
	@Autowired
	private IVendingLanepService vendingLanepService;
	@Autowired
	private IVendingStockService vendingStockService;
	
	/**
	 * 查询商品下架列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(ProductUnder productUnder)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			productUnder.setCorpId("");
		} else {
			productUnder.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<ProductUnder> list = productUnderService.selectProductUnderList(productUnder);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存商品下架
	 */
	@Log(title = "商品下架", action = BusinessType.INSERT)
	@GetMapping("/add")
	@ResponseBody
	public AjaxResult add(String logid)
	{		
		ProductInfo product = productInfoService.selectProductInfoById(logid);
		ProductUnder productUnder = new ProductUnder();
		String corpId = ShiroUtils.getCorpId();
		productUnder.setCorpId(corpId);
		productUnder.setLogid(UUID.randomUUID().toString());
		productUnder.setCreateTime(DateUtils.getTime());
		productUnder.setUnderId(SystemUtil.getSeqId(corpId, "as_product_under"));
		productUnder.setProductId(product.getProductId());
		productUnder.setName(product.getName());
		productUnder.setFullName(product.getFullName());
		productUnder.setCurState(Constant.UNDER_STATE_WAIT);
		return toAjax(productUnderService.insertProductUnder(productUnder));
	}
	
	/**
	 * 商品下架
	 */
	@Log(title = "商品下架", action = BusinessType.INSERT)
	@PostMapping("/under")
	@ResponseBody
	public AjaxResult under(@RequestBody UnderVo underVo)
	{	
		ProductInfo product = productInfoService.selectProductInfoByProductId(underVo.getProductId());
		String ids = underVo.getIds();
		String[] id = ids.split(",");
		List<VendingStock> list = vendingStockService.selectVendingStockByIds(id);
		List<ProductUnder> productUnderList = new ArrayList<ProductUnder>();
		if(list==null||list.size()==0) {
			return AjaxResult.error("请至少下架一件商品");
		}
		List<VendingLanep> totalList = new ArrayList<VendingLanep>();
		for (VendingStock vo : list) {
			//查询站点商品是否已经是等待下架状态
			ProductUnder productUnderSelect = new ProductUnder();
			productUnderSelect.setProductId(vo.getProductId());
			productUnderSelect.setSiteId(vo.getSiteId());
			productUnderSelect.setCurState("1");
			List<ProductUnder> selectProductUnderList = productUnderService.selectProductUnderList(productUnderSelect);
			if(StringUtils.isNotEmpty(selectProductUnderList)) {
				return AjaxResult.error("当前商品已经等待下架中,请不要重复下架");
			}
			if("0".equals(vo.getNum())) {
				return AjaxResult.error("当前库存为0,不允许下架");
			}
			
			/*ProductUnder productUnder = new ProductUnder();
			productUnder.setLogid(UUID.randomUUID().toString());
			String corpId = ShiroUtils.getCorpId();
			productUnder.setCorpId(corpId);
			productUnder.setUnderId(SystemUtil.getSeqId(corpId, "as_product_under"));
			productUnder.setCreateTime(DateUtils.getTime());
			productUnder.setProductId(vo.getProductId());
			productUnder.setName(product.getName());
			productUnder.setFullName(product.getFullName());
			productUnder.setCurState(Constant.UNDER_STATE_1);
			productUnder.setStateTime(DateUtils.getTime());
			productUnder.setPlanTime(DateUtils.getTime());
			//设置下架类型立即下架
			productUnder.setUnderType("1");
			Vending vending = SystemUtil.getVendingBase(vo.getSiteId());
			productUnder.setDistrictId(vending.getDistrictId());
			productUnder.setLineId(vending.getLineId());
			productUnder.setSiteId(vo.getSiteId());
			productUnder.setSiteName(vo.getSiteName());
			productUnder.setPointId(vending.getPointId());
			productUnderList.add(productUnder);*/
			VendingLanep vendingLanep = new VendingLanep();
			vendingLanep.setSiteId(vo.getSiteId());
			vendingLanep.setProductId(vo.getProductId());
			List<VendingLanep> vendingLanepList = vendingLanepService.selectVendingLanepList(vendingLanep);
			totalList.addAll(vendingLanepList);
		}
		try {
			int result = productUnderService.insertProductUnderBatch(totalList);
			switch (result) {
				case 1:
					return AjaxResult.success();
				case 2:
					return AjaxResult.error("当前站点不存在");
				case 3:
					return AjaxResult.error("当前商品不存在");
				case 4:
					return AjaxResult.error("当前线路没有补货配置或补货员不存在");
				case 5:
					return AjaxResult.error("当前线路没有补货配置或补货员不存在");
				default:
					return AjaxResult.error("下架失败");
			}
		}catch (Exception e) {
			return AjaxResult.error("下架失败");
		}
	}

	/**
	 * 修改保存商品下架
	 */
	@Log(title = "商品下架", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody ProductUnder productUnder)
	{		
		return toAjax(productUnderService.updateProductUnder(productUnder));
	}
	
	/**
	 * 删除商品下架
	 */
	@Log(title = "商品下架", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(productUnderService.deleteProductUnderByIds(ids.getIds()));
	}
	
}
