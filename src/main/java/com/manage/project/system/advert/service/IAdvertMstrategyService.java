package com.manage.project.system.advert.service;

import com.manage.project.system.advert.domain.AdvertMstrategy;
import java.util.List;

/**
 * 广告配置 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IAdvertMstrategyService 
{
	/**
     * 查询广告配置信息
     * 
     * @param logid 广告配置ID
     * @return 广告配置信息
     */
	public AdvertMstrategy selectAdvertMstrategyById(String logid);
	
	/**
     * 查询广告配置列表
     * 
     * @param advertMstrategy 广告配置信息
     * @return 广告配置集合
     */
	public List<AdvertMstrategy> selectAdvertMstrategyList(AdvertMstrategy advertMstrategy);
	
	/**
	 * 根据策略id得到素材
	 * @param strategyId
	 * @return
	 */
	public List<AdvertMstrategy> selectAdvertMstrategyByStrategyId(String strategyId);
	
	/**
     * 新增广告配置
     * 
     * @param advertMstrategy 广告配置信息
     * @return 结果
     */
	public int insertAdvertMstrategy(AdvertMstrategy advertMstrategy);
	
	/**
     * 修改广告配置
     * 
     * @param advertMstrategy 广告配置信息
     * @return 结果
     */
	public int updateAdvertMstrategy(AdvertMstrategy advertMstrategy);
		
	/**
     * 删除广告配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAdvertMstrategyByIds(String ids);
	
}
