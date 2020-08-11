package com.manage.project.system.vending.service;

import com.manage.common.exception.vending.LaneProductNullException;
import com.manage.common.exception.vending.SupplyVorderExistException;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.domain.VendingModel;
import com.manage.project.system.vending.vo.CabinetTypeSelectVo;
import com.manage.project.system.vending.vo.RelationSelectResultVo;
import com.manage.project.system.vending.vo.VendingListVo;
import com.manage.project.system.vending.vo.VendingVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 售货机的基本，主柜 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IVendingService 
{
	/**
     * 查询售货机的基本，主柜信息
     * 
     * @param logid 售货机的基本，主柜ID
     * @return 售货机的基本，主柜信息
     */
	public Vending selectVendingById(String logid);
	
	/**
     * 查询售货机的基本，主柜列表
     * 
     * @param vending 售货机的基本，主柜信息
     * @return 售货机的基本，主柜集合
     */
	public List<Vending> selectVendingList(Vending vending);
	
//	/**
//     * 新增售货机的基本，主柜
//     * 
//     * @param vending 售货机的基本，主柜信息
//     * @return 结果
//     */
//	public int insertVending(Vending vending);
	
	/**
     * 新增售货机的基本，主柜
     * 
     * @param vending 售货机的基本，主柜信息
     * @return 结果
     */
	public Vending insertVending(VendingVo vendingVo) throws LaneProductNullException;
	
	/**
     * 修改售货机的基本，主柜
     * 
     * @param vending 售货机的基本，主柜信息
     * @return 结果
     */
	public int updateVending(Vending vending) throws SupplyVorderExistException;
		
	/**
     * 删除售货机的基本，主柜信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingByIds(String ids);
	
	/**
	 * 售卖机管理主页查询
	 * @param vending
	 * @return
	 */
	public List<VendingListVo> selectNVendingList(VendingListVo vendingListVo);
	
	/**
	 * 更新售卖机
	 * @param vendingVo
	 * @return
	 */
	public int updateVending(VendingVo vendingVo);

	/**
	 * 更新售卖机
	 * @param vendingVo
	 * @return
	 */
	public Vending selectVendingBySiteId(String siteId);
	
	/**
	 * 根据售卖机id和名称查询
	 * @param name
	 * @return
	 */
	public List<Vending> selectVendingByCommon(String name, String corpId);
	
	/**
	 * 根据售卖机所有的货柜的类型及售卖机状态查询售卖机列表
	 * @param cabinetType
	 * @param curState
	 * @param corpId
	 * @return
	 */
	public List<RelationSelectResultVo> selectVendingByCabinetType(String cabinetType,String curState, String corpId, String name);

	/**
	 * 根据点位查询售货机
	 * 
	 * @param pointId 点位编号
	 * @return 售货机信息
	 */
	public Vending selectVendingByPointId(String pointId);
	
	/**
	 * 根据厂商查找货柜类型
	 * @param factoryId
	 * @return
	 */
	public List<CabinetTypeSelectVo> selectCabinetTypeByFactoryId(String factoryId);
	
	/**
	 * 根据货柜类型找机型deviceId
	 * @param cabinetType
	 * @param corpId 
	 * @return
	 */
	public List<VendingModel> selectDeviceByCabinetType(String cabinetType, String corpId);

	/**
	 * 查询售货机数量
	 * 
	 * @param corpId 公司编号
	 * @return
	 */
	public Map<String, Integer> selectVendingNum(String corpId);
	
	/**
	 * 通知消息服务器删除售货机
	 * 
	 * @param siteId 售货机编号
	 */
	public String deleteVending(String siteId);

	/**
	 * 根据点位查询未删除的售货机数量
	 * 
	 * @param pointIds
	 * @return
	 */
	public Integer selectVendingNumByPointIds(String[] pointIds);
	
	public Map<String, BigDecimal> selectVendingCap(String siteId);

	/**
	 * 注册售货机
	 * 
	 * @param vending
	 * @return
	 */
	public String registerVending(Vending vending);
	
	/**
	 * 删除售卖机，修改补货配置
	 */
	public void updateSupply(String siteId, String lineId);

	/**
	 * 根据编号数组查询售货机
	 * 
	 * @param ids
	 * @return
	 */
	public List<Vending> selectVendingByIds(String ids);

	/**
	 * 批量删除售货机
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteVendingByIds(String[] ids);

	/**
	 * 根据货柜类型查询未删除的售货机
	 * 
	 * @param cabinetType
	 * @param corpId
	 * @param name
	 * @return
	 */
	public List<RelationSelectResultVo> selectNeverDelVendingByCabinetType(String cabinetType, String corpId,
			String name);

	/**
	 * 逻辑删除售货机
	 * 
	 * @param v
	 */
	public int deleteByFlag(Vending v);

	/**
	 * 售货机换货
	 * 
	 * @param vendingLanep
	 * @return
	 */
	public int updateVendingLanep(VendingLanep vendingLanep);

	/**
	 * 查询未删除售货机
	 * 
	 * @param vending
	 * @return
	 */
	public List<Vending> selectNeverDelVendingList(Vending vending);

	/**
	 * 批量导入售货机
	 * 
	 * @param vending
	 * @return
	 */
	public AjaxResult saveImportVending(Vending vending);

	/**
	 * 锁定货道
	 * 
	 * @param vendingLanep
	 * @return
	 */
	public AjaxResult lockLane(VendingLanep vendingLanep);
	
	/**
	 * 解锁货道
	 * 
	 * @param vendingLanep
	 * @return
	 */
	public AjaxResult unlockLane(VendingLanep vendingLanep);
}
