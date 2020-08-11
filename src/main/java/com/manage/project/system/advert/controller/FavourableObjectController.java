package com.manage.project.system.advert.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.advert.domain.FavourableObject;
import com.manage.project.system.advert.service.IFavourableObjectService;
import com.manage.project.system.advert.vo.ProductFavViewVo;
import com.manage.project.system.advert.vo.TfAdventVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/favourableObject")
public class FavourableObjectController extends BaseController
{
	
	@Autowired
	private IFavourableObjectService favourableObjectService;
	
	/**
	 * 查询区域或线路内使用该优惠的售卖机id,查看折扣页面使用
	 * @param favourableId
	 * @param districtId
	 * @param lineId
	 * @param favObjId
	 * @return
	 */
	@GetMapping("/selectFavUseList")
	@ResponseBody
	public AjaxResult selectFavUseList(String favourableId, String districtId, String lineId, 
			String favObjId)
	{
		String corpId = SystemUtil.getCorpId();
		List<FavourableObject> list = favourableObjectService.selectFavourableSiteList(favourableId, districtId, lineId, favObjId, corpId);
		
		List<TfAdventVo> result = new ArrayList<TfAdventVo>();
        if (list != null && !list.isEmpty()) {
        	for (FavourableObject ad : list) {
        		TfAdventVo tfAdventVo = new TfAdventVo();
        		Vending vending = SystemUtil.getVendingBase(ad.getFavObjId());
        		if (vending != null) {
        			VendingLine line = SystemUtil.getVendingLineFromCache(vending.getLineId());
        			if (line != null) {
        				tfAdventVo.setLineName(line.getName());
        			}
        			tfAdventVo.setNetSateName(vending.getNetSateName());
        			tfAdventVo.setSellStateName(vending.getSellStateName());
        			tfAdventVo.setSiteId(ad.getFavObjId());
        			tfAdventVo.setSiteName(vending.getSiteName());
        		} 
        		result.add(tfAdventVo);
        	}
        }
		
		return AjaxResult.success(result);
	}
	
	
	/**
	 * 查询某一折扣所拥有的商品
	 * @param favourableId	折扣id
	 * @param name	商品名称或商品id
	 * @return
	 */
	@GetMapping("/selectFavProductList")
	@ResponseBody
	public AjaxResult selectFavProductList(String favourableId, String name)
	{
		if (StringUtils.isEmpty(favourableId)) {
			return AjaxResult.error("param favourableId is null");
		}
		List<ProductFavViewVo> list = favourableObjectService.selectFavProductList(favourableId, name);//.selectFavourableSiteList(favourableId, name, corpId);
		
		return AjaxResult.success(list);
	}
	
	/**
	 * 查询优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(FavourableObject favourableObject)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			favourableObject.setCorpId("");
		} else {
			favourableObject.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<FavourableObject> list = favourableObjectService.selectFavourableObjectList(favourableObject);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠
	 */
	@Log(title = "优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody FavourableObject favourableObject)
	{		
		return toAjax(favourableObjectService.insertFavourableObject(favourableObject));
	}
	
	/**
	 * 修改保存优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠
	 */
	@Log(title = "优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody FavourableObject favourableObject)
	{		
		return toAjax(favourableObjectService.updateFavourableObject(favourableObject));
	}
	
	/**
	 * 删除优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠
	 */
	@Log(title = "优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(favourableObjectService.deleteFavourableObjectByIds(ids.getIds()));
	}
	
}
