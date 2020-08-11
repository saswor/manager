/**
 * 
 */
package com.manage.project.system.report.mapper;

import java.util.List;

import com.manage.project.system.report.vo.RankVo;
import com.manage.project.system.report.vo.SaleAnalyzeVo;

/**
 * 畅销分析数据层
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
public interface SaleAnalyzeMapper {
	
	/**
	 * 查询产品总共销售量
	 * 
	 * @param vo 查询条件
	 * @return	产品总销售量
	 */
	Integer selectTotalProductSaleNum(SaleAnalyzeVo vo);

	/**
	 * 查询top10
	 * 
	 * @param vo 查询条件
	 * @return	排名
	 */
	List<RankVo> selectTopTen(SaleAnalyzeVo vo);

	/**
	 * 查询当天排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankVo> selectDayRank(SaleAnalyzeVo vo);

	/**
	 * 查询月度,季度,年度排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankVo> selectOtherRank(SaleAnalyzeVo vo);

	/**
	 * 查询当天销售商品总数
	 * 
	 * @param vo
	 * @return
	 */
	Long selectDayTotalProductSaleNum(SaleAnalyzeVo vo);

	/**
	 * 查询月度,季度,年度销售商品总数
	 * 
	 * @param vo
	 * @return
	 */
	Long selectOtherTotalProductSaleNum(SaleAnalyzeVo vo);

}
