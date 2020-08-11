package com.manage.project.system.product.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.common.support.Convert;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.product.domain.ProductOnline;
import com.manage.project.system.product.mapper.ProductOnlineMapper;
import com.manage.project.system.product.vo.ProductOnlineVo;

/**
 * 记录在线购买的商品 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class ProductOnlineServiceImpl implements IProductOnlineService 
{
	@Autowired
	private ProductOnlineMapper productOnlineMapper;

	/**
     * 查询记录在线购买的商品信息
     * 
     * @param logid 记录在线购买的商品ID
     * @return 记录在线购买的商品信息
     */
    @Override
	public ProductOnline selectProductOnlineById(String logid)
	{
	    return productOnlineMapper.selectProductOnlineById(logid);
	}
	
	/**
     * 查询记录在线购买的商品列表
     * 
     * @param productOnline 记录在线购买的商品信息
     * @return 记录在线购买的商品集合
     */
	@Override
	public List<ProductOnlineVo> selectProductOnlineList(ProductOnlineVo vo)
	{
	    return productOnlineMapper.selectProductOnlineList(vo);
	}
	
    /**
     * 新增记录在线购买的商品
     * 
     * @param productOnline 记录在线购买的商品信息
     * @return 结果
     */
	@Override
	public int insertProductOnline(ProductOnline productOnline)
	{
	    return productOnlineMapper.insertProductOnline(productOnline);
	}
	
	/**
     * 修改记录在线购买的商品
     * 
     * @param productOnline 记录在线购买的商品信息
     * @return 结果
     */
	@Override
	public int updateProductOnline(ProductOnline productOnline)
	{
	    return productOnlineMapper.updateProductOnline(productOnline);
	}

	/**
     * 删除记录在线购买的商品对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProductOnlineByIds(String ids)
	{
		return productOnlineMapper.deleteProductOnlineByIds(Convert.toStrArray(ids));
	}
	
}
