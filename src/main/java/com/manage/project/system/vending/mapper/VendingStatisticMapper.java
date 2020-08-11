package com.manage.project.system.vending.mapper;

import com.manage.project.system.vending.domain.VendingStatistic;	

/**
 * 售货机业务数据统计 数据层
 * 
 * @author 张佳伟
 * @date 2018-10-23
 */
public interface VendingStatisticMapper 
{

	/**
	 * 根据站点编号查询售货机业务数据统计信息
	 * 
	 * @param siteId
	 * @return
	 */
	public VendingStatistic selectVendingStatisticBySiteId(String siteId);
	
	/**
     * 新增售货机业务数据统计
     * 
     * @param vendingStatistic 售货机业务数据统计信息
     * @return 结果
     */
	public int insertVendingStatistic(VendingStatistic vendingStatistic);

	/**
	 * 根据售货机id删除售货机业务数据
	 * 
	 * @param siteId 售货机id
	 * @return
	 */
	public int deleteVendingStatisticBySiteId(String siteId);
}