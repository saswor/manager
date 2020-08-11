package com.manage.project.system.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.stock.domain.StockInbound;
import com.manage.project.system.stock.domain.StockPinbound;
import com.manage.project.system.stock.service.IStockInboundService;
import com.manage.project.system.stock.vo.StockInboundParamVo;
import com.manage.project.system.stock.vo.StockInboundVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 仓库入库记录 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/stockInbound")
public class StockInboundController extends BaseController
{
	@Autowired
	private IStockInboundService stockInboundService;
	
	/**
	 * 查询仓库入库记录列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(StockInbound stockInbound)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			stockInbound.setCorpId("");
		} else {
			stockInbound.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		stockInbound.setCorpId(SystemUtil.getCorpId());
        List<StockInbound> list = stockInboundService.selectStockInboundList(stockInbound);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 查询仓库入库记录列表
	 */
	@GetMapping("/stockInlist")
	@ResponseBody
	public AjaxResult stockInlist(StockInboundParamVo stockInboundParamVo)
	{
		startPage();
		stockInboundParamVo.setCorpId(SystemUtil.getCorpId());
        List<StockInbound> list = stockInboundService.selectStockInbound(stockInboundParamVo);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存仓库入库记录
	 */
	@Log(title = "仓库入库记录", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody StockInbound stockInbound)
	{		
		return toAjax(stockInboundService.insertStockInbound(stockInbound));
	}
	
	/**
	 * 修改保存仓库入库记录
	 */
	@Log(title = "仓库入库记录", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody StockInbound stockInbound)
	{		
		return toAjax(stockInboundService.updateStockInbound(stockInbound));
	}
	
	/**
	 * 删除仓库入库记录
	 */
	@Log(title = "删除仓库入库记录", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		return toAjax(stockInboundService.deleteStockInboundByIds(ids.getIds()));
	}
	
	@Log(title = "仓库入库记录", action = BusinessType.UPDATE)
	@PostMapping("/submitInbound")
	@ResponseBody
	//public AjaxResult submitInbound(@RequestBody List<StockPinbound> stockPinbounds)
	public AjaxResult submitInbound(@RequestBody StockInboundVo vo)
	{				
		if(StringUtils.isNotEmpty(vo.getwInboundId())) {
			StockInbound stockInbound = stockInboundService.selectStockInboundByWInboundId(vo.getwInboundId());
			if("2".equals(stockInbound.getCurState())) {
				return AjaxResult.error("当前入库单已入库,请不要重复入库");
			}
		}
		List<StockPinbound> stockPinbounds = vo.getStockPinbounds();
		if(StringUtils.isEmpty(stockPinbounds)) {
			return AjaxResult.error("请至少入库一种商品");
		}
		for (StockPinbound stockPinbound : stockPinbounds) {
			if(stockPinbound.getBuyNum()<stockPinbound.getPnum()) {
				return AjaxResult.error("入库数量不能大于采购数量");
			}
		}
		try {
			return toAjax(stockInboundService.submitInbound(stockPinbounds));
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("入库失败");
		}
	}
	
	/**
	 * 查询仓库入库记录列表
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(String wInboundId)
	{
        StockInbound stockInbound = stockInboundService.selectStockInboundByWInboundId(wInboundId);
        //查询关联入库单
        StockInbound stockInboundSelect = new StockInbound();
        stockInboundSelect.setWpurchaseId(stockInbound.getWpurchaseId());
        List<StockInbound> stockInboundList = stockInboundService.selectStockInboundList(stockInboundSelect);
		for (StockInbound stockInbound2 : stockInboundList) {
			if(!stockInbound.getWinboundId().equals(stockInbound2.getWinboundId())&&!stockInbound.getInboundType().equals(stockInbound2.getInboundType())) {
				stockInbound.setLinkWinboundId(stockInbound2.getWinboundId());
			}
		}
        return AjaxResult.success(stockInbound);
	}
	
	@Log(title = "仓库入库冲正", action = BusinessType.UPDATE)
	@PostMapping("/releaseInbound")
	@ResponseBody
	//public AjaxResult submitInbound(@RequestBody List<StockPinbound> stockPinbounds)
	public AjaxResult releaseInbound(@RequestBody List<StockPinbound> vo)
	{				
		if(StringUtils.isEmpty(vo)) {
			return AjaxResult.error("请至少入库一种商品");
		}
		try {
			int flag = stockInboundService.releaseInbound(vo);
			if(flag==1)
				return AjaxResult.success("冲正成功");
			else if(flag==5)
				return AjaxResult.error("请至少入库一种商品");
			else if(flag==4)
				return AjaxResult.error("当前仓库库存数少于冲正数");
			else if(flag==3)
				return AjaxResult.error("仓库库存商品不存在");
			else if(flag==2)
				return AjaxResult.error("冲正原始入库单不存在");
			else if(flag==6)
				return AjaxResult.error("不能对冲正单进行冲正");
			else if(flag==7)
				return AjaxResult.error("该入库单已经冲正过");
			else
				return AjaxResult.error("未知错误");
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("冲正失败");
		}
	}
}
