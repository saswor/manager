package com.manage.project.system.advert.service;

import com.manage.project.system.advert.domain.FavourableConfig;
import com.manage.project.system.advert.vo.FavourableConfigTjVo;
import com.manage.project.system.advert.vo.FavourableSaveVo;

import java.util.List;

/**
 * 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IFavourableConfigService 
{
	/**
	 * 根据corp查询折扣统计信息
	 * @param corpId
	 * @return
	 */
	public FavourableConfigTjVo selectFavourableConfigTj(String corpId);
	
	/**
     * 查询商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * 
     * @param logid 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。ID
     * @return 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     */
	public FavourableConfig selectFavourableConfigById(String favourableId);
	
	/**
     * 查询商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。列表
     * 
     * @param favourableConfig 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * @return 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。集合
     */
	public List<FavourableConfig> selectFavourableConfigList(FavourableConfig favourableConfig);
	
	/**
     * 新增商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。
     * 
     * @param favourableConfig 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * @return 结果
     */
	public int insertFavourableConfig(FavourableSaveVo favourableSaveVo) throws Exception;
	
	/**
     * 修改商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。
     * 
     * @param favourableConfig 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * @return 结果
     */
	public int updateFavourableConfig(FavourableSaveVo favourableSaveVo);
		
	/**
     * 删除商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFavourableConfigByIds(String ids);
	
}
