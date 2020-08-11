package com.manage.project.system.advert.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.advert.mapper.AdvertConfigMapper;
import com.manage.project.system.advert.mapper.AdvertDeviceMapper;
import com.manage.project.system.advert.domain.AdvertDevice;
import com.manage.project.system.advert.service.IAdvertDeviceService;
import com.manage.project.system.advert.vo.AdvertDeviceSelectVo;
import com.manage.project.system.advert.vo.EditAdvertDeviceVo;
import com.manage.common.support.Convert;
import com.manage.common.utils.StringUtils;

/**
 * 广告播放对象设置，也叫播放任务列 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class AdvertDeviceServiceImpl implements IAdvertDeviceService 
{
	@Autowired
	private AdvertDeviceMapper advertDeviceMapper;

	/**
     * 查询广告播放对象设置，也叫播放任务列信息
     * 
     * @param logid 广告播放对象设置，也叫播放任务列ID
     * @return 广告播放对象设置，也叫播放任务列信息
     */
    @Override
	public AdvertDevice selectAdvertDeviceById(String logid)
	{
	    return advertDeviceMapper.selectAdvertDeviceById(logid);
	}
	
	/**
     * 查询广告播放对象设置，也叫播放任务列列表
     * 
     * @param advertDevice 广告播放对象设置，也叫播放任务列信息
     * @return 广告播放对象设置，也叫播放任务列集合
     */
	@Override
	public List<AdvertDevice> selectAdvertDeviceList(String districtId, 
			String lineId, 
			String deviceId, 
			String advertId,
			String corpId)
	{
	    return advertDeviceMapper.selectAdvertDeviceList(districtId, lineId, deviceId,advertId, corpId);
	}
	
    /**
     * 新增广告播放对象设置，也叫播放任务列
     * 
     * @param advertDevice 广告播放对象设置，也叫播放任务列信息
     * @return 结果
     */
	@Override
	public int insertAdvertDevice(AdvertDevice advertDevice)
	{
	    return advertDeviceMapper.insertAdvertDevice(advertDevice);
	}
	
	/**
     * 修改广告播放对象设置，也叫播放任务列
     * 
     * @param advertDevice 广告播放对象设置，也叫播放任务列信息
     * @return 结果
     */
	@Override
	public int updateAdvertDevice(AdvertDevice advertDevice)
	{
	    return advertDeviceMapper.updateAdvertDevice(advertDevice);
	}

	/**
     * 删除广告播放对象设置，也叫播放任务列对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAdvertDeviceByIds(String ids)
	{
		return advertDeviceMapper.deleteAdvertDeviceByIds(Convert.toStrArray(ids));
	}

	@Override
	public List<AdvertDeviceSelectVo> selectAdvertDeviceRw(String deviceId, String name, String playerPlat,
			String corpId) {
		// TODO Auto-generated method stub
		return advertDeviceMapper.selectAdvertDeviceRw(deviceId, name, playerPlat, corpId);
	}

	@Override
	public List<EditAdvertDeviceVo> selectAdvertDeviceByAdvertId(String advertId) {
		return advertDeviceMapper.selectAdvertDeviceByAdvertId(advertId);
	}

	/**
	 * 全量下发覆盖之前的广告
	 * 
	 * @param deviceId
	 * @param advertId
	 * @return
	 */
	@Override
	@Transactional
	public int invalidAdvertDeviceCoverExist(String deviceId, String advertId) {
		advertDeviceMapper.invalidAdvertDeviceCoverExist(deviceId, advertId);
		//查询该广告配置下的广告投放设备信息,如果全部失效,将广告配置也失效
//		List<AdvertDevice> list = advertDeviceMapper.selectNotInvalidAdvertDeviceByDeviceId(advertId);
//		if(StringUtils.isEmpty(list)) {
//			
//		}
		return 1;
	}
	
}
