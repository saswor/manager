package com.manage.project.system.vending.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.project.system.vending.domain.VendingLsdiffer;
import com.manage.project.system.vending.mapper.VendingLsdifferMapper;
/**
 * 售货机库存差异服务层实现
 * @author ldh
 * @title VendingLsdifferServiceImpl
 * @description TODO
 * @time 2019年1月17日下午2:29:09
 */
@Service
public class VendingLsdifferServiceImpl implements IVendingLsdifferService{
	@Autowired
	private VendingLsdifferMapper vendingLsdifferMapper;
	/**
	 * 查询售货机库存差异信息
	 */
	@Override
	public VendingLsdiffer selectVendingLsdifferById(String lsdifferId) {
		return vendingLsdifferMapper.selectVendingLsdifferById(lsdifferId);
	}
    /**
     * 查询售货机库存差异列表
     */
	@Override
	public List<VendingLsdiffer> selectVendingLsdifferList(VendingLsdiffer vendingLsdiffer) {
		return vendingLsdifferMapper.selectVendingLsdifferList(vendingLsdiffer);
	}
}
