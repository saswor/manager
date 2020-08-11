package com.manage.project.system.supply.mapper;

import com.manage.project.system.supply.domain.SupplyConfig;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Component
public interface SupplyConfigMapper 
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
	 * 根据id更新最大容量
	 * @param supplyConfig
	 * @return
	 */
	public int updateSupplyConfigMax(SupplyConfig supplyConfig);
	
	
	/**
     * 删除商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
     * 
     * @param logid 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。ID
     * @return 结果
     */
	public int deleteSupplyConfigById(String logid);
	
	/**
     * 批量删除商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyConfigByIds(String[] logids);

	public SupplyConfig selectSupplyConfigBySupplyId(String supplyId);

	/**
	 * 根据线路查询补货记录
	 * 
	 * @param lineId
	 */
	public SupplyConfig selectSupplyConfigByLineId(String lineId);
	
}