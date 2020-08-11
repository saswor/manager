package com.manage.project.system.index.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.CacheUtils;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.common.Constant;
import com.manage.project.system.index.service.IndexService;
import com.manage.project.system.index.vo.OneMonthReviewVo;
import com.manage.project.system.index.vo.OperateReviewVo;

/**
 * 首页对外接口
 * @author xufeng
 *
 */
@Controller
@RequestMapping("/system/interface/index")
public class IndexInterfaceController extends BaseController  {
	
	@Autowired
	private IndexService indexService;
	
	/**
	 * 发送首页概览
	 * @param profit
	 * @param saleMoney
	 * @param saleNum
	 * @param corpId
	 * @throws IOException
	 */
	private void sendIndexOperateReview(Float profit, Float saleMoney, int saleNum, String corpId) throws IOException {
		// 更新首页总览
		OperateReviewVo operateReviewVo = (OperateReviewVo)CacheUtils.get(Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_+corpId);	
		Float totalProfit = Float.valueOf(operateReviewVo.getTotalProfit()) + profit;
		Float totalMoney = Float.valueOf(operateReviewVo.getTotalSale()) + saleMoney;
		int totalsaleNum = Integer.valueOf(operateReviewVo.getTotalSaleNum()) + saleNum;
		operateReviewVo.setTotalProfit(totalProfit.toString());
		operateReviewVo.setTotalSale(totalMoney.toString());
		operateReviewVo.setTotalSaleNum(String.valueOf(totalsaleNum));
		CacheUtils.put(Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_+corpId, operateReviewVo);
		indexService.sendOperateReviewToAll(corpId);
	}
	
	/**
	 * 更新首页近一月总览数据，放入缓存
	 * @param profit
	 * @param saleMoney
	 * @param saleNum
	 * @param corpId
	 * @throws IOException
	 */
	private void sendOneMonth(Float profit, Float saleMoney, int saleNum, String corpId) throws IOException {
		List<OneMonthReviewVo> oneMonthList = (List<OneMonthReviewVo>)CacheUtils.get(Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+corpId);
		for (OneMonthReviewVo m : oneMonthList) {
			if (m.getType() == Constant.INDEX_DATA_TYPE_2_1) {
				Float totalProfitMonth = Float.valueOf(m.getTotalProfit()) + profit;
				Float totalMoneyMonth = Float.valueOf(m.getTotalSale()) + saleMoney;
				int totalsaleNumMonth = Integer.valueOf(m.getTotalSaleNum()) + saleNum;
				m.setTotalProfit(totalProfitMonth.toString());
				m.setTotalSale(totalMoneyMonth.toString());
				m.setTotalSaleNum(String.valueOf(totalsaleNumMonth));
				CacheUtils.put(Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+corpId, oneMonthList);
			}
		}
		indexService.sendOneMonthToAll(corpId);
	}
	
	/**
	 * 接收订单接口，用于刷新首页今日汇总数据，更新缓存
	 * @param profit	// 净利润
	 * @param saleMoney	// 销售额
	 * @param saleNum	// 数量
	 * @throws IOException 
	 */
	@PostMapping("/insertOrderInterface")
    @ResponseBody
	public AjaxResult insertOrder(Float profit, Float saleMoney, int saleNum, String corpId) throws IOException {
		// 更新首页总览
		this.sendIndexOperateReview(profit, saleMoney, saleNum, corpId);
		if (!corpId.equals(Constant.ZHILAI_CORP_ID)) {	// 如果不是宇宙星空发送的，则给宇宙星空用户发送一次推送首页数据
			this.sendIndexOperateReview(profit, saleMoney, saleNum, Constant.ZHILAI_CORP_ID);
		}
		
		// 更新首页近一月总览数据，放入缓存
		this.sendOneMonth(profit, saleMoney, saleNum, corpId);
		if (!corpId.equals(Constant.ZHILAI_CORP_ID)) {	// 如果不是宇宙星空发送的，则给宇宙星空用户发送一次推送首页数据
			this.sendOneMonth(profit, saleMoney, saleNum, Constant.ZHILAI_CORP_ID);
		}
		
		return AjaxResult.success();
	}
}
