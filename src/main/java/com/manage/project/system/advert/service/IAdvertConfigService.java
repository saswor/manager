package com.manage.project.system.advert.service;

import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.advert.domain.AdvertConfig;
import com.manage.project.system.advert.vo.AdvertSaveVo;

import java.util.List;

/**
 * 广告配置 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IAdvertConfigService 
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
	public int insertAdvertConfig(AdvertSaveVo advertSaveVo) throws Exception;
	
	/**
     * 修改广告配置
     * 
     * @param advertConfig 广告配置信息
     * @return 结果
     */
	public int updateAdvertConfig(AdvertSaveVo advertSaveVo);
		
	/**
     * 删除广告配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAdvertConfigByIds(String ids);

	/**
	 * 广告失效
	 * 
	 * @param ids
	 * @return
	 */
	public int invalid(String ids);

	/**
	 * 查询到时间未执行的广告
	 * 
	 * @param advertConfig
	 * @return
	 */
	public List<AdvertConfig> selectNotExcutingAdvertConfigList(AdvertConfig advertConfig);
	
	/**
	 * 通知终端
	 * 
	 * @param editAdvertDeviceVo
	 * @return
	 */
	public int insertVendingCmd(String siteId,String advertId,String AdvDeviceId,String corpId);

	/**
	 * 查询未失效的广告
	 * 
	 * @param advertConfigSelect
	 * @return
	 */
	public List<AdvertConfig> selectNotInvalidAdvertConfigList(AdvertConfig advertConfigSelect);

	/**
	 * 全量下发覆盖之前的广告
	 * 
	 * @param deviceId
	 * @param advertId
	 */
	public void invalidAdvertDeviceCoverExist(String deviceId, String advertId);
	
}
