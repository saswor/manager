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
import com.manage.common.utils.bean.BeanUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.advert.domain.FavourableConfig;
import com.manage.project.system.advert.domain.FavourableObject;
import com.manage.project.system.advert.domain.FavourableTime;
import com.manage.project.system.advert.service.IFavourableConfigService;
import com.manage.project.system.advert.service.IFavourableObjectService;
import com.manage.project.system.advert.service.IFavourableTimeService;
import com.manage.project.system.advert.vo.FavourableConfigTjVo;
import com.manage.project.system.advert.vo.FavourableEditVo;
import com.manage.project.system.advert.vo.FavourableSaveVo;
import com.manage.project.system.advert.vo.FavourableViewVo;
import com.manage.project.system.advert.vo.FavourableViewVo.Ftime;
import com.manage.project.system.advert.vo.FobjEditVo;
import com.manage.project.system.advert.vo.FtimeEditVo;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/favourableConfig")
public class FavourableConfigController extends BaseController
{
	@Autowired
	private IFavourableConfigService favourableConfigService;
	
	@Autowired
	private IFavourableTimeService favourableTimeService;
	
	@Autowired
	private IFavourableObjectService favourableObjectService;

	/**
	 * 查询商品优惠促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(FavourableConfig favourableConfig)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			favourableConfig.setCorpId("");
		} else {
			favourableConfig.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		favourableConfig.setCorpId(SystemUtil.getCorpId());
        List<FavourableConfig> list = favourableConfigService.selectFavourableConfigList(favourableConfig);
		return AjaxResult.success(getDataTable(list));
	}
	
	@GetMapping("/getTj")
	@ResponseBody
	public AjaxResult getTj()
	{
		String corpId = SystemUtil.getCorpId();
		FavourableConfigTjVo tj = favourableConfigService.selectFavourableConfigTj(corpId);
		return AjaxResult.success(tj);
	}
	
	/**
	 * 查看折扣
	 * @param favourableId
	 * @return
	 */
	@GetMapping("/get")
	@ResponseBody
	public AjaxResult get(String favourableId)
	{
		FavourableViewVo favourableViewVo = new FavourableViewVo();
		// 设置优惠基础信息
		FavourableConfig favourableConfig = favourableConfigService.selectFavourableConfigById(favourableId);
		favourableViewVo.setFavTypeName(SystemUtil.parseFavType(favourableConfig.getFavType()));
		favourableViewVo.setName(favourableConfig.getName());
		favourableViewVo.setPayTypeName(SystemUtil.parsePayType(favourableConfig.getPayType()));
		favourableViewVo.setValidSTime(favourableConfig.getValidSTime());
		favourableViewVo.setValidETime(favourableConfig.getValidETime());
		// 设置优惠时段
		FavourableTime favourableTime = new FavourableTime();
		favourableTime.setFavourableId(favourableId);
		List<FavourableTime> ftimes = favourableTimeService.selectFavourableTimeList(favourableTime);
		List<Ftime> fts = new ArrayList<Ftime>();;
		if (ftimes != null) {
			for (FavourableTime ftime : ftimes) {
				Ftime ft = favourableViewVo.new Ftime();
				ft.setDiscount(ftime.getDiscount());
				ft.setFavETime(ftime.getFavETime());
				ft.setFavSTime(ftime.getFavSTime());
				fts.add(ft);
			}
		}
		favourableViewVo.setFtimes(fts);
		return AjaxResult.success(favourableViewVo);
	}
	
	/**
	 * 新增保存商品优惠促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。
	 */
	@Log(title = "商品优惠促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody FavourableSaveVo favourableSaveVo)
	{		
		try {
			return toAjax(favourableConfigService.insertFavourableConfig(favourableSaveVo));
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("保存失败");
		}
	}
	
	/**
	 * 修改保存商品优惠促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。
	 */
	@Log(title = "商品优惠促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody FavourableSaveVo favourableSaveVo)
	{		
		return toAjax(favourableConfigService.updateFavourableConfig(favourableSaveVo));
	}
	
	/**
	 * 删除商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。
	 */
	@Log(title = "商品优惠促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{	
		int result = favourableConfigService.deleteFavourableConfigByIds(ids.getIds());
		if(result==1) {
			return AjaxResult.success();
		}else if(result==2) {
			return AjaxResult.error("请不要重复删除");
		}else {
			return AjaxResult.error("删除失败");
		}
	}
	
	/**
	 * 编辑页面,查询折扣信息
	 * @param favourableId	折扣id
	 * @param favTarget 优惠对象 1:整机 2:单品
	 * @return
	 */
	@GetMapping("/getEditFav")
	@ResponseBody
	public AjaxResult getEditFav(String favourableId, String favTarget) {
		if (StringUtils.isEmpty(favourableId)) {
			return AjaxResult.error("favourableId is null.");
		}
		if (StringUtils.isEmpty(favTarget)) {
			return AjaxResult.error("favTarget is null.");
		}
		FavourableEditVo result = new FavourableEditVo();
		// 查询折扣基础信息
		FavourableConfig favourableConfig = favourableConfigService.selectFavourableConfigById(favourableId);
		if (favourableConfig == null) {
			return AjaxResult.error("favourableId select data is null.");
		}
		BeanUtils.copyBeanProp(result, favourableConfig);
		
		// 查询折扣时段
		FavourableTime favourableTime = new FavourableTime();
		favourableTime.setFavourableId(favourableId);
		List<FavourableTime> favourableTimes = favourableTimeService.selectFavourableTimeList(favourableTime);
		if (favourableTimes != null) {
			List<FtimeEditVo> times = new ArrayList<FtimeEditVo>();
			for (FavourableTime time : favourableTimes) {
				FtimeEditVo ftimeEditVo = new FtimeEditVo();
				BeanUtils.copyBeanProp(ftimeEditVo, time);
				times.add(ftimeEditVo);
			}
			result.setFtimes(times);
		}
		// 查询优惠设备
		FavourableObject favourableObject = new FavourableObject();
		favourableObject.setFavourableId(favourableId);;
		List<FavourableObject> objs = favourableObjectService.selectFavourableObjectList(favourableObject);
		if (objs != null) {
			List<FobjEditVo> fobjects = new ArrayList<FobjEditVo>();
			for (FavourableObject obj : objs) {
				FobjEditVo fe = new FobjEditVo();
				fe.setFavObjId(obj.getFavObjId());
				if (favTarget.equals("1")) {	// 优惠对象为售卖机，查询售卖机信息
					Vending v = SystemUtil.getVendingBase(obj.getFavObjId());
					if (v != null) {
						fe.setFavObjName(v.getSiteName());
					}
				} else if (favTarget.equals("2")) {	// 优惠对象为商品，查询商品信息
					ProductInfo product = SystemUtil.getProductById(obj.getFavObjId());
					if (product != null) {
						fe.setFavObjName(product.getName());
						fe.setPic(product.getPic());
					}
				}
				fobjects.add(fe);
			}
			result.setFobjects(fobjects);
		}
		
		
		return AjaxResult.success(result);
	}
}
