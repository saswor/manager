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

import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.advert.domain.AdvertDevice;
import com.manage.project.system.advert.service.IAdvertDeviceService;
import com.manage.project.system.advert.vo.AdvertDeviceSelectVo;
import com.manage.project.system.advert.vo.TfAdventVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.framework.web.page.TableDataInfo;

/**
 * 广告播放对象设置，也叫播放任务列 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/advertDevice")
public class AdvertDeviceController extends BaseController
{
	@Autowired
	private IAdvertDeviceService advertDeviceService;
	
	/**
	 * 广告任务列表查询
	 * @param deviceId	售卖机编号
	 * @param name	// 任务名称
	 * @return
	 */
	@GetMapping("/selectAdvertDeviceRw")
	@ResponseBody
	public AjaxResult selectAdvertDeviceRw(String deviceId, String name) {
		startPage();
		String corpId = SystemUtil.getCorpId();
		List<AdvertDeviceSelectVo> list = advertDeviceService.selectAdvertDeviceRw(deviceId, name, "01", corpId);
		for (AdvertDeviceSelectVo advertDeviceSelectVo : list) {
			advertDeviceSelectVo.setSiteName(SystemUtil.getVendingNameBySiteId(advertDeviceSelectVo.getDeviceId()));
		}
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 查看广告，设备投放信息接口
	 * @param advertDevice
	 * @return
	 */
	@GetMapping("/tflist")
	@ResponseBody
	public AjaxResult tflist(String districtId, String lineId, String deviceId, String advertId)
	{
		startPage();
		String corpId = SystemUtil.getCorpId();
        List<AdvertDevice> list = advertDeviceService.selectAdvertDeviceList(districtId, lineId, deviceId, advertId, corpId);
        TableDataInfo datas = getDataTable(list);
        
        List<TfAdventVo> result = new ArrayList<TfAdventVo>();
        if (list != null && !list.isEmpty()) {
        	for (AdvertDevice ad : list) {
        		TfAdventVo tfAdventVo = new TfAdventVo();
        		Vending vending = SystemUtil.getVendingBase(ad.getDeviceId());
        		if (vending != null) {
        			VendingLine line = SystemUtil.getVendingLineFromCache(vending.getLineId());
        			if (line != null) {
        				tfAdventVo.setLineName(line.getName());
        			}
        			tfAdventVo.setNetSateName(vending.getNetSateName());
        			tfAdventVo.setSellStateName(vending.getSellStateName());
        			tfAdventVo.setSiteId(ad.getDeviceId());
        			tfAdventVo.setSiteName(vending.getSiteName());
        		} 
        		result.add(tfAdventVo);
        	}
        }
        datas.setRows(result);
        
		return AjaxResult.success(datas);
	}
	
	
	/**
	 * 新增保存广告播放对象设置，也叫播放任务列
	 */
	@Log(title = "广告播放对象设置，也叫播放任务列", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AdvertDevice advertDevice)
	{		
		return toAjax(advertDeviceService.insertAdvertDevice(advertDevice));
	}

	/**
	 * 修改保存广告播放对象设置，也叫播放任务列
	 */
	@Log(title = "广告播放对象设置，也叫播放任务列", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AdvertDevice advertDevice)
	{		
		return toAjax(advertDeviceService.updateAdvertDevice(advertDevice));
	}
	
	/**
	 * 删除广告播放对象设置，也叫播放任务列
	 */
	@Log(title = "广告播放对象设置，也叫播放任务列", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(advertDeviceService.deleteAdvertDeviceByIds(ids.getIds()));
	}
	
}
