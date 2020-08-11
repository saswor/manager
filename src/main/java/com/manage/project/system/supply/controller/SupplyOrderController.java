package com.manage.project.system.supply.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.manage.project.common.Constant;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.base.service.IUserService;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.stock.domain.StockInfo;
import com.manage.project.system.supply.domain.SupplyConfig;
import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.domain.SupplyProduct;
import com.manage.project.system.supply.domain.SupplyVending;
import com.manage.project.system.supply.domain.SupplyVproduct;
import com.manage.project.system.supply.service.ISupplyOrderService;
import com.manage.project.system.supply.service.ISupplyProductService;
import com.manage.project.system.supply.service.ISupplyVproductService;
import com.manage.project.system.supply.vo.*;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.domain.VendingModel;
import com.manage.project.system.vending.domain.VendingPconfig;
import com.manage.project.system.vending.domain.VendingPlane;
import com.manage.project.system.vending.service.IVendingService;
import com.manage.project.system.vendingPoint.domain.VendingLine;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.framework.web.page.TableDataInfo;

/**
 * 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。 信息操作处理
 * 
 * @author wusijie
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/supply/supplyOrder")
public class SupplyOrderController extends BaseController
{
    private String prefix = "module/supplyOrder";
	
	@Autowired
	private ISupplyOrderService supplyOrderService;
	@Autowired
	private ISupplyVproductService supplyVproductService;
	@Autowired
	private ISupplyProductService supplyProductService;
	
	private Logger log = LoggerFactory.getLogger(SupplyOrderController.class);

	@Autowired
	private IVendingService vendingService;
	@Autowired
	private IUserService userService;

	@RequiresPermissions("supply:supplyOrder:view")
	@GetMapping()
	public String supplyOrder()
	{
	    return prefix + "/supplyOrder";
	}
	
	/**
	 * 查询补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(SupplyOrder supplyOrder)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			supplyOrder.setCorpId("");
		} else {
			supplyOrder.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<SupplyOrder> list = supplyOrderService.selectSupplyOrderList(supplyOrder);
        TableDataInfo dataTable = getDataTable(list);
        List<SupplyOrderListVo> supplyOrderListVoList=new ArrayList<>();
		SupplyOrderListVo supplyOrderListVo=null;
		for(SupplyOrder supplyOrderR:list){
			supplyOrderListVo=new SupplyOrderListVo();
			String wmId = supplyOrderR.getWmId();
			supplyOrderListVo.setWmId(wmId);
			StockInfo stock = SystemUtil.getStockInfo(wmId);
			if(stock!=null) {
				supplyOrderListVo.setWmName(stock.getStockName());
			}		
			User user = SystemUtil.getUserByLoginId(SystemUtil.getCorpId(), supplyOrderR.getSupplierId());
			if(user!=null) {
				supplyOrderListVo.setSupplierName(user.getUserName());
			}
			VendingLine vendingLine=SystemUtil.getVendingLineFromCache(supplyOrderR.getLineId());
			if(vendingLine!=null)
			    supplyOrderListVo.setLineName(vendingLine.getName());
			else
				supplyOrderListVo.setLineName("");
			supplyOrderListVo.setLineId(supplyOrderR.getLineId());
			supplyOrderListVo.setCreateTime(supplyOrderR.getCreateTime());
			supplyOrderListVo.setCurState(supplyOrderR.getCurState());
			supplyOrderListVo.setNum(supplyOrderR.getNum());
			supplyOrderListVo.setsOrderId(supplyOrderR.getSOrderId());
			supplyOrderListVo.setStockState(supplyOrderR.getStockState());
			supplyOrderListVo.setSupplyNum(supplyOrderR.getSupplyNum());
			//显示状态名
			supplyOrderListVo.setCurStateName(SystemUtil.parseSupplyState(supplyOrderListVo.getCurState()));
			supplyOrderListVo.setStockStateName(SystemUtil.parseStockState(supplyOrderListVo.getStockState()));
			supplyOrderListVoList.add(supplyOrderListVo);
		}
		dataTable.setRows(supplyOrderListVoList);
		return AjaxResult.success(dataTable);
	}

	/**
	 * 查询补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。列表
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(SupplyOrder supplyOrder)
	{
		SupplyOrder supplyOrderR=this.supplyOrderService.selectSupplyOrderById(supplyOrder.getSOrderId());
		if(supplyOrderR==null){
			return AjaxResult.error("无记录");
		}
		//查询补货单商品信息
		SupplyProduct supplyProductP=new SupplyProduct();
		supplyProductP.setSOrderId(supplyOrderR.getSOrderId());
        List<SupplyProduct> supplyProductList=this.supplyProductService.selectSupplyProductList(supplyProductP);
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
			Vending vending=this.vendingService.selectVendingBySiteId(siteId);
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
		SupplyOrderDetailVo supplyOrderDetailVo=new SupplyOrderDetailVo();
		supplyOrderDetailVo.setSiteInfo(supplyOrderVProductVolist);
		supplyOrderDetailVo.setSumInfo(supplyOrderProductVoList);
		SupplyOrderVo supplyOrderVo=new SupplyOrderVo();
		supplyOrderVo.setLineId(supplyOrderR.getLineId());
		supplyOrderVo.setLineName(SystemUtil.getVendingLineNameFromCache(supplyOrderR.getLineId()));
		supplyOrderVo.setNum(supplyOrderR.getNum());
		supplyOrderVo.setsOrderId(supplyOrderR.getSOrderId());
		supplyOrderVo.setSupplyNum(supplyOrderR.getSupplyNum());
		supplyOrderVo.setrSupplyNum(rSupplyNum);
		String supplierId = supplyOrderR.getSupplierId();
		supplyOrderVo.setSupplierId(supplierId);
		User user = SystemUtil.getUserByLoginId(supplyOrderR.getCorpId(), supplierId);
		if(user!=null) {
			supplyOrderVo.setSupplierName(user.getUserName());
		}
		supplyOrderVo.setSupplyFTime(supplyOrderR.getSupplyFTime());
		if(Integer.parseInt(supplyOrderR.getType()) == 1){
			supplyOrderVo.setTypeName("全补齐");
		}else{
			supplyOrderVo.setTypeName("阈值补齐");
		}
		supplyOrderVo.setWmId(supplyOrderR.getWmId());
		supplyOrderVo.setWmName(SystemUtil.getStockInfo(supplyOrderR.getWmId())!=null?SystemUtil.getStockInfo(supplyOrderR.getWmId()).getStockName():"");
		supplyOrderDetailVo.setSupplyOrder(supplyOrderVo);
		return AjaxResult.success(supplyOrderDetailVo);
	}
	
	/**
	 * 根据站点商品补货信息生成补货单
	 */
	@PostMapping("/add")
	@Log(title="生成补货单",action=BusinessType.INSERT)
	@ResponseBody
	public AjaxResult add(@RequestBody SupplyVProductAddVo supplyVProductAddVo){
		//查询售货机状态
		Vending vending = vendingService.selectVendingBySiteId(supplyVProductAddVo.getSiteId());
		if(vending==null) {
			return AjaxResult.error("售货机不存在");
		}else if(Constant.VENDING_CURSTATE_NOTREGISTER.equals(vending.getCurState())) {
			return AjaxResult.error("售货机未认证");
		}else if(Constant.VENDING_CURSTATE_DELETE.equals(vending.getCurState())) {
			return AjaxResult.error("售货机已删除");
		}
        int flag=this.supplyOrderService.insertSupplyOrderBySite(supplyVProductAddVo);
		if(flag==1){
			return AjaxResult.success();
		}else if(flag==2){
			return AjaxResult.error("此站点未配置补货规则");
		}else if(flag==3){
			return AjaxResult.error("此站点的补货配置不存在");
		}else if(flag==4){
			return AjaxResult.error("线路下没有售货机存在");
		}else if(flag==5){
			return AjaxResult.error("货道配置不完全");
		}else if(flag==6){
			return AjaxResult.error("该线路已存在补货单");
		}else if(flag==7){
			return AjaxResult.error("该点位已存在补货单");
		}else if(flag==8){
			return AjaxResult.error("该点位库存已满或高于阈值,不需要补货");
		}else{
			return AjaxResult.error("未知错误");
		}
	}
	
	/**
	 * 根据线路商品补货信息生成补货单
	 */
	@PostMapping("/addLine")
	@Log(title="生成补货单",action=BusinessType.INSERT)
	@ResponseBody
	public AjaxResult add(@RequestBody SupplyConfig supplyConfig){
        int flag=this.supplyOrderService.insertSupplyOrderByLine(supplyConfig.getSupplyId());
		if(flag==1){
			return AjaxResult.success();
		}else if(flag==2){
			return AjaxResult.error("此站点未配置补货规则");
		}else if(flag==3){
			return AjaxResult.error("此线路的补货配置不存在");
		}else if(flag==4){
			return AjaxResult.error("线路下没有售货机存在");
		}else if(flag==5){
			return AjaxResult.error("货道配置不完全");
		}else if(flag==6){
			return AjaxResult.error("该线路已存在补货单");
		}else if(flag==7){
			return AjaxResult.error("该点位已存在补货单");
		}else if(flag==8){
			return AjaxResult.error("库存已满或高于阈值,不需要补货");
		}else{
			return AjaxResult.error("未知错误");
		}
	}
	
	/**
	 * 根据线路商品补货信息生成补货单
	 */
	@PostMapping("/export")
	@Log(title="导出补货单",action=BusinessType.EXPORT)
	@ResponseBody
	public AjaxResult export(@RequestBody SupplyOrder supplyOrder) {
		try {
			if(StringUtils.isEmpty(supplyOrder.getStartDate())) {
				return AjaxResult.error("开始时间不能为空");
			}
			if(StringUtils.isEmpty(supplyOrder.getEndDate())) {
				return AjaxResult.error("结束时间不能为空");
			}
			supplyOrder.setCorpId(SystemUtil.getCorpId());
			ExcelUtil<SupplyProduct> util = new ExcelUtil<SupplyProduct>(SupplyProduct.class);
			//查询已出库了已下架的补货单
			List<SupplyProduct> list = supplyProductService.selectSupplyProductListForExport(supplyOrder);
			for (SupplyProduct supplyProduct : list) {
				supplyProduct.setLineName(SystemUtil.getVendingLineNameFromCache(supplyProduct.getLineId()));
				supplyProduct.setProductName(SystemUtil.getProductNameById(supplyProduct.getProductId()));
			}
            return util.exportExcel(list, "Sheet1");
			
		} catch (Exception e) {
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
	
	/**
	 * 导出补货单详情
	 * @throws IOException 
	 */
	@PostMapping("/exportDetail")
	@Log(title="导出补货单详情",action=BusinessType.EXPORT)
	@ResponseBody
	public AjaxResult exportDetail(@RequestBody SupplyOrder supplyOrder) throws IOException {
		FileOutputStream fos = null;
		try {
			AjaxResult result = detail(supplyOrder);
			Object datas = result.getBodyDatas();
			if(datas instanceof SupplyOrderDetailVo) {
				SupplyOrderDetailVo vo = (SupplyOrderDetailVo)datas;
				SupplyOrderVo order = vo.getSupplyOrder();
				List<SupplyOrderProductVo> sumInfo = vo.getSumInfo();
				List<SupplyOrderVProductVo> siteInfo = vo.getSiteInfo();
				
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("补货单详情");
				//插入补货单信息
				ExcelUtil<SupplyOrderVo> orderUtil = new ExcelUtil<SupplyOrderVo>(SupplyOrderVo.class);
				List<SupplyOrderVo> orderList = Arrays.asList(order);
				int endRow = orderUtil.insertListToSheet(workbook, sheet, orderList, 0, 0,"补货记录信息");
				//插入线路补货汇总信息
				ExcelUtil<SupplyOrderProductVo> sumInfoUtil = new ExcelUtil<SupplyOrderProductVo>(SupplyOrderProductVo.class);
				endRow = sumInfoUtil.insertListToSheet(workbook, sheet, sumInfo, endRow+1, 0,"线路补货汇总");
				//插入点位补货信息
				ExcelUtil<SupplyOrderVProductVo> siteInfoUtil = new ExcelUtil<SupplyOrderVProductVo>(SupplyOrderVProductVo.class);
				int count=1;
				endRow++;
				for (SupplyOrderVProductVo supplyOrderVProductVo : siteInfo) {
					//插入标题
					String title="";
					if(count==1) {
						title="点位补货记录";
					}
					count++;
					endRow = siteInfoUtil.insertListToSheet(workbook, sheet, Arrays.asList(supplyOrderVProductVo), endRow, 0,title);
					//插入详情
					List<SupplyOrderProductVo> supplyInfo = supplyOrderVProductVo.getSupplyInfo();
					endRow = sumInfoUtil.insertListToSheet(workbook, sheet, supplyInfo, endRow, 0,null);
				}
				String path="file/excel/"+UUID.randomUUID().toString()+".xls";
				File file = new File(ManageConfig.getUploadPrefix()+path);
				fos = new FileOutputStream(file);
				workbook.write(fos);
				fos.close();
				return AjaxResult.success(path);
			}else {
				log.error("导出补货单详情失败,补货单详情对象异常:"+JSONObject.toJSONString(datas));
				return error("导出Excel失败，请联系网站管理员！");
			}
			
		} catch (Exception e) {
			log.error("导出补货单详情失败:",e);
			if(fos!=null) {
				fos.close();
			}
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
	
}
