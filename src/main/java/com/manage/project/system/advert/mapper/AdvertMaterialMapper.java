package com.manage.project.system.advert.mapper;

import com.manage.project.system.advert.domain.AdvertMaterial;
import java.util.List;	

/**
 * 广告素材媒体 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface AdvertMaterialMapper 
{
	/**
     * 查询广告素材媒体信息
     * 
     * @param materialId 广告素材媒体ID
     * @return 广告素材媒体信息
     */
	public AdvertMaterial selectAdvertMaterialById(String materialId);
	
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
     * 删除广告素材媒体
     * 
     * @param logid 广告素材媒体ID
     * @return 结果
     */
	public int deleteAdvertMaterialById(String logid);
	
	/**
     * 批量删除广告素材媒体
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAdvertMaterialByIds(String[] materialIds);

	/**
	 * 查询素材名是否存在
	 * 
	 * @param advertMaterial
	 * @return
	 */
	public AdvertMaterial selectMediaNameIsExit(AdvertMaterial advertMaterial);

	/**
     * 批量查询广告素材媒体信息
     * 
     * @param ids
     * @return 结果
     */
	public List<AdvertMaterial> selectAdvertMaterialByIds(String[] ids);
	
}