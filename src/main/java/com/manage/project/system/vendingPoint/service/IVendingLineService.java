package com.manage.project.system.vendingPoint.service;

import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import java.util.List;

/**
 * 点位的线路 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IVendingLineService 
{
	/**
     * 查询点位的线路信息
     * 
     * @param logid 点位的线路ID
     * @return 点位的线路信息
     */
	public VendingLine selectVendingLineById(String logid);
	
	/**
	 * 根据线路id获取线路
	 * @param lineId
	 * @return
	 */
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
     * 删除点位的线路信息
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingLineByIds(String logids);

	/**
	 * 保存线路信息
	 * 
	 * @param vendingLine
	 * @return
	 */
	public AjaxResult saveImportVendingLine(VendingLine vendingLine);
	
}
