package com.manage.project.system.vending.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.server.domain.ServerMessage;
import com.manage.project.system.server.service.IServerService;
import com.manage.project.system.statement.domain.OrderBox;
import com.manage.project.system.statement.service.IOrderBoxService;
import com.manage.project.system.statement.vo.IOrderBoxVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.domain.VendingLsdiffer;
import com.manage.project.system.vending.service.IVendingLsdifferService;
import com.manage.project.system.vending.vo.VendingLsdifferVo;

/**
 * 售货机库存差异
 * @author ldh
 * @title VendingLsdifferController
 * @description TODO
 * @time 2019年1月17日下午1:51:01
 */
@Controller
@RequestMapping("/system/vendingLsdiffer")
public class VendingLsdifferController extends BaseController{
	private Logger log=LoggerFactory.getLogger(VendingLsdifferController.class);
	@Autowired
	private IVendingLsdifferService vendingLsdifferService;
	@Autowired
	private IOrderBoxService orderBoxService;
	@Autowired
	private IServerService serverService; 
	/**
	 * 查询售货机库存差异列表
	 * @param vendingLsdiffer
	 * @return
	 */
	@GetMapping("/selectVendingLsdifferList")
	@ResponseBody
	public AjaxResult selectVendingLsdifferList(VendingLsdiffer vendingLsdiffer) {
		vendingLsdiffer.setCorpId(SystemUtil.getCorpId());
		startPage();
		
        List<VendingLsdiffer> list = vendingLsdifferService.selectVendingLsdifferList(vendingLsdiffer);
        if (!list.isEmpty()) {
       	 for(VendingLsdiffer vLsdiffer : list){
       		 	User user = SystemUtil.getUserByLoginId(vLsdiffer.getCorpId(), vLsdiffer.getCreaterId());
       		 	if(user!=null){
       		 		vLsdiffer.setCreaterName(user.getUserName());
       		 	}
            	
            	if ("1".equals(vLsdiffer.getHandleType())) {
            		vLsdiffer.setHandleTypeName("入库");
        		}else if ("2".equals(vLsdiffer.getHandleType())) {
        			vLsdiffer.setHandleTypeName("出库");
        		}else {
        			vLsdiffer.setHandleTypeName("");
        		}
        		
        		if ("1".equals(vLsdiffer.getCurState())) {
        			vLsdiffer.setCurStateName("待处理");
        		}else if ("2".equals(vLsdiffer.getCurState())) {
        			vLsdiffer.setCurStateName("超期");
        		}else if ("3".equals(vLsdiffer.getCurState())) {
        			vLsdiffer.setCurStateName("已处理");
        		}else {
        			vLsdiffer.setCurStateName("");
        		}	
        		
        		vLsdiffer.setProductName(SystemUtil.getProductNameById(vLsdiffer.getProductId()));
        		Vending vending = SystemUtil.getVendingBase(vLsdiffer.getSiteId());
        		if(vending!=null) {
        			vLsdiffer.setSiteName(vending.getSiteName());
        		}
        		
            }
		}
        
        
		return AjaxResult.success(getDataTable(list));
	}
	/**
	 * 查询订单商品货道列表
	 * @param orderBox
	 * @return
	 */
	@GetMapping("/selectOrderBoxList")
	@ResponseBody
	public AjaxResult selectOrderBoxList(OrderBox orderBoxParam) {
		startPage();
        List<OrderBox> list = orderBoxService.selectOrderBoxList(orderBoxParam);
        for (OrderBox orderBox : list) {
        	if ("1".equals(orderBox.getOutState())) {
    			orderBox.setOutStateName("未出柜");
    		}else if("2".equals(orderBox.getOutState())) {
    			orderBox.setOutStateName("正常已出柜");
    		}else if("3".equals(orderBox.getOutState())) {
    			orderBox.setOutStateName("异常已出柜");
    		}else if("4".equals(orderBox.getOutState())) {
    			orderBox.setOutStateName("出柜失败");
    		}else {
    			orderBox.setOutStateName("");
    		}
        	if ("1".equals(orderBox.getCorrectState())) {
    			orderBox.setCorrectStateName("未矫正");
    		}else if("2".equals(orderBox.getCorrectState())) {
    			orderBox.setCorrectStateName("已矫正");
    		}else {
    			orderBox.setOutStateName("");
    		}
		}
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 获取售货机库存差异详情页数据
	 * @param lsdifferId 售货机lsdifferId
	 * @return	
	 */
	@GetMapping("/getVendingLsdifferView")
	@ResponseBody
	public AjaxResult getVendingLsdifferView(String lsdifferId) {
		//获取售货机库存差异信息
		VendingLsdiffer vendingLsdiffer = vendingLsdifferService.selectVendingLsdifferById(lsdifferId);
		//组装返回页面数据
		VendingLsdifferVo vo = new VendingLsdifferVo();
		vo.setLsdifferId(lsdifferId);
		if (vendingLsdiffer != null) {
			vo.setLsdifferId(lsdifferId);
			vo.setSiteId(vendingLsdiffer.getSiteId());
			vo.setLaneSId(vendingLsdiffer.getLaneSId());
			vo.setLaneEId(vendingLsdiffer.getLaneEId());
			vo.setProductId(vendingLsdiffer.getProductId());
			vo.setCurCap(vendingLsdiffer.getCurCap());
			vo.setResetCap(vendingLsdiffer.getResetCap());
			vo.setDifferNum(vendingLsdiffer.getDifferNum());
			vo.setHandlerNum(vendingLsdiffer.getHandlerNum());
			vo.setHandleType(vendingLsdiffer.getHandleType());
			vo.setStateTime(vendingLsdiffer.getStateTime());
			vo.setCurState(vendingLsdiffer.getCurState());
			vo.setCreaterId(vendingLsdiffer.getCreaterId());
			vo.setOperTime(vendingLsdiffer.getOperTime());
			vo.setCreateTime(vendingLsdiffer.getCreateTime());
			vo.setCorpId(vendingLsdiffer.getCorpId());
			User user = SystemUtil.getUserByLoginId(vo.getCorpId(), vo.getCreaterId());
   		 	if(user!=null){
   		 		vo.setCreaterName(user.getUserName());
   		 	}
        	
        	if ("1".equals(vo.getHandleType())) {
        		vo.setHandleTypeName("入库");
    		}else if ("2".equals(vo.getHandleType())) {
    			vo.setHandleTypeName("出库");
    		}else {
    			vo.setHandleTypeName("");
    		}
    		
    		if ("1".equals(vo.getCurState())) {
    			vo.setCurStateName("待处理");
    		}else if ("2".equals(vo.getCurState())) {
    			vo.setCurStateName("超期");
    		}else if ("3".equals(vo.getCurState())) {
    			vo.setCurStateName("已处理");
    		}else {
    			vo.setCurStateName("");
    		}	
    		
    		vo.setProductName(SystemUtil.getProductNameById(vo.getProductId()));
    		Vending vending = SystemUtil.getVendingBase(vo.getSiteId());
    		if(vending!=null) {
    			vo.setSiteName(vending.getSiteName());
    		}
		}
		
		return AjaxResult.success(vo);
	}
	
	/**
	 * 获取订单商品货道详情页数据
	 * @param proboxId 
	 * @return	
	 */
	@GetMapping("/getOrderBoxView")
	@ResponseBody
	public AjaxResult getOrderBoxView(String proboxId) {
		//获取订单商品货道信息
		OrderBox orderBox = orderBoxService.selectOrderBoxById(proboxId);
		//组装返回页面数据
		IOrderBoxVo vo = new IOrderBoxVo();
		vo.setProboxId(proboxId);
		if (orderBox != null) {
			BeanUtils.copyProperties(orderBox, vo);
		}
		vo.setOutStateName(SystemUtil.parseBoxState(vo.getOutState()));
		return AjaxResult.success(vo);
	}
	
	
	
	/**
	 * 站点修改库存
	 */
	@PostMapping("/siteInventory")
	@ResponseBody
	public AjaxResult siteInventory(@RequestBody List<VendingLanep> vendingLanepList)
	{   
	    List<Map<String,String>> list = new ArrayList<>();
	    if (!vendingLanepList.isEmpty()) {
	    	for(VendingLanep vendingLanep :vendingLanepList ){
				Map<String,String> map = new HashMap<>();
				String laneSId = vendingLanep.getLaneSId().toString();
				String laneEId = vendingLanep.getLaneEId().toString();
				String curCap = vendingLanep.getCurCap().toString();
				String resetCap = vendingLanep.getResetCap().toString();
				if(vendingLanep.getResetCap()<0) {
					return AjaxResult.error("修正后的数量不能小于0");	
				}
				if(vendingLanep.getResetCap()>vendingLanep.getCapacity()) {
					return AjaxResult.error("修正后的数量不能大于最大库存");	
				}
				map.put("LaneSId", laneSId);
				map.put("LaneEId", laneEId);
				map.put("CurCap", curCap);
				map.put("ResetCap", resetCap);
				list.add(map);
			}
	    	ServerMessage message = serverService.siteInventory(vendingLanepList.get(0).getSiteId(), ShiroUtils.getLoginName(), list);
			if(message.success()) {
				return AjaxResult.success(message.getZbody());
			}else {
				log.error("站点修改库存失败:"+message.toJson()+"时间:"+DateUtils.getTime());
				return AjaxResult.error("站点修改库存失败");
			}
		}
		return AjaxResult.error("站点修改库存数据为空！");	
		
	}
	
	/**
	 * 售货机订单货道修改
	 */
	@PostMapping("/orderLane")
	@ResponseBody
	public AjaxResult orderLane(@RequestBody OrderBox orderBox)
	{
		List<Map<String, String>> laneInfo = new ArrayList<Map<String, String>>();
		Map<String, String> laneInfoMap = new HashMap<String,String>();
		laneInfoMap.put("ProBoxId", orderBox.getProboxId());
		laneInfoMap.put("LsdifferId", orderBox.getLsdifferId());
		laneInfoMap.put("ProductId", orderBox.getProductId());
		laneInfoMap.put("OutState", orderBox.getOutState());
		laneInfo.add(laneInfoMap);
		ServerMessage message = serverService.orderLane(orderBox.getOrderId(),laneInfo , ShiroUtils.getLoginName());
		if(message.success()) {
			return AjaxResult.success();
		}else {
			log.error("售货机订单货道修改失败:"+message.toJson()+"时间:"+DateUtils.getTime());
			return AjaxResult.error("售货机订单货道修改失败");
		}
	}
}
