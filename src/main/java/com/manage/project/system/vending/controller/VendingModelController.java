package com.manage.project.system.vending.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.util.StringUtil;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.bean.BeanUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingCabinet;
import com.manage.project.system.vending.domain.VendingModel;
import com.manage.project.system.vending.service.IVendingCabinetService;
import com.manage.project.system.vending.service.IVendingModelService;
import com.manage.project.system.vending.service.IVendingPconfigService;
import com.manage.project.system.vending.vo.Cols;
import com.manage.project.system.vending.vo.VendingLaneVo;
import com.manage.project.system.vending.vo.VendingLanepVo;
import com.manage.project.system.vending.vo.VendingModelVo;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 售货机机型管理，包括主柜和副柜机型 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/vendingModel")
public class VendingModelController extends BaseController
{
	@Autowired
	private IVendingModelService vendingModelService;
	@Autowired
	private IVendingCabinetService vendingCabinetService;
	@Autowired
	private IVendingPconfigService vendingPconfigService;
	
	/**
	 * 查询售货机机型管理，包括主柜和副柜机型列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingModel vendingModel)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			vendingModel.setCorpId("");
		} else {
			vendingModel.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		vendingModel.setCorpId(SystemUtil.getCorpId());
        List<VendingModel> list = vendingModelService.selectVendingModelList(vendingModel);
        
        // 设置空的货道信息，为前端显示使用
//        if (list != null) {
//        	for (VendingModel vm : list) {
//        		this.getCols(vm);
//        	}
//        }
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 根据机型code构造货道，给前端展现使用
	 * @param deviceCode
	 * @return
	 */
	@GetMapping("/getLaneList")
	@ResponseBody
	public AjaxResult getLaneList(String deviceCode)
	{
		VendingModel param = new VendingModel();
		param.setDeviceCode(deviceCode);
		param.setCorpId(SystemUtil.getCorpId());
		List<VendingModel> models = vendingModelService.selectVendingModelList(param);
		if (models == null || models.isEmpty()) {
			return AjaxResult.error("no deviceId vendingmodel.");
		}
		VendingModel vm = models.get(0);
		if (vm != null) {
			List<Cols> list = this.getCols(vm);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cabinetType", vm.getCabinetType());
			map.put("cabinetTypeName", vm.getCabinetTypeName());
			map.put("factoryId", vm.getFactoryId());
			map.put("factoryName", vm.getFactoryName());
			map.put("lanes", list);
			
			return AjaxResult.success(map);
		} else {
			return AjaxResult.error("no vendingModel data.");
		}
	}
	
	/**
	 * 查询售货机机型管理，包括主柜和副柜机型列表
	 */
	@GetMapping("/get")
	@ResponseBody
	public AjaxResult get(VendingModel vendingModel)
	{
        vendingModel = vendingModelService.selectVendingModelById(vendingModel.getDeviceCode());
        VendingModelVo vo = new VendingModelVo();
        BeanUtils.copyBeanProp(vo, vendingModel);

		return AjaxResult.success(vo);
	}
	
	/**
	 * 新增保存售货机机型管理，包括主柜和副柜机型
	 */
	@Log(title = "售货机机型管理，包括主柜和副柜机型", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody VendingModel vendingModel)
	{	if (StringUtils.isEmpty(vendingModel.getFactoryId())) {
		return AjaxResult.error("厂家不能为空");
	    }
		if (StringUtils.isEmpty(vendingModel.getCabinetType())) {
			return AjaxResult.error("货柜类型不能为空");
		}
		if (StringUtils.isEmpty(vendingModel.getDeviceId())) {
			return AjaxResult.error("型号不能为空");
		}
		if (vendingModel.getDeviceId().length()>30) {
			return AjaxResult.error("型号输入字段过长");
		}
		return toAjax(vendingModelService.insertVendingModel(vendingModel));
	}
	
	/**
	 * 修改保存售货机机型管理，包括主柜和副柜机型
	 */
	@Log(title = "售货机机型管理，包括主柜和副柜机型", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody VendingModel vendingModel)
	{	if (StringUtils.isEmpty(vendingModel.getFactoryId())) {
		return AjaxResult.error("厂家不能为空");
	    }
		if (StringUtils.isEmpty(vendingModel.getCabinetType())) {
			return AjaxResult.error("货柜类型不能为空");
		}
		if (StringUtils.isEmpty(vendingModel.getDeviceId())) {
			return AjaxResult.error("型号不能为空");
		}
		if (vendingModel.getDeviceId().length()>30) {
			return AjaxResult.error("型号输入字段过长");
		}
		AjaxResult result = checkModelUser(new String[] {vendingModel.getDeviceCode()});
		if(!result.isSuccess()) {
			return result;
		}
		return toAjax(vendingModelService.updateVendingModel(vendingModel));
	}
	
	/**
	 * 检查机型是否已经被使用
	 * 
	 * @param deviceIds
	 * @return
	 */
	private AjaxResult checkModelUser(String[] deviceIds) {
		Integer num = vendingCabinetService.selectVendingCabinetListByDeviceIds(deviceIds);
		if(num!=0) {
			return AjaxResult.error("当前机型有售货机在使用");
		}
		int vendingPconfigNum = vendingPconfigService.selectVendingPconfigListByDeviceIds(deviceIds);
		if(vendingPconfigNum!=0) {
			return AjaxResult.error("当前机型有模板在使用");
		}
		return AjaxResult.success();

	}
	
	/**
	 * 删除售货机机型管理，包括主柜和副柜机型
	 */
	@Log(title = "售货机机型管理，包括主柜和副柜机型", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{
		if(StringUtils.isEmpty(ids.getIds())) {
			return AjaxResult.error("请至少选中一行");
		}
		String[] deviceIds = ids.getIds().split(",");
		AjaxResult result = checkModelUser(deviceIds);
		if(!result.isSuccess()) {
			return result;
		}
//		Integer num = vendingCabinetService.selectVendingCabinetListByDeviceIds(deviceIds);
//		if(num!=0) {
//			return AjaxResult.error("当前机型有售货机在使用,无法删除");
//		}		
		return toAjax(vendingModelService.deleteVendingModelByIds(ids.getIds()));
	}
	
	/**
	 * 构造模板货道列表，给前端使用，随机型列表一起给前端。与售卖机新增页面货道列表返回保持一致，方便前端开发。
	 * @param lanes
	 * @return
	 */
	private List<Cols> getCols(VendingModel vendingModel) {
		// 如果机型行，列，总货道数为空，则为脏数据
		if (vendingModel.getRow() == null || vendingModel.getCol() == null || vendingModel.getLaneNum() == null) {
			return new ArrayList<Cols>();
		}
		
		List<Cols> result = new ArrayList<Cols>();
		int laneNum = 0;	// 总货道数
		for (int i = 0; i < vendingModel.getRow(); i++) {// 创建行对象
			Cols row = new Cols();	// 构造一行
			List<VendingLaneVo> cols = new ArrayList<VendingLaneVo>();	
			// 构造行中的货道
			for (int j = 0; j < vendingModel.getCol(); j++) {
				VendingLaneVo vlv = new VendingLaneVo();
				laneNum++;
				vlv.setLaneId(laneNum);
				vlv.setDeviceId(vendingModel.getDeviceId());
				vlv.setCabinetType(vendingModel.getCabinetType());
				vlv.setLaneCode(laneNum);
				vlv.setRow(i+1);
				vlv.setCol(j+1);
				vlv.setCapacity(vendingModel.getCapacity());
				vlv.setWarnCap(vendingModel.getWarnCap());
				
				VendingLanepVo lanep = new VendingLanepVo();	// 货道商品
				lanep.setLaneSId(laneNum);
				lanep.setLaneEId(laneNum);
				lanep.setCapacity(vendingModel.getCapacity());
				lanep.setWarnCap(vendingModel.getWarnCap());
				vlv.setLanep(lanep);
				cols.add(vlv);
				if (laneNum == vendingModel.getLaneNum()) {
					break;
				}
			}
			row.setCols(cols);	
			result.add(row);
		}		
		return result;
	}
}
