package com.manage.project.system.stock.mapper;

import com.manage.project.system.stock.domain.StockInbound;
import com.manage.project.system.stock.vo.StockInboundParamVo;

import java.util.List;	

/**
 * 仓库入库记录 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface StockInboundMapper 
{
	/**
     * 查询仓库入库记录信息
     * 
     * @param logid 仓库入库记录ID
     * @return 仓库入库记录信息
     */
	public StockInbound selectStockInboundById(String logid);
	
	/**
     * 查询仓库入库记录列表
     * 
     * @param stockInbound 仓库入库记录信息
     * @return 仓库入库记录集合
     */
	public List<StockInbound> selectStockInboundList(StockInbound stockInbound);
	
	/**
     * 查询仓库入库记录列表
     * 
     * @param stockInbound 仓库入库记录信息
     * @return 仓库入库记录集合
     */
	public List<StockInbound> selectStockInbound(StockInboundParamVo stockInboundParamVo);
	
	/**
     * 新增仓库入库记录
     * 
     * @param stockInbound 仓库入库记录信息
     * @return 结果
     */
	public int insertStockInbound(StockInbound stockInbound);
	
	/**
     * 修改仓库入库记录
     * 
     * @param stockInbound 仓库入库记录信息
     * @return 结果
     */
	public int updateStockInbound(StockInbound stockInbound);
	
	/**
     * 删除仓库入库记录
     * 
     * @param logid 仓库入库记录ID
     * @return 结果
     */
	public int deleteStockInboundById(String logid);
	
	/**
     * 批量删除仓库入库记录
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteStockInboundByIds(String[] logids);

	/**
	 * 根据入库编号查询入库记录
	 * 
	 * @param winboundId 入库编号
	 * @return 入库记录
	 */
	public StockInbound selectStockInboundByWInboundId(String winboundId);
	
}