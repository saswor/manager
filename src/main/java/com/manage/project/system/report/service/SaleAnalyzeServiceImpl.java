/**
 * 
 */
package com.manage.project.system.report.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.report.mapper.SaleAnalyzeMapper;
import com.manage.project.system.report.vo.RankVo;
import com.manage.project.system.report.vo.SaleAnalyzeVo;
import com.manage.project.system.util.SystemUtil;

/**
 * 畅销分析服务层实现
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
@Service
public class SaleAnalyzeServiceImpl implements ISaleAnalyzeService{

	@Autowired
	private SaleAnalyzeMapper saleAnalyzeMapper;
	
	/**
	 * 查询线路top10
	 * 
	 * @param vo 查询条件
	 * @return 线路top10销售信息
	 */
	@Override
	public List<RankVo> selectLineTopTen(SaleAnalyzeVo vo) {
		return saleAnalyzeMapper.selectTopTen(vo);
	}

	/**
	 * 查询点位top10
	 * 
	 * @param vo 查询条件
	 * @return 线路top10销售信息
	 */
	@Override
	public List<RankVo> selectPointTopTen(SaleAnalyzeVo vo) {
		return saleAnalyzeMapper.selectTopTen(vo);
	}

/*	@Transactional(readOnly=true)
	@Override
	public List<Map<String,Object>> selectProductTopTen(SaleAnalyzeVo vo) {		
		List<RankVo> list = saleAnalyzeMapper.selectProductTopTen(vo);
		RankVo total=saleAnalyzeMapper.selectTotalProductSaleMoney(vo);
		double totalSaleMoney;
		if(total!=null) {
			totalSaleMoney = total.getSaleMoney();
		}else {
			totalSaleMoney=0;
		}
		for (RankVo rankVo : list) {
			if(rankVo!=null) {
				double saleMoney = rankVo.getSaleMoney();
				if(totalSaleMoney!=0) {
					rankVo.setPercent(saleMoney/totalSaleMoney);
				}
			}	
		}
		return list;
	}*/
	
	/**
	 * 查询商品top10
	 * 
	 * @param vo 查询条件
	 * @return 商品top10销售信息
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Map<String,Object>> selectProductTopTen(SaleAnalyzeVo vo) {		
		List<RankVo> list = saleAnalyzeMapper.selectTopTen(vo);
		List<Map<String,Object>> arrayList = new ArrayList<Map<String,Object>>();
//		Integer totalSaleNum=saleAnalyzeMapper.selectTotalProductSaleNum(vo);
		//设置销售额百分比
		for (RankVo rankVo : list) {
			if(rankVo!=null) {
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("name", rankVo.getName());
				map.put("value", rankVo.getSaleNum());
				arrayList.add(map);
//				int saleNum = rankVo.getSaleNum();
//				if(totalSaleNum!=0) {
//					rankVo.setPercent(saleNum/totalSaleNum*100);
//					Map<String, Object> map = new HashMap<String,Object>();
//					map.put("name", rankVo.getName());
//					DecimalFormat df = new DecimalFormat("0.0");
//					map.put("value", df.format(rankVo.getPercent()));
//					arrayList.add(map);
//				}
			}	
		}
		
		return arrayList;
	}

	/**
	 * 查询排名信息
	 * 
	 */
	private List<RankVo> selectRank(SaleAnalyzeVo vo) {
		vo.setCorpId(SystemUtil.getCorpId());
		LocalDate now = LocalDate.now();
		LocalDate lastDay = now.minusDays(1);
		List<RankVo> list = null;
		switch (vo.getDateType()) {
		//当天排名,获取当天日期
		case "0":
			vo.setYear(now.getYear());
			vo.setMonth(now.getMonthValue());
			vo.setDay(now.getDayOfMonth());
			list = saleAnalyzeMapper.selectDayRank(vo);
			break;
		//月度季度年度排名,获取前一天记录
		case "1":
			vo.setYear(lastDay.getYear());
			vo.setMonth(lastDay.getMonthValue());
			list = saleAnalyzeMapper.selectOtherRank(vo);
			break;
		case "2":
			vo.setYear(lastDay.getYear());
			vo.setQuarter((lastDay.getMonthValue()-1)/3+1);
			list = saleAnalyzeMapper.selectOtherRank(vo);
			break;
		case "3":
			vo.setYear(lastDay.getYear());
			list = saleAnalyzeMapper.selectOtherRank(vo);
			break;
		default:
			vo.setYear(now.getYear());
			vo.setMonth(now.getMonthValue());
			vo.setDay(now.getDayOfMonth());
			list = saleAnalyzeMapper.selectDayRank(vo);
			break;
		}
		return list;
	}
	
	/**
	 * 查询商品总计销售量
	 * 
	 * @param vo
	 * @return
	 */
	private Long selectTotalProductSaleNum(SaleAnalyzeVo vo) {
		if("0".equals(vo.getDateType())) {
			//查询当天
			return saleAnalyzeMapper.selectDayTotalProductSaleNum(vo);
		}else {
			//查询月度季度年度
			return saleAnalyzeMapper.selectOtherTotalProductSaleNum(vo);
		}
		
	}

	/**
	 * 查询线路排名
	 */
	@Override
	public List<RankVo> selectLineRank(SaleAnalyzeVo vo) {
		return selectRank(vo);
	}

	/**
	 * 查询点位排名
	 */
	@Override
	public List<RankVo> selectPointRank(SaleAnalyzeVo vo) {
		return selectRank(vo);
	}

	@Override
	@Transactional(readOnly=true)
	public List<RankVo> selectProductRank(SaleAnalyzeVo vo) {
		List<RankVo> list = selectRank(vo);
		//商品需要查询当前维度累计销售量
		Long sumSaleNum = selectTotalProductSaleNum(vo);
		if(sumSaleNum==null||sumSaleNum==0) {
			return list;
		}
		//计算销售额百分比
		for (RankVo rankVo : list) {
			if(rankVo!=null) {
				int saleNum = rankVo.getSaleNum();
				double percent = (double)saleNum/sumSaleNum;
				rankVo.setPercent(percent);
			}	
		}
		return list;
	}
}
