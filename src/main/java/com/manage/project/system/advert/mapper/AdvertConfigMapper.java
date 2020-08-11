package com.manage.project.system.advert.mapper;

import com.manage.project.common.Constant;
import com.manage.project.system.advert.domain.AdvertConfig;
import com.manage.project.system.vending.domain.VendingCmd;

import java.util.List;	

/**
 * 广告配置 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface AdvertConfigMapper 
{
	/**
     * 查询广告配置信息
     * 
     * @param logid 广告配置ID
     * @return 广告配置信息
     */
	public AdvertConfig selectAdvertConfigById(String logid);
	
	/**
	 * 根据广告id得到广告信息
	 * @param advertId
	 * @return
	 */
	public AdvertConfig selectAdvertConfigByAdvertId(String advertId);
	
	/**
     * 查询广告配置列表
     * 
     * @param advertConfig 广告配置信息
     * @return 广告配置集合
     */
	public List<AdvertConfig> selectAdvertConfigList(AdvertConfig advertConfig);
	
	/**
     * 新增广告配置
     * 
     * @param advertConfig 广告配置信息
     * @return 结果
     */
	public int insertAdvertConfig(AdvertConfig advertConfig);
	
	/**
     * 修改广告配置
     * 
     * @param advertConfig 广告配置信息
     * @return 结果
     */
	public int updateAdvertConfig(AdvertConfig advertConfig);
	
	/**
     * 删除广告配置
     * 
     * @param logid 广告配置ID
     * @return 结果
     */
	public int deleteAdvertConfigById(String logid);
	
	/**
     * 批量删除广告配置
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAdvertConfigByIds(String[] logids);

	/**
	 * 查询广告名是否存在
	 * 
	 * @param advertConfig
	 * @return
	 */
	public AdvertConfig selectAdvertConfigNameNotExist(AdvertConfig advertConfig);

	/**
	 * 查询到时间未执行的广告
	 * 
	 * @param advertConfig
	 * @return
	 */
	public List<AdvertConfig> selectNotExcutingAdvertConfigList(AdvertConfig advertConfig);

	/**
	 * 查询未失效的广告
	 * 
	 * @param advertConfig
	 * @return
	 */
	public List<AdvertConfig> selectNotInvalidAdvertConfigList(AdvertConfig advertConfig);

	/**
	 * 根据编号查询未删除的广告
	 * 
	 * @param advertIds
	 * @return
	 */
	public List<AdvertConfig> selectNotDeleteAdvertByAdvertIds(String[] advertIds);

	
}