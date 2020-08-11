package com.manage.project.system.supply.controller;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.framework.web.page.TableDataInfo;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.base.service.IUserService;
import com.manage.project.system.supply.domain.SupplyConfig;
import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.service.ISupplyConfigService;
import com.manage.project.system.supply.service.ISupplyOrderService;
import com.manage.project.system.supply.service.ISupplyVendingService;
import com.manage.project.system.supply.vo.SupplyConfigDetailVo;
import com.manage.project.system.supply.vo.SupplyConfigListVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.service.IVendingService;
import com.manage.project.system.vendingPoint.domain.VendingLine;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 查询配置的线路预警信息
 * 
 * @author wusijie
 * @date 2018-09-02
 */
@Controller
@RequestMapping("/system/supply/supplyConfig")
public class SupplyConfigController extends BaseController
{
    private String prefix = "supply/supplyConfig";
	
	@Autowired
	private ISupplyConfigService supplyConfigService;
    @Autowired
	private ISupplyVendingService supplyVendingService;
    @Autowired
	private IVendingService vendingService;
    @Autowired
	private IUserService userService;
    @Autowired
	private ISupplyOrderService supplyOrderService;

	@GetMapping()
	public String supplyConfig()
	{
	    return prefix + "/supplyConfig";
	}
	
	/**
	 * 查询商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。列表
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
		/*if(StringUtils.isNotEmpty(supplyConfig.getStartTime())&&StringUtils.isNotEmpty(supplyConfig.getEndTime())&&supplyConfig.getStartTime().compareTo(supplyConfig.getEndTime())>0) {
			return AjaxResult.error("开始时间不能迟于结束时间");
		}*/
        List<SupplyConfig> list = supplyConfigService.selectSupplyConfigList(supplyConfig);
        TableDataInfo tableDataInfo=getDataTable(list);
		List<SupplyConfigListVo> supplyOrderListVoList=new ArrayList();
		SupplyConfigListVo supplyOrderListVo=null;
		for(SupplyConfig supplyConfigR:list){
			supplyOrderListVo=new SupplyConfigListVo();
			supplyOrderListVo.setCreateTime(supplyConfigR.getCreateTime());
			supplyOrderListVo.setEveryTime(supplyConfigR.getEveryTime());
			supplyOrderListVo.setLineId(supplyConfigR.getLineId());
			supplyOrderListVo.setStoryLevel(supplyConfigR.getStoryLevel());
			String supplierId = supplyConfigR.getSupplierId();
			String corpId = SystemUtil.getCorpId();
			User user = SystemUtil.getUserByLoginId(corpId, supplierId);
			if(user!=null) {
				supplyOrderListVo.setSupplierName(user.getUserName());
			}
			supplyOrderListVo.setSupplyName(supplyConfigR.getName());
			supplyOrderListVo.setWmName(SystemUtil.getStockInfo(supplyConfigR.getWmId()).getStockName());
			supplyOrderListVo.setSupplyId(supplyConfigR.getSupplyId());
			supplyOrderListVo.setNum(supplyConfigR.getNum());
			VendingLine vendingLine=SystemUtil.getVendingLineFromCache(supplyConfigR.getLineId());
			if(vendingLine!=null) {
				String vendingDistrict = SystemUtil.getVendingDistrictNameFromCache(vendingLine.getDistrictId());
				if(vendingDistrict!=null) {
					supplyOrderListVo.setDistrictName(vendingDistrict);
				}
				supplyOrderListVo.setLineName(vendingLine.getName());
			}
			supplyOrderListVoList.add(supplyOrderListVo);
		}
        tableDataInfo.setRows(supplyOrderListVoList);
		return AjaxResult.success(tableDataInfo);
	}

    /**
     * 查询线路告警详情
     */
    @GetMapping("/detail")
    @ResponseBody
    public AjaxResult detail(SupplyConfig supplyConfig)
    {
        SupplyConfig supplyConfigR=supplyConfigService.selectSupplyConfigBySupplyId(supplyConfig.getSupplyId());
        SupplyConfigDetailVo supplyConfigDetailVo=new SupplyConfigDetailVo();
        supplyConfigDetailVo.setAdvTime(supplyConfigR.getAdvTime());
        supplyConfigDetailVo.setCreateTime(supplyConfigR.getCreateTime());
        supplyConfigDetailVo.setDescription(supplyConfigR.getDescription());
        supplyConfigDetailVo.setFristlevel(supplyConfigR.getFristlevel());
        supplyConfigDetailVo.setLineId(supplyConfigR.getLineId());
        supplyConfigDetailVo.setStrategy(supplyConfigR.getStrategy());
        supplyConfigDetailVo.setSupplierId(supplyConfigR.getSupplierId());
        User user = userService.selectUserByLoginName(supplyConfigR.getSupplierId());
		if(user == null){
			//return AjaxResult.error("没有记录");
			supplyConfigDetailVo.setSupplierName("");
		}else {
			supplyConfigDetailVo.setSupplierName(user.getUserName());
		}
        supplyConfigDetailVo.setSupplyName(supplyConfigR.getName());
        supplyConfigDetailVo.setThreelevel(supplyConfigR.getThreelevel());
        supplyConfigDetailVo.setTwolevel(supplyConfigR.getTwolevel());
        supplyConfigDetailVo.setType(supplyConfigR.getType());
        supplyConfigDetailVo.setWmId(supplyConfigR.getWmId());
        supplyConfigDetailVo.setWmName(SystemUtil.getStockInfo(supplyConfigR.getWmId()).getStockName());
        return AjaxResult.success(supplyConfigDetailVo);
    }
	
	/**
	 * 新增商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
	 */
	@Log(title = "商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody SupplyConfig supplyConfig)
	{
		if(!checkStockLevel(supplyConfig.getFristlevel(), supplyConfig.getTwolevel(), supplyConfig.getThreelevel())) {
			return AjaxResult.error("库存三级划分不正确");
		}
		int flag = this.supplyConfigService.insertSupplyConfig(supplyConfig);
		if(flag == 1){
			return AjaxResult.success("新建补货配置成功");
		}else if(flag == 2){
			return AjaxResult.error("补货线路重复配置");
		}else if (flag == 3){
			return AjaxResult.error("此线路上没有站点");
		}else if (flag == 4){
			return AjaxResult.error("当前未关联线路");
		}else if (flag == 5){
			return AjaxResult.error("未配置货道数量");
		}else if (flag == 6){
			return AjaxResult.error("存在未配置的货道");
		}else if (flag == 7){
			return AjaxResult.error("补货策略不完整");
		}else if (flag == 8){
			return AjaxResult.error("基本信息填写不完整");
		}else {
			return AjaxResult.error("未知错误");
		}
	}

	/**
	 * 修改商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
	 */
	@GetMapping("/edit/{logid}")
	public String edit(@PathVariable("logid") String logid, ModelMap mmap)
	{
		SupplyConfig supplyConfig = supplyConfigService.selectSupplyConfigById(logid);
		mmap.put("supplyConfig", supplyConfig);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
	 */
	@Log(title = "商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody SupplyConfig supplyConfig)
	{		
		if(!checkStockLevel(supplyConfig.getFristlevel(), supplyConfig.getTwolevel(), supplyConfig.getThreelevel())) {
			return AjaxResult.error("库存三级划分不正确");
		}
		int result = supplyConfigService.updateSupplyConfig(supplyConfig);
		if(result==7) {
			return  AjaxResult.error("补货策略不完整");
		}else if (result == 8){
			return AjaxResult.error("基本信息填写不完整");
		}else {
			return toAjax(result);
		}	
	}
	
	/**
	 * 删除商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
	 */
	@Log(title = "商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody SupplyConfig supplyConfig)
	{
		String ids = supplyConfig.getSupplyId();
		for (String supplyId : ids.split(",")) {
			SupplyOrder supplyOrderSelect = new SupplyOrder();
			supplyOrderSelect.setSupplyId(supplyId);
			supplyOrderSelect.setCurState("1");
			List<SupplyOrder> supplyOrderList = supplyOrderService.selectSupplyOrderList(supplyOrderSelect);
			if(supplyOrderList!=null&&supplyOrderList.size()!=0) {
				return  AjaxResult.error("当前补货配置下有未完成的补货单,请先完成补货后再删除");
			}
		}
		//supplyVendingService.deleteSupplyVendingBySupplyIds(ids);
		return toAjax(supplyConfigService.deleteSupplyConfigByIds(ids));
	}
	
	/**
	 * 判断三级库存是否正确
	 * 
	 * @param first
	 * @param second
	 * @param third
	 * @return
	 */
	public boolean checkStockLevel(String first,String second,String third) {
		try {
			String[] firstLevel = first.split("-");
			String[] secondLevel = second.split("-");
			String[] thirdLevel = third.split("-");
			int firstPrefix = Integer.parseInt(firstLevel[0]);
			int firstSuffix = Integer.parseInt(firstLevel[1]);
			int secondPrefix = Integer.parseInt(secondLevel[0]);
			int secondSuffix = Integer.parseInt(secondLevel[1]);
			int thirdPrefix = Integer.parseInt(thirdLevel[0]);
			int thirdSuffix = Integer.parseInt(thirdLevel[1]);
			//判断是否以0开始,以100结束
			if(firstPrefix!=0||thirdSuffix!=100) {
				return false;
			}
			//判断两级之间是否衔接
			if((firstSuffix!=secondPrefix-1)||(secondSuffix!=thirdPrefix-1)) {
				return false;
			}
		}catch (Exception e) {
			return false;
		}
		return true;
		
	}
}
