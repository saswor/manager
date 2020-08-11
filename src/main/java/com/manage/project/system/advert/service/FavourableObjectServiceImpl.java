package com.manage.project.system.advert.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.advert.mapper.FavourableObjectMapper;
import com.manage.project.system.advert.domain.FavourableObject;
import com.manage.project.system.advert.service.IFavourableObjectService;
import com.manage.project.system.advert.vo.ProductFavViewVo;
import com.manage.common.support.Convert;

/**
 * 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class FavourableObjectServiceImpl implements IFavourableObjectService 
{
	@Autowired
	private FavourableObjectMapper favourableObjectMapper;

	/**
     * 查询优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠信息
     * 
     * @param logid 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠ID
     * @return 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠信息
     */
    @Override
	public FavourableObject selectFavourableObjectById(String logid)
	{
	    return favourableObjectMapper.selectFavourableObjectById(logid);
	}
	
	/**
     * 查询优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠列表
     * 
     * @param favourableObject 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠信息
     * @return 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠集合
     */
	@Override
	public List<FavourableObject> selectFavourableObjectList(FavourableObject favourableObject)
	{
	    return favourableObjectMapper.selectFavourableObjectList(favourableObject);
	}
	
    /**
     * 新增优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠
     * 
     * @param favourableObject 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠信息
     * @return 结果
     */
	@Override
	public int insertFavourableObject(FavourableObject favourableObject)
	{
	    return favourableObjectMapper.insertFavourableObject(favourableObject);
	}
	
	/**
     * 修改优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠
     * 
     * @param favourableObject 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠信息
     * @return 结果
     */
	@Override
	public int updateFavourableObject(FavourableObject favourableObject)
	{
	    return favourableObjectMapper.updateFavourableObject(favourableObject);
	}

	/**
     * 删除优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteFavourableObjectByIds(String ids)
	{
		return favourableObjectMapper.deleteFavourableObjectByIds(Convert.toStrArray(ids));
	}
	
	/**
	 * 查询区域或线路内使用该优惠的售卖机id
	 * @param districtId	// 区域id
	 * @param favObjectId 折扣id
	 * @param lineId	线路id
	 * @param favObjId	售卖机id
	 * @param corpId	
	 * @return
	 */
	@Override
	public List<FavourableObject> selectFavourableSiteList(String favourableId, String districtId, String lineId, 
			String favObjId, String corpId) {
		return favourableObjectMapper.selectFavourableSiteList(favourableId, districtId, lineId, favObjId, corpId);
	}
	
	/**
	 * 查询某一折扣所拥有的商品
	 * @param favourableId	折扣id
	 * @param name	商品名称或商品id
	 * @return
	 */
	public List<ProductFavViewVo> selectFavProductList(String favourableId, String name) {
		return favourableObjectMapper.selectFavProductList(favourableId, name);
	}
}
