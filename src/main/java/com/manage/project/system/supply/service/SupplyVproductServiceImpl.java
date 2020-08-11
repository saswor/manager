package com.manage.project.system.supply.service;

import java.util.List;

import com.manage.project.system.supply.domain.SupplyVproduct;
import com.manage.project.system.supply.mapper.SupplyVproductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.common.support.Convert;

/**
 * 补货站点需要补货的货道商品，根据流水可检查商品的过期时间 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class SupplyVproductServiceImpl implements ISupplyVproductService 
{
	@Autowired
	private SupplyVproductMapper supplyVproductMapper;

	/**
     * 查询补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     * 
     * @param logid 补货站点需要补货的货道商品，根据流水可检查商品的过期时间ID
     * @return 补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     */
    @Override
	public SupplyVproduct selectSupplyVproductById(String logid)
	{
	    return supplyVproductMapper.selectSupplyVproductById(logid);
	}
	
	/**
     * 查询补货站点需要补货的货道商品，根据流水可检查商品的过期时间列表
     * 
     * @param supplyVproduct 补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     * @return 补货站点需要补货的货道商品，根据流水可检查商品的过期时间集合
     */
	@Override
	public List<SupplyVproduct> selectSupplyVproductList(SupplyVproduct supplyVproduct)
	{
	    return supplyVproductMapper.selectSupplyVproductList(supplyVproduct);
	}
	
    /**
     * 新增补货站点需要补货的货道商品，根据流水可检查商品的过期时间
     * 
     * @param supplyVproduct 补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     * @return 结果
     */
	@Override
	public int insertSupplyVproduct(SupplyVproduct supplyVproduct)
	{
	    return supplyVproductMapper.insertSupplyVproduct(supplyVproduct);
	}
	
	/**
     * 修改补货站点需要补货的货道商品，根据流水可检查商品的过期时间
     * 
     * @param supplyVproduct 补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     * @return 结果
     */
	@Override
	public int updateSupplyVproduct(SupplyVproduct supplyVproduct)
	{
	    return supplyVproductMapper.updateSupplyVproduct(supplyVproduct);
	}

	/**
     * 删除补货站点需要补货的货道商品，根据流水可检查商品的过期时间对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSupplyVproductByIds(String ids)
	{
		return supplyVproductMapper.deleteSupplyVproductByIds(Convert.toStrArray(ids));
	}
	
}
