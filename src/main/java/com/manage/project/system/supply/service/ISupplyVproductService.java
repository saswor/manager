package com.manage.project.system.supply.service;

import com.manage.project.system.supply.domain.SupplyVproduct;

import java.util.List;

/**
 * 补货站点需要补货的货道商品，根据流水可检查商品的过期时间 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface ISupplyVproductService 
{
	/**
     * 查询补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     * 
     * @param logid 补货站点需要补货的货道商品，根据流水可检查商品的过期时间ID
     * @return 补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     */
	public SupplyVproduct selectSupplyVproductById(String logid);
	
	/**
     * 查询补货站点需要补货的货道商品，根据流水可检查商品的过期时间列表
     * 
     * @param supplyVproduct 补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     * @return 补货站点需要补货的货道商品，根据流水可检查商品的过期时间集合
     */
	public List<SupplyVproduct> selectSupplyVproductList(SupplyVproduct supplyVproduct);
	
	/**
     * 新增补货站点需要补货的货道商品，根据流水可检查商品的过期时间
     * 
     * @param supplyVproduct 补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     * @return 结果
     */
	public int insertSupplyVproduct(SupplyVproduct supplyVproduct);
	
	/**
     * 修改补货站点需要补货的货道商品，根据流水可检查商品的过期时间
     * 
     * @param supplyVproduct 补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     * @return 结果
     */
	public int updateSupplyVproduct(SupplyVproduct supplyVproduct);
		
	/**
     * 删除补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyVproductByIds(String ids);
	
}
