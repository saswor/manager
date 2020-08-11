package com.manage.project.system.vendingPoint.service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.vendingPoint.mapper.VendingPointMapper;
import com.manage.project.system.base.domain.Dispatch;
import com.manage.project.system.base.mapper.DispatchMapper;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.mapper.VendingMapper;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import com.manage.project.system.vendingPoint.mapper.VendingLineMapper;
import com.manage.project.system.vendingPoint.domain.VendingPoint;
import com.manage.project.system.vendingPoint.service.IVendingPointService;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 售货机绑定的点位 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingPointServiceImpl implements IVendingPointService 
{
	@Autowired
	private VendingPointMapper vendingPointMapper;
	
	@Autowired
	private VendingLineMapper vendingLineMapper;

	@Autowired
	private BussinessCacheService bussinessCacheService;
	
	@Autowired
	private VendingMapper vendingMapper;
	
	@Autowired
	private DispatchMapper dispatchMapper;

	/**
     * 查询售货机绑定的点位信息
     * 
     * @param logid 售货机绑定的点位ID
     * @return 售货机绑定的点位信息
     */
    @Override
	public VendingPoint selectVendingPointById(String logid)
	{
	    return vendingPointMapper.selectVendingPointById(logid);
	}
	
	/**
     * 查询售货机绑定的点位列表
     * 
     * @param vendingPoint 售货机绑定的点位信息
     * @return 售货机绑定的点位集合
     */
	@Override
	public List<VendingPoint> selectVendingPointList(VendingPoint vendingPoint)
	{
	    return vendingPointMapper.selectVendingPointList(vendingPoint);
	}
	
    /**
     * 新增售货机绑定的点位
     * 
     * @param vendingPoint 售货机绑定的点位信息
     * @return 结果
     */
	@Override
	public int insertVendingPoint(VendingPoint vendingPoint)
	{
		vendingPoint.setLogid(UUID.randomUUID().toString());
		vendingPoint.setCorpId(ShiroUtils.getUser().getCorpId());
		vendingPoint.setLocationType("1");
		if (vendingPoint.getLineId() != null && !vendingPoint.getLineId().equals("")) {
			VendingLine vendingLine = vendingLineMapper.selectVendingLineById(vendingPoint.getLineId());
			vendingPoint.setLineId(vendingLine.getLineId());
			vendingPoint.setDistrictId(vendingLine.getDistrictId());
		}
		vendingPoint.setPointId(SystemUtil.getSeqId(vendingPoint.getCorpId(), "as_vending_point"));
		int r = vendingPointMapper.insertVendingPoint(vendingPoint);
		bussinessCacheService.initVendingPoint();
	    return r;
	    
	}
	
	/**
     * 修改售货机绑定的点位
     * 
     * @param vendingPoint 售货机绑定的点位信息
     * @return 结果
     */
	@Override
	@Transactional
	public int updateVendingPoint(VendingPoint vendingPoint)
	{
		if (vendingPoint.getLineId() != null && !vendingPoint.getLineId().equals("")) {
			VendingLine vendingLine = vendingLineMapper.selectVendingLineByLineId(vendingPoint.getLineId());
			vendingPoint.setLineId(vendingLine.getLineId());
			vendingPoint.setDistrictId(vendingLine.getDistrictId());
		}
	    int r = vendingPointMapper.updateVendingPoint(vendingPoint);
	    // 修改售卖机经纬度
 		Vending vending = new Vending();
 		vending.setPointId(vendingPoint.getPointId());
 		List<Vending> vendingList = vendingMapper.selectVendingList(vending);
 		if (vendingList != null && !vendingList.isEmpty()) {
 			for (Vending v : vendingList) {
 				v.setLongitude(vendingPoint.getLongitude());
 				v.setLatitude(vendingPoint.getLatitude());
 				vendingMapper.updateLongitudeLatitude(v);
 				bussinessCacheService.cacheVending(v.getSiteId());// 更新缓存
 			}
 		}
	    bussinessCacheService.initVendingPoint();
	    return r;
	}

	/**
     * 删除售货机绑定的点位对象
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingPointByIds(String logids)
	{
		int r = vendingPointMapper.deleteVendingPointByIds(Convert.toStrArray(logids));
		bussinessCacheService.initVendingPoint();
	    return r;
	}
	
	/**
     * 查询售货机绑定的点位列表
     * 
     * @param vendingPoint 售货机绑定的点位信息
     * @return 售货机绑定的点位集合
     */
	@Override
	public List<VendingPoint> selectVendingPoint(VendingPoint vendingPoint) {
		return vendingPointMapper.selectVendingPoint(vendingPoint);
	}

	/**
	 * 保存点位信息
	 * 
	 * @param vendingPoint
	 * @return
	 */
	@Override
	public AjaxResult saveImportVendingPoint(VendingPoint vendingPoint) {
		AjaxResult result = checkVendingPoint(vendingPoint);
		if(!result.isSuccess()) {
			return result;
		}
		vendingPoint.setLogid(UUID.randomUUID().toString());
		String corpId = ShiroUtils.getCorpId();
		VendingLine vendingLine = SystemUtil.getVendingLineFromCache(vendingPoint.getLineId());
		if(vendingLine!=null) {
			vendingPoint.setDistrictId(vendingLine.getDistrictId());
		}
		vendingPoint.setPointId(SystemUtil.getSeqId(corpId, "as_vending_point"));
		vendingPoint.setLocationType("1");
		vendingPoint.setCorpId(corpId);
		vendingPoint.setCreateTime(DateUtils.getTime());
		vendingPointMapper.insertVendingPoint(vendingPoint);
		return AjaxResult.success();
	}
	
	private AjaxResult checkVendingPoint(VendingPoint vendingPoint) {
		String name = vendingPoint.getName();
		String code = vendingPoint.getCode();
		String lineName = vendingPoint.getLineName();
		String country = vendingPoint.getCountry();
		String province = vendingPoint.getProvince();
		String city = vendingPoint.getCity();
		String district = vendingPoint.getDistrict();
		String adderss = vendingPoint.getAdderss();
		String longitude = vendingPoint.getLongitude();
		String latitude = vendingPoint.getLatitude();
		String description = vendingPoint.getDescription();
		if(StringUtils.isEmpty(name)) {
			return AjaxResult.error("点位名称不能为空");
		}
		if(name.length()>50) {
			return AjaxResult.error("点位名称不能超过50个字符");
		}
		VendingPoint vendingPointName = new VendingPoint();
		vendingPointName.setName(name);
		vendingPointName.setCorpId(ShiroUtils.getCorpId());
		VendingPoint nameExist = vendingPointMapper.selectVendingPointExist(vendingPointName);
		if(nameExist!=null) {
			return AjaxResult.error("点位名称重复");
		}
		if(StringUtils.isEmpty(code)) {
			return AjaxResult.error("点位编码不能为空");
		}
		if(code.length()>30) {
			return AjaxResult.error("点位编码不能超过30个字符");
		}
		VendingPoint vendingPointCode = new VendingPoint();
		vendingPointCode.setCode(code);
		vendingPointCode.setCorpId(ShiroUtils.getCorpId());
		VendingPoint codeExist = vendingPointMapper.selectVendingPointExist(vendingPointCode);
		if(codeExist!=null) {
			return AjaxResult.error("点位编码重复");
		}
		if(StringUtils.isEmpty(lineName)) {
			return AjaxResult.error("线路不能为空");
		}
		String[] split = lineName.split("--");
		String lineId = split[split.length-1];
		VendingLine vendingLine = vendingLineMapper.selectVendingLineByLineId(lineId);
		if(vendingLine==null) {
			return AjaxResult.error("线路不存在");
		}
		vendingPoint.setLineId(lineId);
		if(StringUtils.isNotEmpty(country)) {
			String countryId = getCode(country);
			if(dispatchMapper.selectDispatchById(countryId)==null) {
				return AjaxResult.error("国家不存在");
			}
			vendingPoint.setCountry(countryId);
		}
		if(StringUtils.isNotEmpty(province)) {
			String provinceId = getCode(province);
			if(dispatchMapper.selectDispatchById(provinceId)==null) {
				return AjaxResult.error("省不存在");
			}
			vendingPoint.setProvince(provinceId);
		}
		if(StringUtils.isNotEmpty(city)) {
			String cityId = getCode(city);
			if(dispatchMapper.selectDispatchById(cityId)==null) {
				return AjaxResult.error("城市不存在");
			}
			vendingPoint.setCity(cityId);
		}
		if(StringUtils.isNotEmpty(district)) {
			String districtId = getCode(district);
			if(dispatchMapper.selectDispatchById(districtId)==null) {
				return AjaxResult.error("区域不存在");
			}
			vendingPoint.setDistrict(districtId);
		}
		if(StringUtils.isEmpty(adderss)) {
			return AjaxResult.error("详细地址不能为空");
		}
		if(adderss.length()>50) {
			return AjaxResult.error("详细地址不能超过50个字符");
		}
		//校验经度
		if(StringUtils.isEmpty(longitude)) {
			return AjaxResult.error("经度不能为空");
		}
		if(!Pattern.matches("(\\-|\\+)?(((\\d|[1-9]\\d|1[0-7]\\d|0{1,3})\\.\\d{0,6})|(\\d|[1-9]\\d|1[0-7]\\d|0{1,3})|180\\.0{0,6}|180)", vendingPoint.getLongitude())) {
			return AjaxResult.error("经度格式错误");
		}
		//校验纬度
		if(StringUtils.isEmpty(latitude)) {
			return AjaxResult.error("纬度不能为空");
		}
		if(!Pattern.matches("(\\-|\\+)?([0-8]?\\d{1}\\.\\d{0,6}|90\\.0{0,6}|[0-8]?\\d{1}|90)", vendingPoint.getLatitude())) {
			return AjaxResult.error("纬度格式错误");
		}
		if(description!=null&&description.length()>150) {
			return AjaxResult.error("描述不能超过150个字符");
		}
		return AjaxResult.success();
	}
	
	/**
	 * 分解行政区域
	 * 
	 * @param item
	 * @return
	 */
	private String getCode(String item) {
		//查找第一个数字所在位置
		Matcher matcher = Pattern.compile("[0-9]").matcher(item);
		matcher.find();
		int start = matcher.start();
		//截取数字
		return item.substring(start);
	}

	/**
	 * 查询未绑定的点位
	 * 
	 * @param vendingPoint
	 * @return
	 */
	@Override
	public List<VendingPoint> selectNotBindingVendingPointList(VendingPoint vendingPoint) {
		return vendingPointMapper.selectNotBindingVendingPointList(vendingPoint);
	}
	
}
