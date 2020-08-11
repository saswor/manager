package com.manage.project.system.vending.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.vending.domain.VendingStatistic;
import com.manage.project.system.vending.mapper.VendingStatisticMapper;

/**
 * 售货机业务数据统计 服务层实现
 * 
 * @author 张佳伟
 * @date 2018-10-23
 */
@Service
public class VendingStatisticServiceImpl implements IVendingStatisticService 
{
	
	@Autowired
	private VendingStatisticMapper vendingStatisticMapper;

	/**
	 * 根据站点编号查询售货机业务数据统计信息
	 * 
	 * @param siteId
	 * @return
	 */
	@Override
	public VendingStatistic selectVendingStatisticBySiteId(String siteId) {
		return vendingStatisticMapper.selectVendingStatisticBySiteId(siteId);
	}
	
	
}
