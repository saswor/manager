package com.manage.project.system.stock.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.stock.mapper.StockProductMapper;
import com.manage.project.system.stock.domain.StockProduct;
import com.manage.project.system.stock.service.IStockProductService;
import com.manage.common.support.Convert;

/**
 * 商品库存量 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class StockProductServiceImpl implements IStockProductService 
{
	@Autowired
	private StockProductMapper stockProductMapper;

	/**
     * 查询商品库存量信息
     * 
     * @param logid 商品库存量ID
     * @return 商品库存量信息
     */
    @Override
	public StockProduct selectStockProductById(String logid)
	{
	    return stockProductMapper.selectStockProductById(logid);
	}
	
	/**
     * 查询商品库存量列表
     * 
     * @param stockProduct 商品库存量信息
     * @return 商品库存量集合
     */
	@Override
	public List<StockProduct> selectStockProductList(StockProduct stockProduct)
	{
	    return stockProductMapper.selectStockProductList(stockProduct);
	}
	
    /**
     * 新增商品库存量
     * 
     * @param stockProduct 商品库存量信息
     * @return 结果
     */
	@Override
	public int insertStockProduct(StockProduct stockProduct)
	{
	    return stockProductMapper.insertStockProduct(stockProduct);
	}
	
	/**
     * 修改商品库存量
     * 
     * @param stockProduct 商品库存量信息
     * @return 结果
     */
	@Override
	public int updateStockProduct(StockProduct stockProduct)
	{
	    return stockProductMapper.updateStockProduct(stockProduct);
	}

	/**
     * 删除商品库存量对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteStockProductByIds(String ids)
	{
		return stockProductMapper.deleteStockProductByIds(Convert.toStrArray(ids));
	}
	
}
