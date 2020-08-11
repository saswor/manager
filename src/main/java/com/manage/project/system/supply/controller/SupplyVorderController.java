package com.manage.project.system.supply.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manage.project.system.base.domain.User;
import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.domain.SupplyProduct;
import com.manage.project.system.supply.domain.SupplyVorder;
import com.manage.project.system.supply.domain.SupplyVproduct;
import com.manage.project.system.supply.service.ISupplyOrderService;
import com.manage.project.system.supply.service.ISupplyProductService;
import com.manage.project.system.supply.service.ISupplyVorderService;
import com.manage.project.system.supply.service.ISupplyVproductService;
import com.manage.project.system.supply.vo.*;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.service.IVendingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.druid.util.StringUtils;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。 信息操作处理
 * 
 * @author wusijie
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/supply/supplyVorder")
public class SupplyVorderController extends BaseController
{
	@Autowired
	private ISupplyVorderService supplyVorderService;
	
	@Autowired
	private ISupplyOrderService supplyOrderService;
	@Autowired
	private ISupplyVproductService supplyVproductService;
	@Autowired
	private ISupplyProductService supplyProductService;

	@Autowired
	private IVendingService vendingService;
	
	/**
	 * 查询补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。列表
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(String vorderId, String sOrderId)
	{
		SupplyVorder supplyVorder = new SupplyVorder();
		supplyVorder.setVorderId(vorderId);
		List<SupplyVorder> list = supplyVorderService.selectSupplyVorderList(supplyVorder);
		if (list == null || list.isEmpty()) {
			return AjaxResult.error("无记录");
		}
		supplyVorder = list.get(0);
		
		SupplyOrder supplyOrderR=supplyOrderService.selectSupplyOrderById(sOrderId);
		if(supplyOrderR==null){
			return AjaxResult.error("无记录");
		}
		//查询补货单商品信息
		SupplyProduct supplyProductP=new SupplyProduct();
		supplyProductP.setSOrderId(supplyOrderR.getSOrderId());
        List<SupplyProduct> supplyProductList=supplyProductService.selectSupplyProductList(supplyProductP);
        //查询补货单站点商品信息
		SupplyVproduct supplyVproductP=new SupplyVproduct();
		supplyVproductP.setSOrderId(supplyOrderR.getSOrderId());
        List<SupplyVproduct> supplyVproductList=this.supplyVproductService.selectSupplyVproductList(supplyVproductP);
        Map<String,String> siteIdMap=new HashMap<String,String>();
		Map<String,String> productIdMap=new HashMap<String,String>();
		for(SupplyVproduct supplyVproduct:supplyVproductList){
             siteIdMap.put(supplyVproduct.getSiteId(),supplyVproduct.getSiteId());
			 productIdMap.put(supplyVproduct.getProductId(),supplyVproduct.getProductId());
			 if(productIdMap.get(supplyVproduct.getProductId()+"_productRSupplyNum")==null)
			   productIdMap.put(supplyVproduct.getProductId()+"_productRSupplyNum",String.valueOf(supplyVproduct.getRSupplyNum()));
			else
			   productIdMap.put(supplyVproduct.getProductId()+"_productRSupplyNum",String.valueOf(supplyVproduct.getRSupplyNum()+Integer.parseInt(productIdMap.get(supplyVproduct.getProductId()+"_productRSupplyNum"))));
		}
		//封装补货单商品信息
		int rSupplyNum = 0;
		List<SupplyOrderProductVo> supplyOrderProductVoList=new ArrayList<>();
		SupplyOrderProductVo supplyOrderProductVo=null;
		for(SupplyProduct supplyProductPP:supplyProductList){
			supplyOrderProductVo=new SupplyOrderProductVo();
			supplyOrderProductVo.setProductId(supplyProductPP.getProductId());
			supplyOrderProductVo.setProductName(SystemUtil.getProductNameById(supplyProductPP.getProductId()));
			supplyOrderProductVo.setSupplyNum(supplyProductPP.getSupplyNum());
			supplyOrderProductVo.setrSupplyNum(Integer.parseInt(productIdMap.get(supplyProductPP.getProductId()+"_productRSupplyNum")));
			rSupplyNum = supplyOrderProductVo.getrSupplyNum()+rSupplyNum;
			supplyOrderProductVoList.add(supplyOrderProductVo);
		}
		//封装补货单站点商品信息
		List<SupplyOrderVProductVo> supplyOrderVProductVolist=new ArrayList<>();
		SupplyOrderVProductVo supplyOrderVProductVo=null;
		for(String siteId:siteIdMap.keySet()){
			if (!supplyVorder.getSiteId().equals(siteId)) {
				continue;
			}
			Vending vending=vendingService.selectVendingBySiteId(siteId);
			supplyOrderVProductVo=new SupplyOrderVProductVo();
			supplyOrderVProductVo.setSiteId(siteId);
			supplyOrderVProductVo.setSiteName(vending.getSiteName());
			//封装站点的商品信息
			List<SupplyOrderProductVo> supplyInfo=new ArrayList<>();
			for(SupplyVproduct supplyVproduct:supplyVproductList){
				if(supplyVproduct.getSiteId().equals(siteId)) {
					boolean isexsit=false;
					supplyOrderProductVo = new SupplyOrderProductVo();
					for(SupplyOrderProductVo supplyOrderProductVoTemp:supplyInfo) {
						if(supplyOrderProductVoTemp.getProductId().equals(supplyVproduct.getProductId())) {
							isexsit = true;
							supplyOrderProductVo=supplyOrderProductVoTemp;
						}
					}
					if(isexsit){
						supplyOrderProductVo.setSupplyNum(supplyOrderProductVo.getSupplyNum()+supplyVproduct.getSupplyNum());
						supplyOrderProductVo.setrSupplyNum(supplyOrderProductVo.getrSupplyNum()+supplyVproduct.getRSupplyNum());
					}else{
						supplyOrderProductVo.setProductId(supplyVproduct.getProductId());
						supplyOrderProductVo.setProductName(SystemUtil.getProductNameById(supplyVproduct.getProductId()));
						supplyOrderProductVo.setSupplyNum(supplyVproduct.getSupplyNum());
						supplyOrderProductVo.setrSupplyNum(supplyVproduct.getRSupplyNum());
						supplyInfo.add(supplyOrderProductVo);
					}
				}
			}
			supplyOrderVProductVo.setSupplyInfo(supplyInfo);
			supplyOrderVProductVolist.add(supplyOrderVProductVo);
		}
		//分装站点汇总信息
		SupplyVorderDetailVo supplyVorderDetailVo = new SupplyVorderDetailVo();
		supplyVorderDetailVo.setSiteInfo(supplyOrderVProductVolist);
		SupplyVorderVo supplyVorderVo = new SupplyVorderVo();
		supplyVorderVo.setVorderId(vorderId);
		supplyVorderVo.setLineId(supplyOrderR.getLineId());
		supplyVorderVo.setLineName(SystemUtil.getVendingLineNameFromCache(supplyOrderR.getLineId()));
		supplyVorderVo.setSupplierId(supplyVorder.getSupplierId());
		User user = SystemUtil.getUserByLoginId(supplyOrderR.getCorpId(), supplyVorder.getSupplierId());
		if(user!=null) {
			supplyVorderVo.setSupplierName(user.getUserName());
		}
		supplyVorderVo.setWmId(supplyOrderR.getWmId());
		supplyVorderVo.setWmName(SystemUtil.getStockInfo(supplyOrderR.getWmId())!=null?SystemUtil.getStockInfo(supplyOrderR.getWmId()).getStockName():"");
		supplyVorderVo.setCurState(supplyVorder.getCurState());
		if (StringUtils.isEmpty(supplyVorder.getCurState())) {
			supplyVorderVo.setCurStateName("");
		} else if (supplyVorder.getCurState().equals("1")) {
			supplyVorderVo.setCurStateName("等待补货");
		} else if (supplyVorder.getCurState().equals("2")) {
			supplyVorderVo.setCurStateName("完成补货");
		} else {
			supplyVorderVo.setCurStateName("");
		}
		supplyVorderVo.setSupplyNum(supplyVorder.getSupplyNum());
		supplyVorderVo.setSupplyFTime(supplyVorder.getCreateTime());
		supplyVorderDetailVo.setSupplyVorder(supplyVorderVo);
		
		return AjaxResult.success(supplyVorderDetailVo);
	}
}
