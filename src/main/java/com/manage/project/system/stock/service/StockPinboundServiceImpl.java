package com.manage.project.system.stock.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.stock.mapper.StockPinboundMapper;
import com.manage.project.system.stock.domain.StockPinbound;
import com.manage.project.system.stock.service.IStockPinboundService;
import com.manage.common.support.Convert;

/**
 * 仓库采购记录 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class StockPinboundServiceImpl implements IStockPinboundService 
{
	@Autowired
	private StockPinboundMapper stockPinboundMapper;

	/**
     * 查询仓库采购记录信息
     * 
     * @param logid 仓库采购记录ID
     * @return 仓库采购记录信息
     */
    @Override
	public StockPinbound selectStockPinboundById(String logid)
	{
	    return stockPinboundMapper.selectStockPinboundById(logid);
	}
	
	/**
     * 查询仓库采购记录列表
     * 
     * @param stockPinbound 仓库采购记录信息
     * @return 仓库采购记录集合
     */
	@Override
	public List<StockPinbound> selectStockPinboundList(StockPinbound stockPinbound)
	{
	    return stockPinboundMapper.selectStockPinboundList(stockPinbound);
	}
	
    /**
     * 新增仓库采购记录
     * 
     * @param stockPinbound 仓库采购记录信息
     * @return 结果
     */
	@Override
	public int insertStockPinbound(StockPinbound stockPinbound)
	{
	    return stockPinboundMapper.insertStockPinbound(stockPinbound);
	}
	
	/**
     * 修改仓库采购记录
     * 
     * @param stockPinbound 仓库采购记录信息
     * @return 结果
     */
	@Override
	public int updateStockPinbound(StockPinbound stockPinbound)
	{
	    return stockPinboundMapper.updateStockPinbound(stockPinbound);
	}

	/**
     * 删除仓库采购记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteStockPinboundByIds(String ids)
	{
		return stockPinboundMapper.deleteStockPinboundByIds(Convert.toStrArray(ids));
	}

	/**
	 * 查询商品最近的采购金额
	 * 
	 * @param productId
	 * @return
	 */
	@Override
	public Float selectBuyPriceRecentlyByProductId(String productId) {
		return stockPinboundMapper.selectBuyPriceRecentlyByProductId(productId);
	}
	
}
