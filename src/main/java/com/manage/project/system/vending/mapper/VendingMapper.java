package com.manage.project.system.vending.mapper;

import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingModel;
import com.manage.project.system.vending.vo.CabinetTypeSelectVo;
import com.manage.project.system.vending.vo.RelationSelectResultVo;
import com.manage.project.system.vending.vo.VendingListVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;	

/**
 * 售货机的基本，主柜 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Component
public interface VendingMapper 
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
	
	/**
     * 新增售货机的基本，主柜
     * 
     * @param vending 售货机的基本，主柜信息
     * @return 结果
     */
	public int insertVending(Vending vending);
	
	/**
     * 修改售货机的基本，主柜
     * 
     * @param vending 售货机的基本，主柜信息
     * @return 结果
     */
	public int updateVending(Vending vending);
	
	/**
	 * 根据siteId修改售卖机经纬度
	 * @param vending
	 * @return
	 */
	public int updateLongitudeLatitude(Vending vending);
	/**
     * 删除售货机的基本，主柜
     * 
     * @param logid 售货机的基本，主柜ID
     * @return 结果
     */
	public int deleteVendingById(String logid);
	
	/**
     * 批量删除售货机的基本，主柜
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingByIds(String[] ids);
	
	/**
	 * 售卖机管理主页查询
	 * @param vending
	 * @return
	 */
	public List<VendingListVo> selectNVendingList(VendingListVo vendingListVo);

	public Vending selectVendingBySiteId(String siteId);
	
	/**
	 * 根据售卖机id和名称查询
	 * @param name
	 * @return
	 */
	public List<Vending> selectVendingByCommon(@Param("name") String name, @Param("corpId") String corpId);

	/**
	 * 根据售卖机所有的货柜的类型及售卖机状态查询售卖机列表
	 * @param cabinetType
	 * @param curState
	 * @param corpId
	 * @return
	 */
	public List<RelationSelectResultVo> selectVendingByCabinetType(@Param("cabinetType") String cabinetType,@Param("curState") String curState, @Param("corpId") String corpId, @Param("name") String name);

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
	public List<CabinetTypeSelectVo> selectCabinetTypeByFactoryId(@Param("factoryId") String factoryId);
	
	/**
	 * 根据货柜类型找机型deviceId
	 * @param cabinetType
	 * @param corpId 
	 * @return
	 */
	public List<VendingModel> selectDeviceByCabinetType(@Param("cabinetType") String cabinetType, @Param("corpId") String corpId);

	/**
	 * 查询售货机数量
	 * 
	 * @param corpId 公司编号
	 * @return
	 */
	public Map<String, Integer> selectVendingNum(String corpId);

	/**
	 * 
	 * @param corpId
	 * @return
	 */
	public int selectVendingAllNum(@Param("corpId") String corpId);

	public int selectVendingOnlineNum(@Param("corpId") String corpId);

	public int selectVendingOutlineNum(@Param("corpId") String corpId);

	public int selectVendingWzNum(@Param("corpId") String corpId);

	public int selectVendingDelNum(@Param("corpId") String corpId);

	/**
	 * 根据点位查询未删除的售货机数量
	 * 
	 * @param pointIds
	 * @return
	 */
	public Integer selectVendingNumByPointIds(String[] pointIds);
	
	public Map<String, BigDecimal> selectVendingCap(String siteId);

	/**
	 * 批量修改站点状态为下架
	 * 
	 * @param sites
	 * @return
	 */
	public int siteProductUnder(String[] sites);

	/**
	 * 根据编号数组查询售货机
	 * 
	 * @param ids
	 * @return
	 */
	public List<Vending> selectVendingByIds(String[] split);

	/**
	 * 查询未删除售货机
	 * 
	 * @param vendingP
	 * @return
	 */
	public List<Vending> selectNeverDelVendingList(Vending vendingP);

	/**
	 * 根据货柜类型查询未删除的售货机
	 * 
	 * @param cabinetType
	 * @param corpId
	 * @param name
	 * @return
	 */
	public List<RelationSelectResultVo> selectNeverDelVendingByCabinetType(@Param("cabinetType") String cabinetType,@Param("corpId") String corpId, @Param("name") String name);

	/**
	 * 根据siteId查询未删除售货机
	 * 
	 * @param siteId
	 * @return
	 */
	public Vending selectNeverDelVendingBySiteId(String siteId);

	/**
	 * 查询售货机是否存在
	 * 
	 * @param vending
	 * @return
	 */
	public Vending selectVendingExist(Vending vending);
}