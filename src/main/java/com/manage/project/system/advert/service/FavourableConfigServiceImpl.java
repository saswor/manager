package com.manage.project.system.advert.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.advert.mapper.FavourableConfigMapper;
import com.manage.project.system.advert.mapper.FavourableObjectMapper;
import com.manage.project.system.advert.mapper.FavourableTimeMapper;
import com.manage.project.system.advert.domain.FavourableConfig;
import com.manage.project.system.advert.domain.FavourableObject;
import com.manage.project.system.advert.domain.FavourableTime;
import com.manage.project.system.advert.service.IFavourableConfigService;
import com.manage.project.system.advert.vo.FavourableConfigTjVo;
import com.manage.project.system.advert.vo.FavourableSaveVo;
import com.manage.project.system.advert.vo.Fobject;
import com.manage.project.system.advert.vo.Ftime;
import com.manage.project.system.util.SystemUtil;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.bean.BeanUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class FavourableConfigServiceImpl implements IFavourableConfigService 
{
	@Autowired
	private FavourableConfigMapper favourableConfigMapper;
	
	@Autowired
	private FavourableTimeMapper favourableTimeMapper;
	
	@Autowired
	private FavourableObjectMapper favourableObjectMapper;

	/**
	 * 根据corp查询折扣统计信息
	 * @param corpId
	 * @return
	 */
	@Override
	public FavourableConfigTjVo selectFavourableConfigTj(String corpId) {
		return favourableConfigMapper.selectFavourableConfigTj(corpId);
	}
	
	/**
     * 查询商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * 
     * @param logid 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。ID
     * @return 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     */
    @Override
	public FavourableConfig selectFavourableConfigById(String favourableId)
	{
	    return favourableConfigMapper.selectFavourableConfigById(favourableId);
	}
	
	/**
     * 查询商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。列表
     * 
     * @param favourableConfig 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * @return 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。集合
     */
	@Override
	public List<FavourableConfig> selectFavourableConfigList(FavourableConfig favourableConfig)
	{
	    return favourableConfigMapper.selectFavourableConfigList(favourableConfig);
	}
	
    /**
     * 新增商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。
     * 
     * @param favourableConfig 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * @return 结果
     * @throws Exception 
     */
	@Override
	@Transactional
	public int insertFavourableConfig(FavourableSaveVo favourableSaveVo) throws Exception
	{
		// 新增优惠
		FavourableConfig favourableConfig = new FavourableConfig();
		BeanUtils.copyBeanProp(favourableConfig, favourableSaveVo);
		favourableConfig.setLogid(UUID.randomUUID().toString());
		String corpId = ShiroUtils.getCorpId();
		favourableConfig.setCorpId(corpId);
		String favourableId = SystemUtil.getSeqId(corpId, "as_favourable_config");
		favourableConfig.setFavourableId(favourableId);
		List<Fobject> fobjectList = favourableSaveVo.getFobjects();
		if(StringUtils.isEmpty(fobjectList)) {
			throw new RuntimeException("请至少选择一个售货机或商品");
		}
		if (fobjectList != null) {
			favourableConfig.setFavNum(fobjectList.size());
		}
		favourableConfig.setCreateTime(DateUtils.getTime());
		favourableConfig.setFavMoney(0f);
		favourableConfig.setFavTimes(0);
		int r = favourableConfigMapper.insertFavourableConfig(favourableConfig);
		// 新增优惠时段
		List<Ftime> ftimes = favourableSaveVo.getFtimes();
		if (ftimes != null) {
			for (Ftime ftime : ftimes) {
				FavourableTime favTime = new FavourableTime();
				BeanUtils.copyBeanProp(favTime, ftime);
				favTime.setLogid(UUID.randomUUID().toString());
				String favTimeId = SystemUtil.getSeqId(corpId, "as_favourable_time");
				favTime.setFavTimeId(favTimeId);
				favTime.setFavourableId(favourableId);
				favTime.setValidSTime(favourableSaveVo.getValidSTime());
				favTime.setValidETime(favourableSaveVo.getValidETime());
				favTime.setCurState(favourableSaveVo.getCurState());
				favTime.setCreateTime(DateUtils.getTime());
				favTime.setCorpId(corpId);
				favourableTimeMapper.insertFavourableTime(favTime);
			}			
		}
		// 新增as_favourable_object
		if (fobjectList != null) {
			for (Fobject fobject : fobjectList) {
				FavourableObject favourableObject = new FavourableObject();
				BeanUtils.copyBeanProp(favourableObject, fobject);
				favourableObject.setLogid(UUID.randomUUID().toString());
				String favObjectId = SystemUtil.getSeqId(corpId, "as_favourable_object");
				favourableObject.setFavObjectId(favObjectId);
				favourableObject.setCorpId(corpId);
				favourableObject.setCreateTime(DateUtils.getTime());
				favourableObject.setFavourableId(favourableId);
				favourableObjectMapper.insertFavourableObject(favourableObject);
			}
		}
		
		return r;
	}
	
	/**
     * 修改商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。
     * 
     * @param favourableConfig 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * @return 结果
     */
	@Override
	@Transactional
	public int updateFavourableConfig(FavourableSaveVo favourableSaveVo)
	{
		FavourableConfig favourableConfig = new FavourableConfig();
		BeanUtils.copyBeanProp(favourableConfig, favourableSaveVo);
		List<Fobject> fobjectList = favourableSaveVo.getFobjects();
		if (fobjectList != null) {
			favourableConfig.setFavNum(fobjectList.size());
		}
		favourableConfig.setFavMoney(0f);
		favourableConfig.setFavTimes(0);
	    int r = favourableConfigMapper.updateFavourableConfig(favourableConfig);
	    // 删除所有优惠时段和优惠设备
	    String favourableId = favourableSaveVo.getFavourableId();
	    favourableTimeMapper.deleteFavourableTimeByFavourableId(favourableId);
	    favourableObjectMapper.deleteFavourableObjectByFavourableId(favourableId);
	    // 新增优惠时段
	    String corpId = ShiroUtils.getCorpId();
 		List<Ftime> ftimes = favourableSaveVo.getFtimes();
 		if (ftimes != null) {
 			for (Ftime ftime : ftimes) {
 				FavourableTime favTime = new FavourableTime();
 				BeanUtils.copyBeanProp(favTime, ftime);
 				favTime.setLogid(UUID.randomUUID().toString());
 				String favTimeId = SystemUtil.getSeqId(corpId, "as_favourable_time");
 				favTime.setFavTimeId(favTimeId);
 				favTime.setFavourableId(favourableId);
 				favTime.setValidSTime(favourableSaveVo.getValidSTime());
 				favTime.setValidETime(favourableSaveVo.getValidETime());
 				favTime.setCurState(favourableSaveVo.getCurState());
 				favTime.setCreateTime(DateUtils.getTime());
 				favTime.setCorpId(corpId);
 				favourableTimeMapper.insertFavourableTime(favTime);
 			}			
 		}
 		// 新增as_favourable_object
 		if (fobjectList != null) {
 			for (Fobject fobject : fobjectList) {
 				FavourableObject favourableObject = new FavourableObject();
 				BeanUtils.copyBeanProp(favourableObject, fobject);
 				favourableObject.setLogid(UUID.randomUUID().toString());
 				String favObjectId = SystemUtil.getSeqId(corpId, "as_favourable_object");
 				favourableObject.setFavObjectId(favObjectId);
 				favourableObject.setCorpId(corpId);
 				favourableObject.setCreateTime(DateUtils.getTime());
 				favourableObject.setFavourableId(favourableId);
 				favourableObjectMapper.insertFavourableObject(favourableObject);
 			}
 		}
	    return r;
	}

	/**
     * 删除商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。对象
     * 逻辑删除
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	@Transactional
	public int deleteFavourableConfigByIds(String ids)
	{
		int r = 0;
		String[] aids = Convert.toStrArray(ids);
		for (String id : aids) {
			FavourableConfig favourableConfig = new FavourableConfig();
			favourableConfig.setFavourableId(id);
			favourableConfig.setCurState("4");
			List<FavourableConfig> list = favourableConfigMapper.selectFavourableConfigList(favourableConfig);
			if(list!=null&&!list.isEmpty()) {
				return 2;
			}
		}
		for (String id : aids) {
			FavourableConfig favourableConfig = new FavourableConfig();
			favourableConfig.setFavourableId(id);
			favourableConfig.setCurState("4");
			r = r + favourableConfigMapper.updateFavourableConfig(favourableConfig);
		}
		
		return 1;
	}
	
}
