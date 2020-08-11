package com.manage.project.system.supply.mapper;

import com.manage.project.system.supply.domain.SupplyVproduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 补货站点需要补货的货道商品，根据流水可检查商品的过期时间 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Component
public interface SupplyVproductMapper 
{
	/**
     * 查询补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     * 
     * @param logid 补货站点需要补货的货道商品，根据流水可检查商品的过期时间ID
     * @return 补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
     */
	public SupplyVproduct selectSupplyVproductById(String logid);

	/**
	 * 查询补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
	 *
	 * @param logid 补货站点需要补货的货道商品，根据流水可检查商品的过期时间ID
	 * @return 补货站点需要补货的货道商品，根据流水可检查商品的过期时间信息
	 */
	public SupplyVproduct selectSupplyVproductByVpId(SupplyVproduct supplyVproduct);

	/**
	 * 统计补货订单下面的总补货量、总实际补货量、总销量统计信息
	 * @param supplyVproduct
	 * @return
	 */
	public SupplyVproduct selectTotalSupplyNumByOrderId(SupplyVproduct supplyVproduct);
	/**
	 * 统计补货订单下站点的总补货量、总实际补货量、总销量统计信息
	 * @param supplyVproduct
	 * @return
	 */
	public SupplyVproduct selectTotalSupplyNumBySiteId(SupplyVproduct supplyVproduct);

	/**
	 * 统计补货订单下商品的总补货量、总实际补货量、总销量统计信息
	 * @param supplyVproduct
	 * @return
	 */
	public SupplyVproduct selectTotalSupplyNumByProductId(SupplyVproduct supplyVproduct);
	
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
     * 删除补货站点需要补货的货道商品，根据流水可检查商品的过期时间
     * 
     * @param logid 补货站点需要补货的货道商品，根据流水可检查商品的过期时间ID
     * @return 结果
     */
	public int deleteSupplyVproductById(String logid);
	
	/**
     * 批量删除补货站点需要补货的货道商品，根据流水可检查商品的过期时间
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyVproductByIds(String[] logids);

	public List<SupplyVproduct> findsupplyVproductList(SupplyVproduct supplyVproduct);

	/**
	 * 批量插入补货站点需要补货的货道商品
	 * 
	 * @param supplyVproductList
	 */
	public void insertSupplyVproductBatch(List<SupplyVproduct> supplyVproductList);
	
}