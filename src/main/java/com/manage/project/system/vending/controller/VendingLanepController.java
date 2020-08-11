package com.manage.project.system.vending.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.CacheUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.Constant;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.base.domain.Dispatch;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.domain.VendingStock;
import com.manage.project.system.vending.service.IVendingLanepService;
import com.manage.project.system.vending.service.IVendingStockService;
import com.manage.project.system.vending.vo.UnderProductVo;
import com.manage.project.system.vendingPoint.domain.VendingDistrict;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import com.manage.project.system.vendingPoint.domain.VendingPoint;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.framework.web.page.TableDataInfo;

/**
 * 售货机货道商品 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/vendingLanep")
public class VendingLanepController extends BaseController
{
	@Autowired
	private IVendingLanepService vendingLanepService;
	
	@Autowired
	private IVendingStockService vendingStockService;
	
	/**
	 * 查询售货机货道商品列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingLanep vendingLanep)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			vendingLanep.setCorpId("");
		} else {
			vendingLanep.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<VendingLanep> list = vendingLanepService.selectVendingLanepList(vendingLanep);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 查询售货机商品列表(下架)
	 */
	@GetMapping("/selectUnderProductSite")
	@ResponseBody
	public AjaxResult selectUnderProductSite(UnderProductVo vo)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			vo.setCorpId("");
		} else {
			vo.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		List<VendingStock> list = vendingStockService.selectOnlineVendingStockList(vo);
        TableDataInfo dataTable = getDataTable(list);
        List<UnderProductVo> resultList = new ArrayList<UnderProductVo>();
        for (VendingStock vs : list) {
			UnderProductVo underProductVo = new UnderProductVo();
			underProductVo.setLogid(vs.getLogid());
			String siteId = vs.getSiteId();
			underProductVo.setSiteId(siteId);
			underProductVo.setSiteName(vs.getSiteName());
			Vending vending = SystemUtil.getVendingBase(siteId);
			if(vending!=null) {
				underProductVo.setDistrictName(SystemUtil.getVendingDistrictNameFromCache(vending.getDistrictId()));
				underProductVo.setLineName(SystemUtil.getVendingLineNameFromCache(vending.getLineId()));
				VendingPoint point = SystemUtil.getVendingPointFromCache(vending.getPointId());
				if(point!=null) {
					Map<String, Dispatch> map = (Map<String, Dispatch>)CacheUtils.get(Constant.DISPATCH_CACHE);
        			Dispatch dispatch = map.get(point.getDistrict());
        			underProductVo.setDispatch(dispatch.getNamepath());
				}
			}
			underProductVo.setCreateTime(vs.getCreateTime());
			underProductVo.setCurCap(vs.getNum());
			resultList.add(underProductVo);
		}
        dataTable.setRows(resultList);
		return AjaxResult.success(dataTable);
	}
	
	/**
	 * 根据货柜id查询该货柜货道及商品
	 * @param cabinetId	货柜id
	 * @return	货道商品列表
	 */
	@GetMapping("/selectVendingLanepByCabinetId")
	@ResponseBody
	public AjaxResult selectVendingLanepByCabinetId(String cabinetId)
	{
        List<VendingLanep> list = vendingLanepService.selectVendingLanepByCabinetId(cabinetId);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存售货机货道商品
	 */
	@Log(title = "售货机货道商品", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(VendingLanep vendingLanep)
	{		
		return toAjax(vendingLanepService.insertVendingLanep(vendingLanep));
	}
	
	/**
	 * 修改保存售货机货道商品
	 */
	@Log(title = "售货机货道商品", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(VendingLanep vendingLanep)
	{		
		return toAjax(vendingLanepService.updateVendingLanep(vendingLanep));
	}
	
	/**
	 * 删除售货机货道商品
	 */
	@Log(title = "售货机货道商品", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(vendingLanepService.deleteVendingLanepByIds(ids.getIds()));
	}
	
}
