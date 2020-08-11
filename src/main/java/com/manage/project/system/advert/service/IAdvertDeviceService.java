package com.manage.project.system.advert.service;

import com.manage.project.system.advert.domain.AdvertDevice;
import com.manage.project.system.advert.vo.AdvertDeviceSelectVo;
import com.manage.project.system.advert.vo.EditAdvertDeviceVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 广告播放对象设置，也叫播放任务列 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IAdvertDeviceService 
{
	/**
	 * 查询广告任务列表
	 * @param deviceId
	 * @param name
	 * @param playerPlat
	 * @param corpId
	 * @return
	 */
	public List<AdvertDeviceSelectVo> selectAdvertDeviceRw(String deviceId, 
			String name, 
			String playerPlat, 
			String corpId);
	
	/**
	 * 根据广告id查询所有投放设备
	 * @param advertId
	 * @return
	 */
	public List<EditAdvertDeviceVo> selectAdvertDeviceByAdvertId(@Param("advertId") String advertId);
	
	/**
     * 查询广告播放对象设置，也叫播放任务列信息
     * 
     * @param logid 广告播放对象设置，也叫播放任务列ID
     * @return 广告播放对象设置，也叫播放任务列信息
     */
	public AdvertDevice selectAdvertDeviceById(String logid);
	
	/**
     * 查询广告播放对象设置，也叫播放任务列列表
     * 
     * @param 
     * @return 广告播放对象设置，也叫播放任务列集合
     */
	public List<AdvertDevice> selectAdvertDeviceList(String districtId, 
			String lineId, 
			String deviceId, 
			String advertId, 
			String corpId);
	
	/**
     * 新增广告播放对象设置，也叫播放任务列
     * 
     * @param advertDevice 广告播放对象设置，也叫播放任务列信息
     * @return 结果
     */
	public int insertAdvertDevice(AdvertDevice advertDevice);
	
	/**
     * 修改广告播放对象设置，也叫播放任务列
     * 
     * @param advertDevice 广告播放对象设置，也叫播放任务列信息
     * @return 结果
     */
	public int updateAdvertDevice(AdvertDevice advertDevice);
		
	/**
     * 删除广告播放对象设置，也叫播放任务列信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAdvertDeviceByIds(String ids);
	
	/**
	 * 全量下发覆盖之前的广告
	 * 
	 * @param deviceId
	 * @param advertId
	 * @return
	 */
	public int invalidAdvertDeviceCoverExist(String deviceId,String advertId);
}
