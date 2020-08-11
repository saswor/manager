package com.manage.project.system.advert.mapper;

import com.manage.project.system.advert.domain.FavourableConfig;
import com.manage.project.system.advert.vo.FavourableConfigTjVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface FavourableConfigMapper 
{
	/**
	 * 根据corp查询折扣统计信息
	 * @param corpId
	 * @return
	 */
	public FavourableConfigTjVo selectFavourableConfigTj(@Param("corpId") String corpId);
	
	/**
     * 查询商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * 
     * @param logid 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。ID
     * @return 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     */
	public FavourableConfig selectFavourableConfigById(String logid);
	
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
	public int insertFavourableConfig(FavourableConfig favourableConfig);
	
	/**
     * 修改商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。
     * 
     * @param favourableConfig 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。信息
     * @return 结果
     */
	public int updateFavourableConfig(FavourableConfig favourableConfig);
	
	/**
     * 删除商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。
     * 
     * @param logid 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。ID
     * @return 结果
     */
	public int deleteFavourableConfigById(String logid);
	
	/**
     * 批量删除商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFavourableConfigByIds(String[] logids);
	
}