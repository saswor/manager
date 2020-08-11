package com.manage.project.system.stock.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.stock.mapper.StockPpurchaseMapper;
import com.manage.project.system.stock.domain.StockPpurchase;
import com.manage.project.system.stock.service.IStockPpurchaseService;
import com.manage.common.support.Convert;

/**
 * 仓库采购记录 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class StockPpurchaseServiceImpl implements IStockPpurchaseService 
{
	@Autowired
	private StockPpurchaseMapper stockPpurchaseMapper;

	/**
     * 查询仓库采购记录信息
     * 
     * @param logid 仓库采购记录ID
     * @return 仓库采购记录信息
     */
    @Override
	public StockPpurchase selectStockPpurchaseById(String logid)
	{
	    return stockPpurchaseMapper.selectStockPpurchaseById(logid);
	}
	
	/**
     * 查询仓库采购记录列表
     * 
     * @param stockPpurchase 仓库采购记录信息
     * @return 仓库采购记录集合
     */
	@Override
	public List<StockPpurchase> selectStockPpurchaseList(StockPpurchase stockPpurchase)
	{
	    return stockPpurchaseMapper.selectStockPpurchaseList(stockPpurchase);
	}
	
    /**
     * 新增仓库采购记录
     * 
     * @param stockPpurchase 仓库采购记录信息
     * @return 结果
     */
	@Override
	public int insertStockPpurchase(StockPpurchase stockPpurchase)
	{
	    return stockPpurchaseMapper.insertStockPpurchase(stockPpurchase);
	}
	
	/**
     * 修改仓库采购记录
     * 
     * @param stockPpurchase 仓库采购记录信息
     * @return 结果
     */
	@Override
	public int updateStockPpurchase(StockPpurchase stockPpurchase)
	{
	    return stockPpurchaseMapper.updateStockPpurchase(stockPpurchase);
	}

	/**
     * 删除仓库采购记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteStockPpurchaseByIds(String ids)
	{
		return stockPpurchaseMapper.deleteStockPpurchaseByIds(Convert.toStrArray(ids));
	}
	
}
