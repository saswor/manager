package com.manage.project.system.product.mapper;

import java.util.List;

import com.manage.project.system.product.domain.ProductVunder;

/**
 * 下架站点信息 数据层
 * 
 * @author zhangjiawei
 *
 */
public interface ProductVunderMapper {

	/**
	 * 批量插入下架站点信息
	 * 
	 * @param productVunderList
	 * @return
	 */
	int insertProductVunderBatch(List<ProductVunder> productVunderList);

	/**
	 * 查询下架站点信息列表
	 * 
	 * @param productVunderSelect
	 * @return
	 */
	List<ProductVunder> selectProductVunderList(ProductVunder productVunderSelect);

	/**
	 * 更新下架站点信息列表
	 * 
	 * @param productVunder
	 * @return
	 */
	int updateProductVunder(ProductVunder productVunder);

}
