package com.manage.project.system.vending.service;

import java.util.List;

import com.manage.project.system.vending.domain.VendingLsdiffer;

public interface IVendingLsdifferService {
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
