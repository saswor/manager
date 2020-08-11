package com.manage.project.system.advert.service;

import com.manage.project.system.advert.domain.AdvertMaterial;
import java.util.List;

/**
 * 广告素材媒体 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IAdvertMaterialService 
{
	/**
     * 查询广告素材媒体信息
     * 
     * @param logid 广告素材媒体ID
     * @return 广告素材媒体信息
     */
	public AdvertMaterial selectAdvertMaterialById(String logid);
	
	/**
     * 查询广告素材媒体列表
     * 
     * @param advertMaterial 广告素材媒体信息
     * @return 广告素材媒体集合
     */
	public List<AdvertMaterial> selectAdvertMaterialList(AdvertMaterial advertMaterial);
	
	/**
     * 新增广告素材媒体
     * 
     * @param advertMaterial 广告素材媒体信息
     * @return 结果
     */
	public int insertAdvertMaterial(AdvertMaterial advertMaterial);
	
	/**
     * 修改广告素材媒体
     * 
     * @param advertMaterial 广告素材媒体信息
     * @return 结果
     */
	public int updateAdvertMaterial(AdvertMaterial advertMaterial);
		
	/**
     * 删除广告素材媒体信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAdvertMaterialByIds(String ids);
	
}
