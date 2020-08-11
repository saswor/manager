package com.manage.project.system.vending.service;

import java.util.List;

import com.manage.project.system.vending.domain.VendingStatistic;

/**
 * 售货机业务数据统计 服务层
 * 
 * @author 张佳伟
 * @date 2018-10-23
 */
public interface IVendingStatisticService 
{

	/**
	 * 根据站点编号查询售货机业务数据统计信息
	 * 
	 * @param siteId
	 * @return
	 */
	public VendingStatistic selectVendingStatisticBySiteId(String siteId);
	
}
