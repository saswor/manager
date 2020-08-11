package com.manage.project.system.supply.service;

import com.manage.project.system.supply.domain.SupplyConfig;

import java.util.List;

/**
 * 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface ISupplyConfigService 
{
	/**
     * 查询商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     * 
     * @param logid 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。ID
     * @return 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     */
	public SupplyConfig selectSupplyConfigById(String logid);
	
	/**
     * 查询商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。列表
     * 
     * @param supplyConfig 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     * @return 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。集合
     */
	public List<SupplyConfig> selectSupplyConfigList(SupplyConfig supplyConfig);
	
	/**
     * 新增商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
     * 
     * @param supplyConfig 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     * @return 结果
     */
	public int insertSupplyConfig(SupplyConfig supplyConfig);
	
	/**
     * 修改商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
     * 
     * @param supplyConfig 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     * @return 结果
     */
	public int updateSupplyConfig(SupplyConfig supplyConfig);
		
	/**
     * 删除商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyConfigByIds(String ids);

	public SupplyConfig selectSupplyConfigBySupplyId(String supplyId);
	
}
