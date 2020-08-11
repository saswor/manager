package com.manage.project.system.advert.mapper;

import com.manage.project.system.advert.domain.FavourableObject;
import com.manage.project.system.advert.vo.ProductFavViewVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface FavourableObjectMapper 
{
	/**
     * 查询优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠信息
     * 
     * @param logid 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠ID
     * @return 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠信息
     */
	public FavourableObject selectFavourableObjectById(String logid);
	
	/**
     * 查询优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠列表
     * 
     * @param favourableObject 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠信息
     * @return 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠集合
     */
	public List<FavourableObject> selectFavourableObjectList(FavourableObject favourableObject);
	
	/**
	 * 查询区域或线路内使用该优惠的售卖机id
	 * @param districtId	// 区域id
	 * @param favObjectId 折扣id
	 * @param lineId	线路id
	 * @param favObjId	售卖机id
	 * @param corpId	
	 * @return
	 */
	public List<FavourableObject> selectFavourableSiteList(@Param("favourableId") String favourableId,
			@Param("districtId") String districtId, 
			@Param("lineId") String lineId, 
			@Param("favObjId") String favObjId, 
			@Param("corpId") String corpId);
	
	/**
     * 新增优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠
     * 
     * @param favourableObject 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠信息
     * @return 结果
     */
	public int insertFavourableObject(FavourableObject favourableObject);
	
	/**
     * 修改优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠
     * 
     * @param favourableObject 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠信息
     * @return 结果
     */
	public int updateFavourableObject(FavourableObject favourableObject);
	
	/**
     * 删除优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠
     * 
     * @param logid 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠ID
     * @return 结果
     */
	public int deleteFavourableObjectById(String logid);
	
	/**
	 * 根据优惠id删除优惠设备
	 * @param favourableId
	 * @return
	 */
	public int deleteFavourableObjectByFavourableId(String favourableId);
	
	/**
     * 批量删除优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFavourableObjectByIds(String[] logids);
	
	/**
	 * 查询某一折扣所拥有的商品
	 * @param favourableId	折扣id
	 * @param name	商品名称或商品id
	 * @return
	 */
	public List<ProductFavViewVo> selectFavProductList(@Param("favourableId") String favourableId,
			@Param("name") String name);
}