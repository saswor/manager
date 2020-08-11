package com.manage.project.system.product.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.common.support.Convert;
import com.manage.project.system.product.domain.ProductLunder;
import com.manage.project.system.product.mapper.ProductLunderMapper;
import com.manage.project.system.product.vo.ProductLunderVo;

/**
 * 站点货道商品下架 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class ProductLunderServiceImpl implements IProductLunderService 
{
	@Autowired
	private ProductLunderMapper productLunderMapper;

	/**
     * 查询站点货道商品下架信息
     * 
     * @param logid 站点货道商品下架ID
     * @return 站点货道商品下架信息
     */
    @Override
	public ProductLunder selectProductLunderById(String logid)
	{
	    return productLunderMapper.selectProductLunderById(logid);
	}
	
	/**
     * 查询站点货道商品下架列表
     * 
     * @param productLunderVo 站点货道商品下架信息
     * @return 站点货道商品下架集合
     */
	@Override
	public List<ProductLunderVo> selectProductLunderList(ProductLunderVo vo)
	{
	    return productLunderMapper.selectProductLunderVoList(vo);
	}
	
    /**
     * 新增站点货道商品下架
     * 
     * @param productLunder 站点货道商品下架信息
     * @return 结果
     */
	@Override
	public int insertProductLunder(ProductLunder productLunder)
	{
	    return productLunderMapper.insertProductLunder(productLunder);
	}
	
	/**
     * 修改站点货道商品下架
     * 
     * @param productLunder 站点货道商品下架信息
     * @return 结果
     */
	@Override
	public int updateProductLunder(ProductLunder productLunder)
	{
	    return productLunderMapper.updateProductLunder(productLunder);
	}

	/**
     * 删除站点货道商品下架对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProductLunderByIds(String ids)
	{
		return productLunderMapper.deleteProductLunderByIds(Convert.toStrArray(ids));
	}

	/**
     * 查询站点货道商品下架列表
     * 
     * @param productLunderVo 站点货道商品下架信息
     * @return 站点货道商品下架集合
     */
	@Override
	public List<ProductLunderVo> selectProductLunderVoList(ProductLunderVo vo) {
		return productLunderMapper.selectProductLunderVoList(vo);
	}
	
	public int changeProduct() {
		return 1;
	}
}
