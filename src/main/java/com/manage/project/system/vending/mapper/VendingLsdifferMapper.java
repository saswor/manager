package com.manage.project.system.vending.mapper;

import java.util.List;

import com.manage.project.system.vending.domain.VendingLsdiffer;	

/**
 * 售货机库存差异数据层
 * @author ldh
 * @title VendingLsdifferMapper
 * @description TODO
 * @time 2019年1月17日上午10:14:49
 */
public interface VendingLsdifferMapper 
{
	/**
     * 查询售货机库存差异信息
     * 
     * @param lsdifferId 货道差异编号
     * @return 售货机库存差异信息
     */
	public VendingLsdiffer selectVendingLsdifferById(String lsdifferId);
	
	/**
     * 查询售货机库存差异列表
     * 
     * @param VendingLsdiffer 售货机库存差异
     * @return 售货机库存差异集合
     */
	public List<VendingLsdiffer> selectVendingLsdifferList(VendingLsdiffer vendingLsdiffer);
}