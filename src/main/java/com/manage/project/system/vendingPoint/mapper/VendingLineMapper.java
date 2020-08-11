package com.manage.project.system.vendingPoint.mapper;

import com.manage.project.system.vendingPoint.domain.VendingLine;
import java.util.List;	

/**
 * 点位的线路 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface VendingLineMapper 
{
	/**
     * 查询点位的线路信息
     * 
     * @param logid 点位的线路ID
     * @return 点位的线路信息
     */
	public VendingLine selectVendingLineById(String logid);
	
	
	public VendingLine selectVendingLineByLineId(String lineId);
	
	/**
     * 查询点位的线路列表
     * 
     * @param vendingLine 点位的线路信息
     * @return 点位的线路集合
     */
	public List<VendingLine> selectVendingLineList(VendingLine vendingLine);
	
	/**
     * 新增点位的线路
     * 
     * @param vendingLine 点位的线路信息
     * @return 结果
     */
	public int insertVendingLine(VendingLine vendingLine);
	
	/**
     * 修改点位的线路
     * 
     * @param vendingLine 点位的线路信息
     * @return 结果
     */
	public int updateVendingLine(VendingLine vendingLine);
	
	/**
     * 删除点位的线路
     * 
     * @param logid 点位的线路ID
     * @return 结果
     */
	public int deleteVendingLineById(String logid);
	
	/**
     * 批量删除点位的线路
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingLineByIds(String[] logids);

	/**
	 * 查询线路是否已经存在
	 * 
	 * @param vendingLineCode
	 * @return
	 */
	public VendingLine selectVendingLineExist(VendingLine vendingLineCode);
	
}