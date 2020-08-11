/**
 * 
 */
package com.manage.project.system.report.service;

import java.util.List;
import java.util.Map;

import com.manage.project.system.report.vo.RankVo;
import com.manage.project.system.report.vo.SaleAnalyzeVo;

/**
 * 畅销分析服务层接口
 * 
 * @author zhangjiawei
 * @date 2018年10月10日
 * 
 */
public interface ISaleAnalyzeService {

	/**
	 * 查询线路top10
	 * 
	 * @param vo 查询条件
	 * @return 线路top10销售信息
	 */
	@Deprecated
	List<RankVo> selectLineTopTen(SaleAnalyzeVo vo);

	/**
	 * 查询点位top10
	 * 
	 * @param vo 查询条件
	 * @return 点位top10销售信息
	 */
	@Deprecated
	List<RankVo> selectPointTopTen(SaleAnalyzeVo vo);

	/**
	 * 查询产品top10
	 * 
	 * @param vo 查询条件
	 * @return 产品top10销售信息
	 */
	@Deprecated
	List<Map<String, Object>> selectProductTopTen(SaleAnalyzeVo vo);

	/**
	 * 查询线路排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankVo> selectLineRank(SaleAnalyzeVo vo);

	/**
	 * 查询点位排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankVo> selectPointRank(SaleAnalyzeVo vo);

	/**
	 * 查询商品排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankVo> selectProductRank(SaleAnalyzeVo vo);

}
