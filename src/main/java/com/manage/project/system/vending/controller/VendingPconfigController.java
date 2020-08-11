package com.manage.project.system.vending.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.exception.vending.LaneProductCapacityException;
import com.manage.common.exception.vending.LaneProductWarnCapException;
import com.manage.common.exception.vending.WarnCapGreatThanCapacityException;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.bean.BeanUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingModel;
import com.manage.project.system.vending.domain.VendingPconfig;
import com.manage.project.system.vending.domain.VendingPlane;
import com.manage.project.system.vending.service.IVendingModelService;
import com.manage.project.system.vending.service.IVendingPconfigService;
import com.manage.project.system.vending.service.IVendingPlaneService;
import com.manage.project.system.vending.service.VendingPconfigServiceImpl;
import com.manage.project.system.vending.vo.Cols;
import com.manage.project.system.vending.vo.PconfigSaveVo;
import com.manage.project.system.vending.vo.VendingLaneVo;
import com.manage.project.system.vending.vo.VendingLanepVo;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 售货机配货模板 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/vendingPconfig")
public class VendingPconfigController extends BaseController
{
	@Autowired
	private IVendingPconfigService vendingPconfigService;
	@Autowired
	private IVendingPlaneService vendingPlaneService;
	
	@Autowired
	private IVendingModelService vendingModelService;
	
	private Logger log = LoggerFactory.getLogger(VendingPconfigController.class);
	/**
	 * 查询售货机配货模板列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingPconfig vendingPconfig)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			vendingPconfig.setCorpId("");
		} else {
			vendingPconfig.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<VendingPconfig> list = vendingPconfigService.selectVendingPconfigList(vendingPconfig);
//		if (list != null && !list.isEmpty()) {
//			for (VendingPconfig vp : list) {
//				VendingPlane vendingPlane = new VendingPlane();
//				vendingPlane.setCorpId(vp.getCorpId());
//				vendingPlane.setMConfigId(vp.getMConfigId());
//				List<VendingPlane> vl = vendingPlaneService.selectVendingPlaneList(vendingPlane);
//				vp.setPlanes(vl);
//			}
//		}
        return AjaxResult.success(getDataTable(list));
	}
	
	@GetMapping("/get")
	@ResponseBody
	public AjaxResult get(String logid)
	{
		if (StringUtils.isEmpty(logid)) {
			return AjaxResult.error("param is null");
		}
		VendingPconfig config = vendingPconfigService.selectVendingPconfigById(logid);
		
		PconfigSaveVo psv = new PconfigSaveVo();
		BeanUtils.copyBeanProp(psv, config);
		VendingModel model = vendingModelService.selectVendingModelById(config.getDeviceId());
		if(model!=null) {
			psv.setDevice(model.getDeviceId());
		}
		
		// 根据机型构造模板的行和列结构，给前端用
		VendingModel vendingModel = new VendingModel();
		vendingModel.setDeviceCode(config.getDeviceId());
		List<VendingModel> models = vendingModelService.selectVendingModelList(vendingModel);
		if (models == null || models.isEmpty()) {
			return AjaxResult.error("no deviceId VendingModel.");
		}
		VendingModel vm = models.get(0);
		// 查询模板货道
		VendingPlane vendingPlane = new VendingPlane();
		vendingPlane.setMConfigId(config.getMConfigId());
		List<VendingPlane> planes = vendingPlaneService.selectVendingPlaneList(vendingPlane);
		List<Cols> rows = this.getCols(vm, planes);
		psv.setLanes(rows);
		
		return AjaxResult.success(psv);
	}
	
	/**
	 * 构造模板货道列表，给前端使用，随机型列表一起给前端。与售卖机新增页面货道列表返回保持一致，方便前端开发。
	 * @param lanes
	 * @return
	 */
	private List<Cols> getCols(VendingModel vendingModel, List<VendingPlane> planes) {
		// 如果机型行，列，总货道数为空，则为脏数据
		if (vendingModel.getRow() == null || vendingModel.getCol() == null || vendingModel.getLaneNum() == null) {
			return new ArrayList<Cols>();
		}
		
		// 将模板货道转成map,key为row-col
		Map<String, VendingPlane> map =new HashMap<String, VendingPlane>();
		if (planes != null) {
			for (VendingPlane v : planes) {
				map.put(v.getRow() + "-" + v.getCol(), v);
			}
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
				vlv.setDeviceId(vendingModel.getDeviceCode());
				vlv.setCabinetType(vendingModel.getCabinetType());
				vlv.setLaneCode(laneNum);
				int r = i+1;
				int c = j+1;
				vlv.setRow(r);
				vlv.setCol(c);
				
				VendingLanepVo lanep = new VendingLanepVo();	// 货道商品
				lanep.setLaneSId(laneNum);
				lanep.setLaneEId(laneNum);
				
				// 插入模板货道及商品
				VendingPlane vp = map.get(r+"-"+c);
				
				lanep.setCurCap(0);
				lanep.setProductId(vp.getProductId());
				lanep.setProductPic(vp.getProductPic());
				lanep.setSalePrice(vp.getSalePrice());
				lanep.setCorpId(vendingModel.getCorpId());
				if (vp.getWarnCap() == null || vp.getWarnCap() == 0) {
					lanep.setWarnCap(null);	
				} else {
					lanep.setWarnCap(vp.getWarnCap());	
				}
				if (vp.getCapacity() == null || vp.getCapacity() == 0) {
					lanep.setCapacity(null);	
				} else {
					lanep.setCapacity(vp.getCapacity());
				}
				lanep.setLaneSate(vp.getLaneSate());
				vlv.setLanep(lanep);
				
				vlv.setArrange(vp.getArrange());
				vlv.setCurState(vp.getLaneSate());
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
	
	/**
	 * 新增保存售货机配货模板
	 */
	@Log(title = "售货机配货模板", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody PconfigSaveVo pconfigSaveVo)
	{	
		if (StringUtils.isEmpty(pconfigSaveVo.getName())) {
			return AjaxResult.error("模板名称不能为空");
		}
		if (pconfigSaveVo.getName().length()>50) {
			return AjaxResult.error("模板名称输入字段过长");
		}
		if (StringUtils.isEmpty(pconfigSaveVo.getFactoryId())) {
			return AjaxResult.error("厂家不能为空");
		}
		if (StringUtils.isEmpty(pconfigSaveVo.getCabinetType())) {
			return AjaxResult.error("货柜类型不能为空");
		}
		if (StringUtils.isEmpty(pconfigSaveVo.getDeviceId())) {
			return AjaxResult.error("型号不能为空");
		}
		int result=0;
		try {
			result = vendingPconfigService.insertVendingPconfig(pconfigSaveVo);
		}catch (LaneProductCapacityException e) {
			return AjaxResult.error("货道最大容量为空或小于1");
		}catch (LaneProductWarnCapException e) {
			return AjaxResult.error("货道阈值为空或小于1");
		}catch (WarnCapGreatThanCapacityException e) {
			return AjaxResult.error("货道阈值大于最大容量");
		}catch (Exception e) {
			return AjaxResult.error("新增配货模板失败");
		}
		return toAjax(result);
	}

	/**
	 * 修改保存售货机配货模板
	 */
	@Log(title = "售货机配货模板", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody PconfigSaveVo pconfigSaveVo)
	{	
		if (StringUtils.isEmpty(pconfigSaveVo.getName())) {
			return AjaxResult.error("模板名称不能为空");
		}
		if (pconfigSaveVo.getName().length()>50) {
			return AjaxResult.error("模板名称输入字段过长");
		}
		if (StringUtils.isEmpty(pconfigSaveVo.getFactoryId())) {
			return AjaxResult.error("厂家不能为空");
		}
		if (StringUtils.isEmpty(pconfigSaveVo.getCabinetType())) {
			return AjaxResult.error("货柜类型不能为空");
		}
		if (StringUtils.isEmpty(pconfigSaveVo.getDeviceId())) {
			return AjaxResult.error("型号不能为空");
		}
		int result=0;
		try {
			result = vendingPconfigService.updateVendingPconfig(pconfigSaveVo);
		}catch (LaneProductCapacityException e) {
			return AjaxResult.error("货道最大容量为空或小于1");
		}catch (LaneProductWarnCapException e) {
			return AjaxResult.error("货道阈值为空或小于1");
		}catch (WarnCapGreatThanCapacityException e) {
			return AjaxResult.error("货道阈值大于最大容量");
		}catch (Exception e) {
			return AjaxResult.error("修改配货模板失败");
		}
		return toAjax(result);
	}
	
	/**
	 * 修改保存售货机配货模板
	 */
	@Log(title = "售货机配货模板", action = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(@RequestBody PconfigSaveVo pconfigSaveVo)
	{	
		try {
			return vendingPconfigService.export(pconfigSaveVo);
		}catch (Exception e) {
			log.error("导出配货模板失败:",e);
			return AjaxResult.error("导出配货模板失败,请联系管理员");
		}
	}
	
	/**
	 * 删除售货机配货模板
	 */
	@Log(title = "售货机配货模板", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(vendingPconfigService.deleteVendingPconfigByIds(ids.getIds()));
	}
	
//	/**
//	 * 模板关联售卖机
//	 * @param mConfigId	模板id
//	 * @param siteId	货柜id
//	 * @return
//	 */
//	@GetMapping( "/relation")
//	@ResponseBody
//	public AjaxResult relation(String mConfigId, String cabinetId) {
//		if (StringUtils.isEmpty(mConfigId) || StringUtils.isEmpty(cabinetId)) {
//			return AjaxResult.error("mConfigId or siteId is null.");
//		}
//		vendingPconfigService.relation(mConfigId, cabinetId);
//		return AjaxResult.success();
//	}
}
