package com.manage.project.system.supply.service;

import com.manage.project.system.supply.domain.SupplyConfig;
import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.handler.SupplyOrderHandler;
import com.manage.project.system.supply.vo.SupplyOutListVo;
import com.manage.project.system.supply.vo.SupplyVProductAddVo;
import com.manage.project.system.supply.vo.SupplyVendingVo;
import com.manage.project.system.supply.vo.VendingSupplyProductVo;

import java.util.List;

/**
 * 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface ISupplyOrderService 
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
     * 删除补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyOrderByIds(String ids);
	/**
	 *
	 *
	 * @param supplyOrder
	 * @return 结果
	 */
	public int insertSupplyOrderBySite(SupplyVProductAddVo supplyVProductAddVo);

	/**
	 * 查询售货机补货记录
	 * 
	 * @param vo
	 * @return
	 */
	public List<SupplyOrder> selectSiteSupplyOrder(SupplyVendingVo vo);
	
	/**
	 * 根据补货配置生成补货单
	 * 
	 * @param supplyConfig
	 * @return
	 */
	public int createSupplyOrderBySupplyConfig(SupplyConfig supplyConfig,String site);
	
	/**
	 * 获取对应线路生成补货单方法的执行对象
	 * 
	 * @param lineId 线路编号
	 * @return
	 */
	public SupplyOrderHandler getLineThreadByLineId(String lineId);

	/**
	 * 线路告警生成补货单
	 * 
	 * @param lineId
	 * @return
	 */
	public int insertSupplyOrderByLine(String lineId);

	/**
	 * 查询是否存在未完成的补货单
	 * 
	 * @param supplyId
	 * @return 补货单编号
	 */
	public String selectNotFinishSupplyOrder(String supplyId);
	
}
