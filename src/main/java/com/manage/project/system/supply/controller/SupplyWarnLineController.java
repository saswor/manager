package com.manage.project.system.supply.controller;

import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.project.common.Constant;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.stock.domain.StockInfo;
import com.manage.project.system.supply.domain.SupplyConfig;
import com.manage.project.system.supply.service.ISupplyConfigService;
import com.manage.project.system.supply.vo.SupplyProductVo;
import com.manage.project.system.supply.vo.SupplyWarnVo;
import com.manage.project.system.supply.vo.VendingSupplyProductVo;
import com.manage.project.system.supply.vo.WarnLineVo;
import com.manage.project.system.supply.vo.WarnSupplyProductVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.service.IVendingLanepService;
import com.manage.project.system.vending.service.IVendingService;
import com.manage.project.system.vendingPoint.domain.VendingDistrict;
import com.manage.project.system.vendingPoint.domain.VendingLine;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询配置的线路预警信息
 * 
 * @author wusijie
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/supply/supplyWarnLine")
public class SupplyWarnLineController extends BaseController
{
    private String prefix = "supply/supplyWarnLine";
	
	@Autowired
	private ISupplyConfigService supplyConfigService;
	@Autowired
	private IVendingService vendingService;
	@Autowired
	private IVendingLanepService vendingLanepService;

	@GetMapping()
	public String supplyWarnLine()
	{
	    return prefix + "/supplyWarnLine";
	}
	
	/**
	 * 查询线路告警信息
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(SupplyConfig supplyConfig)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			supplyConfig.setCorpId("");
		} else {
			supplyConfig.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<SupplyConfig> list = supplyConfigService.selectSupplyConfigList(supplyConfig);
        TableDataInfo dataTable = getDataTable(list);
        List<WarnLineVo> warnLineVoList=new ArrayList<>();
		WarnLineVo warnLineVo=null;
		//重新封装线路告警信息列表
        for(SupplyConfig supplyConfigR:list){
			warnLineVo=new WarnLineVo();
			warnLineVo.setConfigId(supplyConfigR.getSupplyId());
			VendingLine vendingLine=SystemUtil.getVendingLineFromCache(supplyConfigR.getLineId());
			if(vendingLine!=null) {
				String vendingDistrict = SystemUtil.getVendingDistrictNameFromCache(vendingLine.getDistrictId());
				if(vendingDistrict!=null) {
					warnLineVo.setDistrictName(vendingDistrict);
				}
				warnLineVo.setLineName(vendingLine.getName());
			}
			warnLineVo.setLastSTime(supplyConfigR.getLastSTime());
			warnLineVo.setLineId(supplyConfigR.getLineId());
			warnLineVo.setStoryLevel(supplyConfigR.getStoryLevel());
			warnLineVo.setWaitSPNum(supplyConfigR.getWaitSPNum());
			warnLineVo.setWaitSVNum(supplyConfigR.getWaitSVNum());
			StockInfo StockInfo = SystemUtil.getStockInfo(supplyConfigR.getWmId());
			warnLineVo.setWmName(StockInfo.getStockName());
//			warnLineVo.setWmName(SystemUtil.getStockInfo(supplyConfigR.getWmId()).getStockName());
			int storyPercent=(int)((double)supplyConfigR.getCurPNum()/(double)supplyConfigR.getMaxPNum()*100);
			warnLineVo.setStoryPercent(storyPercent);
			warnLineVoList.add(warnLineVo);
		}
        dataTable.setRows(warnLineVoList);
		return AjaxResult.success(dataTable);
	}
	/**
	 * 查询线路告警详情
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(SupplyConfig supplyConfig)
	{
		//查询改线路配置的基本信息
		SupplyConfig supplyConfigR = supplyConfigService.selectSupplyConfigBySupplyId(supplyConfig.getSupplyId());
        //查询该线路的未删除的站点信息
		Vending vendingP=new Vending();
		vendingP.setLineId(supplyConfigR.getLineId());
		List<Vending> vendingList=vendingService.selectNeverDelVendingList(vendingP);
		//封装站点补货商品信息
		List<VendingSupplyProductVo> vendingSupplyProductVoList=new ArrayList<>();
		//保持商品汇总信息
		Map<String,String> totalSupplyProductMap=new HashMap<>();
		Map<String,String> totalProductMap=new HashMap<>();
		//查询站点商品的缺货情况
		for(Vending vending:vendingList) {
            //获取站点补货商品信息
			List<VendingLanep> vendingLanepList=null;
			//阈值补货，按商品编号分组
			if(supplyConfigR.getType().equals(Constant.supplyType_yuzhi)){
				vendingLanepList=vendingLanepService.selectSupplyPThreshold(vending.getSiteId());
			}else{//全补齐，按商品编号分组
				vendingLanepList=vendingLanepService.selectSupplyProduct(vending.getSiteId());
			}
			//分装Vo
			VendingSupplyProductVo vendingSupplyProductVo=new VendingSupplyProductVo();
			vendingSupplyProductVo.setSiteId(vending.getSiteId());
			vendingSupplyProductVo.setSiteName(vending.getSiteName());
			vendingSupplyProductVo.setPointId(vending.getPointId());
			vendingSupplyProductVo.setPointName(SystemUtil.getVendingPointNameFromCache(vending.getPointId()));
			//分装站点商品补货信息
			List<SupplyProductVo> supplyProductVoList=new ArrayList<>();
			SupplyProductVo supplyProductVo=null;
			for(VendingLanep vendingLanep:vendingLanepList){
				supplyProductVo=new SupplyProductVo();
				supplyProductVo.setProductId(vendingLanep.getProductId());
				supplyProductVo.setProductName(vendingLanep.getProductName());
				supplyProductVo.setSupplyNum(String.valueOf(vendingLanep.getCurCap()));
				supplyProductVoList.add(supplyProductVo);
                //汇总信息
				if(totalSupplyProductMap.containsKey(vendingLanep.getProductId()+"_productId")){
					totalSupplyProductMap.put(vendingLanep.getProductId() + "_productNum", String.valueOf(vendingLanep.getCurCap()+Integer.parseInt(totalSupplyProductMap.get(vendingLanep.getProductId() + "_productNum"))));
				}else {
					totalSupplyProductMap.put(vendingLanep.getProductId() + "_productId", vendingLanep.getProductId());
					totalSupplyProductMap.put(vendingLanep.getProductId() + "_productName", vendingLanep.getProductName());
					totalSupplyProductMap.put(vendingLanep.getProductId() + "_productNum", String.valueOf(vendingLanep.getCurCap()));
				}
				totalProductMap.put(vendingLanep.getProductId(),vendingLanep.getProductId());
			}
			vendingSupplyProductVo.setSupplyInfo(supplyProductVoList);
			vendingSupplyProductVoList.add(vendingSupplyProductVo);
		}
		//统计商品的缺货情况
		List<SupplyProductVo> supplyProductVoList=new ArrayList<>();
		SupplyProductVo supplyProductVo=null;
		for(String key:totalProductMap.keySet()){
			supplyProductVo=new SupplyProductVo();
			supplyProductVo.setProductId(totalSupplyProductMap.get(key+ "_productId"));
			supplyProductVo.setProductName(totalSupplyProductMap.get(key+ "_productName"));
			supplyProductVo.setSupplyNum(totalSupplyProductMap.get(key+ "_productNum"));
			supplyProductVoList.add(supplyProductVo);
		}
		WarnSupplyProductVo warnSupplyProductVo=new WarnSupplyProductVo();
		warnSupplyProductVo.setSiteInfo(vendingSupplyProductVoList);
		warnSupplyProductVo.setSumInfo(supplyProductVoList);
		return AjaxResult.success(warnSupplyProductVo);
	}
	
	/**
	 * 查询线路警告记录信息
	 */
	@GetMapping("/warnDetail")
	@ResponseBody
	public AjaxResult warnDetail(String supplyId){
		SupplyConfig supplyConfig = supplyConfigService.selectSupplyConfigBySupplyId(supplyId);
		SupplyWarnVo vo = new SupplyWarnVo();
		vo.setLineId(supplyConfig.getLineId());
		VendingLine vendingLine=SystemUtil.getVendingLineFromCache(vo.getLineId());
		if(vendingLine!=null) {
			String vendingDistrict = SystemUtil.getVendingDistrictNameFromCache(vendingLine.getDistrictId());
			if(vendingDistrict!=null) {
				vo.setDistrictName(vendingDistrict);
			}
			vo.setLineName(vendingLine.getName());
			vo.setDistrictId(vendingLine.getDistrictName());
		}
		vo.setWmId(supplyConfig.getWmId());
		StockInfo stock = SystemUtil.getStockInfo(vo.getWmId());
		if(stock!=null) {
			vo.setWmName(stock.getStockName());
		}
		vo.setWaitSPNum(supplyConfig.getWaitSPNum());
		vo.setSupplierId(supplyConfig.getSupplierId());
		User user = SystemUtil.getUserByLoginId(supplyConfig.getCorpId(),vo.getSupplierId());
		vo.setSupplierName(user.getUserName());
		vo.setLastSTime(supplyConfig.getLastSTime());
		String warnLevel = SystemUtil.parseWarnLevel(supplyConfig.getStoryLevel().toString());
		vo.setWarnLevelName(warnLevel);
		return AjaxResult.success(vo);
	}
	
}
