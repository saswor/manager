package com.manage.project.system.advert.service;

import com.manage.project.system.advert.domain.AdvertStrategy;
import java.util.List;

/**
 * 广告配置 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IAdvertStrategyService 
{
	/**
     * 查询广告配置信息
     * 
     * @param logid 广告配置ID
     * @return 广告配置信息
     */
	public AdvertStrategy selectAdvertStrategyById(String logid);
	
	/**
	 * 根据广告id查询所有策略
	 * @param advertId
	 * @return
	 */
	public List<AdvertStrategy> selectAdvertStrategyByAdvertId(String advertId);
	
	/**
     * 查询广告配置列表
     * 
     * @param advertStrategy 广告配置信息
     * @return 广告配置集合
     */
	public List<AdvertStrategy> selectAdvertStrategyList(AdvertStrategy advertStrategy);
	
	/**
     * 新增广告配置
     * 
     * @param advertStrategy 广告配置信息
     * @return 结果
     */
	public int insertAdvertStrategy(AdvertStrategy advertStrategy);
	
	/**
     * 修改广告配置
     * 
     * @param advertStrategy 广告配置信息
     * @return 结果
     */
	public int updateAdvertStrategy(AdvertStrategy advertStrategy);
		
	/**
     * 删除广告配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAdvertStrategyByIds(String ids);
	
}
