package com.manage.project.system.supply.mapper;

import com.manage.project.system.supply.domain.SupplyVorder;
import com.manage.project.system.supply.vo.SupplyOrderTmpVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 补货记录信息dao
 * 
 * @author xufeng
 * @date 2018-12-22
 */
@Component
public interface SupplyVorderMapper 
{
	/**
     * 查询补货记录信息
     * 
     * @param SupplyVorder 
     * @return
     */
	public List<SupplyVorder> selectSupplyVorderList(SupplyVorder supplyVorder);
	
	/**
	 * 批量插入站点补货记录
	 * 
	 * @param supplyVorderList
	 */
	int insertSupplyVorderBatch(List<SupplyVorder> supplyVorderList);
	
	/**
	 * 根据站点id查询补货信息
	 * @param siteId
	 * @return
	 */
	public List<SupplyOrderTmpVo> selectSupplyVorderBySiteId(@Param("siteId") String siteId, @Param("supplyFTime") String supplyFTime, @Param("supplierId") String supplierId);

	/**
	 * 修改站点补货单
	 * 
	 * @param supplyVorder
	 */
	public int updateSupplyVorder(SupplyVorder supplyVorder);
}
