package com.manage.project.system.product.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.product.mapper.ProductClassifyMapper;
import com.manage.project.system.product.domain.ProductClassify;
import com.manage.project.system.product.service.IProductClassifyService;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.security.ShiroUtils;

/**
 * 商品分类 服务层实现
 * 
 * @author shicong
 * @date 2018-09-25
 */
@Service
public class ProductClassifyServiceImpl implements IProductClassifyService 
{
	@Autowired
	private ProductClassifyMapper productClassifyMapper;
	@Autowired
	private BussinessCacheService bussinessCacheService;

	/**
     * 查询商品分类信息
     * 
     * @param logid 商品分类ID
     * @return 商品分类信息
     */
    @Override
	public ProductClassify selectProductClassifyById(String logid)
	{
	    return productClassifyMapper.selectProductClassifyById(logid);
	}
	
	/**
     * 查询商品分类列表
     * 
     * @param productClassify 商品分类信息
     * @return 商品分类集合
     */
	@Override
	public List<ProductClassify> selectProductClassifyList(ProductClassify productClassify)
	{	
		return productClassifyMapper.selectProductClassifyList(productClassify);
	}
	
    /**
     * 新增商品分类
     * 
     * @param productClassify 商品分类信息
     * @return 结果
     */
	@Override
	public int insertProductClassify(ProductClassify productClassify)
	{
		productClassify.setLogid(UUID.randomUUID().toString());
		String corpId = ShiroUtils.getUser().getCorpId();
		productClassify.setCorpId(corpId);
		productClassify.setClassifyId(SystemUtil.getSeqId(corpId, "as_product_classify"));
		productClassify.setCreateTime(DateUtils.getTime());
		int result = productClassifyMapper.insertProductClassify(productClassify);
		bussinessCacheService.initProductClass();
		return result;
	}
	
	/**
     * 修改商品分类
     * 
     * @param productClassify 商品分类信息
     * @return 结果
     */
	@Override
	public int updateProductClassify(ProductClassify productClassify)
	{
	    int result = productClassifyMapper.updateProductClassify(productClassify);
	    bussinessCacheService.initProductClass();
		return result;
	}

	/**
     * 删除商品分类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProductClassifyByIds(String ids)
	{
		int result = productClassifyMapper.deleteProductClassifyByIds(Convert.toStrArray(ids));
		bussinessCacheService.initProductClass();
		return result;
	}
	
	/**
	 * 根据商品分类名称模糊查询
	 * @param classifyName
	 * @return
	 */
	public List<ProductClassify> selectProductClassifyByName(String classifyName) {
		return productClassifyMapper.selectProductClassifyByName(classifyName);
	}
	
}
