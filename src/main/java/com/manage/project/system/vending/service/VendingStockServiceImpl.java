package com.manage.project.system.vending.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.common.support.Convert;
import com.manage.project.system.vending.domain.VendingStock;
import com.manage.project.system.vending.mapper.VendingStockMapper;
import com.manage.project.system.vending.vo.UnderProductVo;

/**
 * 售货机商品库存，提供下单检查初步检查库存情况。 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class VendingStockServiceImpl implements IVendingStockService 
{
	@Autowired
	private VendingStockMapper vendingStockMapper;

	/**
     * 查询售货机商品库存，提供下单检查初步检查库存情况。信息
     * 
     * @param logid 售货机商品库存，提供下单检查初步检查库存情况。ID
     * @return 售货机商品库存，提供下单检查初步检查库存情况。信息
     */
    @Override
	public VendingStock selectVendingStockById(String logid)
	{
	    return vendingStockMapper.selectVendingStockById(logid);
	}
	
	/**
     * 查询售货机商品库存，提供下单检查初步检查库存情况。列表
     * 
     * @param vendingStock 售货机商品库存，提供下单检查初步检查库存情况。信息
     * @return 售货机商品库存，提供下单检查初步检查库存情况。集合
     */
	@Override
	public List<VendingStock> selectVendingStockList(VendingStock vendingStock)
	{
	    return vendingStockMapper.selectVendingStockList(vendingStock);
	}
	
    /**
     * 新增售货机商品库存，提供下单检查初步检查库存情况。
     * 
     * @param vendingStock 售货机商品库存，提供下单检查初步检查库存情况。信息
     * @return 结果
     */
	@Override
	public int insertVendingStock(VendingStock vendingStock)
	{
	    return vendingStockMapper.insertVendingStock(vendingStock);
	}
	
	/**
     * 修改售货机商品库存，提供下单检查初步检查库存情况。
     * 
     * @param vendingStock 售货机商品库存，提供下单检查初步检查库存情况。信息
     * @return 结果
     */
	@Override
	public int updateVendingStock(VendingStock vendingStock)
	{
	    return vendingStockMapper.updateVendingStock(vendingStock);
	}

	/**
     * 删除售货机商品库存，提供下单检查初步检查库存情况。对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingStockByIds(String ids)
	{
		return vendingStockMapper.deleteVendingStockByIds(Convert.toStrArray(ids));
	}

	/**
     * 根据id查询售货机库存列表
     * 
     * @param ids 多个查询id
     * @return 售货机商品库存信息
     */
	@Override
	public List<VendingStock> selectVendingStockByIds(String[] ids) {
		return vendingStockMapper.selectVendingStockByIds(ids);
	}

	/**
	 * 查询要下架商品的站点
	 * 
	 * @param vo
	 * @return
	 */
	@Override
	public List<VendingStock> selectOnlineVendingStockList(UnderProductVo vo) {
		return vendingStockMapper.selectOnlineVendingStockList(vo);
	}
	
}
