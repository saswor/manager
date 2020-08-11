package com.manage.project.system.stock.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.common.Constant;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.base.service.IUserService;
import com.manage.project.system.stock.domain.StockPpurchase;
import com.manage.project.system.stock.domain.StockPurchase;
import com.manage.project.system.stock.domain.StockWarehouse;
import com.manage.project.system.stock.service.IStockPurchaseService;
import com.manage.project.system.stock.vo.ExtraPurchaseVo;
import com.manage.project.system.util.SystemUtil;

/**
 * 仓库采购记录 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/stockPurchase")
public class StockPurchaseController extends BaseController
{
	private Logger log = LoggerFactory.getLogger(StockPurchaseController.class);
	@Autowired
	private IStockPurchaseService stockPurchaseService;
	@Autowired
	private IUserService userService;
	
	/**
	 * 查询仓库采购记录列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(StockPurchase stockPurchase)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			stockPurchase.setCorpId("");
		} else {
			stockPurchase.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		stockPurchase.setCorpId(SystemUtil.getCorpId());
        List<StockPurchase> list = stockPurchaseService.selectStockPurchaseList(stockPurchase);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存仓库采购记录
	 */
	@Log(title = "仓库采购记录", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody List<StockWarehouse> stockWarehouses)
	{		
		if (stockWarehouses == null || stockWarehouses.isEmpty()) {
			return AjaxResult.error("采购单不能为空");
		}
		int pNum = 0;	// 总采购数量
		//int tNum = stockWarehouses.size();	// 采购商品种类数
		int tNum = 0;
		Float totalPrice = 0f;	// 总采购价
		
		String corpId = ShiroUtils.getCorpId();
		String wpurchaseId = SystemUtil.getSeqId(corpId, "as_stock_purchase");
		List<StockPpurchase> stockPpurchases = new ArrayList<StockPpurchase>();
		for (StockWarehouse stockWarehouse: stockWarehouses) {
			if(stockWarehouse.getpNum()==null) {
				return AjaxResult.error("采购数量不能为空");
			}
			if(stockWarehouse.getpNum()<0) {
				return AjaxResult.error("采购数量不能小于0");
			}
			if(stockWarehouse.getpNum()>1000000) {
				return AjaxResult.error("采购数量不能大于1000000");
			}
			//采购单审核的时候填入采购单间
			if(stockWarehouse.getBuyPrice()==null) {
//				return AjaxResult.error("采购单价不能为空");
				stockWarehouse.setBuyPrice(0F);
			}
			try {
				if(stockWarehouse.getpNum()==0) {
					continue;
				}
//				if(stockWarehouse.getBuyPrice()<=0) {
//					return AjaxResult.error("采购单价不能小于等于0");
//				}
				pNum = pNum + stockWarehouse.getpNum();		
				tNum++;
//				totalPrice = totalPrice + stockWarehouse.getpNum()*stockWarehouse.getBuyPrice();
				totalPrice=0F;
			} catch (Exception e) {
				
			}
			
			StockPpurchase stockPpurchase = new StockPpurchase();
			stockPpurchase.setLogid(UUID.randomUUID().toString());
			stockPpurchase.setCorpId(corpId);
			stockPpurchase.setWppurchaseId(SystemUtil.getSeqId(corpId, "as_stock_ppurchase"));
			stockPpurchase.setWpurchaseId(wpurchaseId);
			stockPpurchase.setStockId(stockWarehouse.getStockId());
			stockPpurchase.setStokcName(stockWarehouse.getStokcName());
			stockPpurchase.setProductId(stockWarehouse.getProductId());
			stockPpurchase.setProductName(stockWarehouse.getProductName());
			stockPpurchase.setPnum(stockWarehouse.getpNum());
			stockPpurchase.setBuyPrice(0F);
			stockPpurchase.setTotalPrice(0F);
			stockPpurchase.setCreateTime(DateUtils.getTime());
			stockPpurchase.setIsDel(stockWarehouse.getIsDel());
			stockPpurchases.add(stockPpurchase);
		}
		if(pNum==0) {
			return AjaxResult.error("生成采购单失败,没有要采购的商品或采购的商品数量为零");
		}
		
		StockPurchase stockPurchase = new StockPurchase();
		
		stockPurchase.setCorpId(ShiroUtils.getCorpId());
		stockPurchase.setLogid(UUID.randomUUID().toString());
		stockPurchase.setCreateTime(DateUtils.getTime());
		stockPurchase.setStockId(stockWarehouses.get(0).getStockId());
		stockPurchase.setWpurchaseId(wpurchaseId);
		stockPurchase.setStokcName(stockWarehouses.get(0).getStokcName());
		stockPurchase.setPnum(pNum);
		stockPurchase.setTnum(tNum);
		stockPurchase.setTotalPrice(0F);
		stockPurchase.setCurState(Constant.PURCHASE_CUR_STATE_NORMAL);
		stockPurchase.setStateTime(DateUtils.getTime());
		stockPurchase.setCheckState(Constant.PURCHASE_CHECK_STATE_WAIT);
		stockPurchase.setStockState(Constant.STOCK_STATE_WAIT);
		stockPurchase.setSupplyId(ShiroUtils.getUserId().toString());
		stockPurchase.setSupplyName(ShiroUtils.getUser().getUserName());
		return toAjax(stockPurchaseService.insertPurchase(stockPurchase, stockPpurchases));
	}
	
//	/**
//	 * 修改保存仓库采购记录
//	 */
//	@Log(title = "仓库采购记录", action = BusinessType.UPDATE)
//	@PostMapping("/edit")
//	@ResponseBody
//	public AjaxResult editSave(@RequestBody StockPurchase stockPurchase)
//	{		
//		return toAjax(stockPurchaseService.updateStockPurchase(stockPurchase));
//	}
	
	/**
	 * 删除仓库采购记录
	 */
	@Log(title = "仓库采购记录", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(stockPurchaseService.deleteStockPurchaseByIds(ids.getIds()));
	}
	
	@Log(title = "审核", action = BusinessType.UPDATE)
	@PostMapping("/check")
	@ResponseBody
	public AjaxResult check(@RequestBody StockPurchase stockPurchase)
	{		
		if (stockPurchase.getWpurchaseId() == null || stockPurchase.getWpurchaseId().equals("")) {
			return AjaxResult.error("采购单号不能为空");
		}
		if (stockPurchase.getCheckState() == null || stockPurchase.getCheckState().equals("")) {
			return AjaxResult.error("采购状态不能为空");
		}
		StockPurchase purcharse = stockPurchaseService.selectStockPurchaseByWpurchaseId(stockPurchase.getWpurchaseId());
		//只有未审核状态才能审核
		if("1".equals(purcharse.getCheckState())) {
			stockPurchase.setCheckId(ShiroUtils.getUserId().toString());
			stockPurchase.setCheckTime(DateUtils.getTime());
			int result = stockPurchaseService.updateStockPurchase(stockPurchase);
			if(result==-1) {
				return AjaxResult.error("采购金额不能为空");
			}else if(result==-1) {
				return AjaxResult.error("采购金额不能为必须在0-1000000之间,不包含0和1000000");
			}else {
				return AjaxResult.success();
			}	
		}else {
			return AjaxResult.error("只有未审核状态下才能审核");
		}	
	}
	
	/**
	 * 采购详情
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(StockPurchase stockPurchase){
		if (stockPurchase.getWpurchaseId() == null || stockPurchase.getWpurchaseId().equals("")) {
			return AjaxResult.error("采购编号不能为空");
		}
		StockPurchase purcharse = stockPurchaseService.selectStockPurchaseByWpurchaseId(stockPurchase.getWpurchaseId());
		StockPurchase stockPurchaseR = new StockPurchase();
		stockPurchaseR.setWpurchaseId(purcharse.getWpurchaseId());
		stockPurchaseR.setStockId(purcharse.getStockId());
		stockPurchaseR.setStokcName(purcharse.getStokcName());
		stockPurchaseR.setTnum(purcharse.getTnum());
		stockPurchaseR.setPnum(purcharse.getPnum());
		stockPurchaseR.setTotalPrice(purcharse.getTotalPrice());
		stockPurchaseR.setSupplyName(purcharse.getSupplyName());
		stockPurchaseR.setCheckState(purcharse.getCheckState());
		String checkId = purcharse.getCheckId();
		stockPurchaseR.setCheckId(checkId);
		if(StringUtils.isNotEmpty(checkId)) {
			User user = SystemUtil.getUserById(Long.parseLong(purcharse.getCheckId()));
	  		if(user == null){
	  			stockPurchaseR.setCheckName("");
			}else {
				stockPurchaseR.setCheckName(user.getUserName());
			}
		}
  		stockPurchaseR.setCheckTime(purcharse.getCheckTime());
  		return AjaxResult.success(stockPurchaseR);
	}
	
	/**
	 * 补货时额外采购
	 */
	@Log(title = "仓库采购记录", action = BusinessType.INSERT)
	@PostMapping("/extraPurchase")
	@ResponseBody
	public AjaxResult extraPurchase(@RequestBody ExtraPurchaseVo vo)
	{	
		try {
			int result = stockPurchaseService.extraPurchase(vo);
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("请至少入库一件商品");
			}else if(result==3) {
				return AjaxResult.error("商品数量不能小于0");
			}else if(result==4) {
				return AjaxResult.error("商品数量不能大于9999999");
			}else if(result==5) {
				return AjaxResult.error("仓库库存中不存在对应商品");
			}else if(result==5) {
				return AjaxResult.error("系统库存中不存在对应商品");
			}else {
				return AjaxResult.error("入库失败");
			}
		}catch (Exception e) {
			log.error("入库失败,时间:"+DateUtils.getTime(),e);
			return AjaxResult.error("入库失败");
		}
	}
	
	/**
	 * 导出采购记录
	 */
	@Log(title = "导出采购记录", action = BusinessType.EXPORT)
	@PostMapping( "/exportExcel")
	@ResponseBody
	public AjaxResult exportExcel(@RequestBody StockPurchase stockPpurchase) {

		try {

			ExcelUtil<StockPurchase> util = new ExcelUtil<StockPurchase>(StockPurchase.class);
			stockPpurchase.setCorpId(SystemUtil.getCorpId());
	        List<StockPurchase> list = stockPurchaseService.selectStockPurchaseList(stockPpurchase);
            return util.exportExcel(list, "Sheet1");
			
		} catch (Exception e) {
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
}
