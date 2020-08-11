package com.manage.project.system.stock.service;

import com.manage.project.system.stock.domain.StockPinbound;
import java.util.List;

/**
 * 仓库采购记录 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IStockPinboundService 
{
	/**
     * 查询仓库采购记录信息
     * 
     * @param logid 仓库采购记录ID
     * @return 仓库采购记录信息
     */
	public StockPinbound selectStockPinboundById(String logid);
	
	/**
     * 查询仓库采购记录列表
     * 
     * @param stockPinbound 仓库采购记录信息
     * @return 仓库采购记录集合
     */
	public List<StockPinbound> selectStockPinboundList(StockPinbound stockPinbound);
	
	/**
     * 新增仓库采购记录
     * 
     * @param stockPinbound 仓库采购记录信息
     * @return 结果
     */
	public int insertStockPinbound(StockPinbound stockPinbound);
	
	/**
     * 修改仓库采购记录
     * 
     * @param stockPinbound 仓库采购记录信息
     * @return 结果
     */
	public int updateStockPinbound(StockPinbound stockPinbound);
		
	/**
     * 删除仓库采购记录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteStockPinboundByIds(String ids);

	/**
	 * 查询商品最近的采购金额
	 * 
	 * @param productId
	 * @return
	 */
	public Float selectBuyPriceRecentlyByProductId(String productId);
	
}
