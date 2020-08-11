package com.manage.project.system.supply.mapper;

import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.vo.SupplyVendingVo;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Component
public interface SupplyOrderMapper 
{
	/**
     * 查询补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     * 
     * @param logid 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。ID
     * @return 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     */
	public SupplyOrder selectSupplyOrderById(String logid);
	
	/**
     * 查询补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。列表
     * 
     * @param supplyOrder 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     * @return 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。集合
     */
	public List<SupplyOrder> selectSupplyOrderList(SupplyOrder supplyOrder);
	
	/**
     * 新增补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。
     * 
     * @param supplyOrder 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     * @return 结果
     */
	public int insertSupplyOrder(SupplyOrder supplyOrder);
	
	/**
     * 修改补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。
     * 
     * @param supplyOrder 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     * @return 结果
     */
	public int updateSupplyOrder(SupplyOrder supplyOrder);
	
	/**
     * 删除补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。
     * 
     * @param logid 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。ID
     * @return 结果
     */
	public int deleteSupplyOrderById(String logid);
	
	/**
     * 批量删除补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyOrderByIds(String[] logids);


	public List<SupplyOrder> selectSupplyOrderByRefreshDate(SupplyOrder supplyOrder);

	public SupplyOrder selectNoFinshOrderBySupplyId(SupplyOrder supplyOrder);

	public SupplyOrder selectBySOrderId(String sOrderId);

	/**
	 * 查询售货机补货记录
	 * 
	 * @param vo
	 * @return
	 */
	public List<SupplyOrder> selectSiteSupplyOrder(SupplyVendingVo vo);

	/**
	 * 查询是否存在未完成的补货单
	 * 
	 * @param supplyId
	 * @return 补货单编号
	 */
	public String selectNotFinishSupplyOrder(String supplyId);
}