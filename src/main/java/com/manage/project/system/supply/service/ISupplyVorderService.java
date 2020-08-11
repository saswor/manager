package com.manage.project.system.supply.service;

import com.manage.project.system.supply.domain.SupplyVorder;
import com.manage.project.system.supply.vo.SupplyOrderTmpVo;

import java.util.List;

/**
 * 站点补货记录信息
 * @author xufeng
 *
 */
public interface ISupplyVorderService 
{
	/**
     * 查询补货记录信息
     * 
     * @param SupplyVorder 
     * @return
     */
	public List<SupplyVorder> selectSupplyVorderList(SupplyVorder supplyVorder);
	
	/**
	 * 根据站点id查询补货信息
	 * @param siteId
	 * @return
	 */
	public List<SupplyOrderTmpVo> selectSupplyVorderBySiteId(String siteId, String supplyFTime, String supplierId);
}
