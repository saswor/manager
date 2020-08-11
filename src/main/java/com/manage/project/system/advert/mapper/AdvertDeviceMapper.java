package com.manage.project.system.advert.mapper;

import com.manage.project.system.advert.domain.AdvertDevice;
import com.manage.project.system.advert.vo.AdvertDeviceSelectVo;
import com.manage.project.system.advert.vo.EditAdvertDeviceVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 广告播放对象设置，也叫播放任务列 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface AdvertDeviceMapper 
{
	/**
     * 查询广告播放对象设置，也叫播放任务列信息
     * 
     * @param advDeviceId 广告播放对象设置，也叫播放任务列ID
     * @return 广告播放对象设置，也叫播放任务列信息
     */
	public AdvertDevice selectAdvertDeviceById(String advDeviceId);
	
	/**
	 * 根据广告id查询所有投放设备
	 * @param advertId
	 * @return
	 */
	public List<EditAdvertDeviceVo> selectAdvertDeviceByAdvertId(@Param("advertId") String advertId);
	
	/**
	 * 查询广告任务列表
	 * @param deviceId
	 * @param name
	 * @param playerPlat
	 * @param corpId
	 * @return
	 */
	public List<AdvertDeviceSelectVo> selectAdvertDeviceRw(@Param("deviceId") String deviceId, 
			@Param("name") String name, 
			@Param("playerPlat") String playerPlat, 
			@Param("corpId") String corpId);
	
	/**
     * 查询广告播放对象设置，也叫播放任务列列表
     * 
     * @param advertDevice 广告播放对象设置，也叫播放任务列信息
     * @return 广告播放对象设置，也叫播放任务列集合
     */
	public List<AdvertDevice> selectAdvertDeviceList(@Param("districtId") String districtId, 
			@Param("lineId") String lineId, 
			@Param("deviceId") String deviceId, 
			@Param("advertId") String advertId, 
			@Param("corpId") String corpId);
	
	
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
     * 删除广告播放对象设置，也叫播放任务列
     * 
     * @param logid 广告播放对象设置，也叫播放任务列ID
     * @return 结果
     */
	public int deleteAdvertDeviceById(String logid);
	
	/**
	 * 通过广告编号删除投放设备
	 * @param advertId
	 * @return
	 */
	public int deleteAdvertDeviceByAdvertId(String advertId);
	
	/**
     * 批量删除广告播放对象设置，也叫播放任务列
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAdvertDeviceByIds(String[] logids);

	/**
	 * 标记删除广告播放设备
	 * 
	 * @param id
	 * @return
	 */
	public int deleteAdvertDeviceByAdvertIdWithFlag(String advertId);

	/**
	 * 批量失效广告播放设备
	 * 
	 * @param id
	 * @return
	 */
	public int invalidAdvertDeviceByAdvertIdWithFlag(String advertId);

	/**
	 * 全量下发覆盖之前的广告
	 * 
	 * @param deviceId
	 * @param advertId
	 * @return
	 */
	public int invalidAdvertDeviceCoverExist(@Param("deviceId") String deviceId,@Param("advertId") String advertId);

	/**
	 * 根据广告编号查询未失效的广告
	 * 
	 * @param advertId
	 * @return
	 */
	public List<AdvertDevice> selectNotInvalidAdvertDeviceByAdvertId(String advertId);

	/**
	 * 查询广告投放设备
	 * 
	 * @param advertDevice
	 * @return
	 */
	public List<AdvertDevice> selectAdvertDevices(AdvertDevice advertDevice);

	/**
	 * 查询未失效的广告
	 * 
	 * @param advertDevice
	 * @return
	 */
	public List<AdvertDevice> selectAdvertDevicesNotiInvalid(AdvertDevice advertDevice);
	
}