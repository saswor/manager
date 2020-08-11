package com.manage.project.system.vendingPoint.domain;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.common.utils.CacheUtils;
import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.common.Constant;
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.base.domain.Dispatch;
import com.manage.project.system.util.SystemUtil;

/**
 * 售货机绑定的点位表 as_vending_point
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingPoint extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 点位编号 */
	private String pointId;
	/** 区域编号 */
	private String districtId;
	/** 线路编号 */
	private String lineId;
	/** 点位编码 */
	@Excel(name="点位编码",column="A")
	private String code;	
	/** 点位名称 */
	@Excel(name="点位名称",column="B")
	private String name;
	/** 区域名称 */
	@Excel(name="区域名称",isImport=false)
	private String districtName;
	/** 线路名称 */
	@Excel(name="线路名称",column="C")
	private String lineName;
	@Excel(name="所属行政区",column="D",isImport=false)
	private String addresses;	// 所属行政区
	/** 国家 */
	@Excel(name="国家",column="D",isExport=false)
	private String country;
	/** 省份或州 */
	@Excel(name="省",column="E",isExport=false)
	private String province;
	/** 城市 */
	@Excel(name="市",column="F",isExport=false)
	private String city;
	/** 区 */
	@Excel(name="区",column="G",isExport=false)
	private String district;
	/** 详细地址 */
	@Excel(name="详细地址",column="H")
	private String adderss;
	/** 经度 */
	@Excel(name="经度",column="I")
	private String longitude;
	/** 维度 */
	@Excel(name="纬度",column="J")
	private String latitude;
	/** 地图类型 1:百度 2:google */
	private String locationType;
	/** 托管公司编号 */
	private String corpId;
	/** 描述 */
	@Excel(name="描述",column="K")
	private String description;	
	/** 创建时间 */
	@Excel(name="创建时间",column="L",isImport=false)
	private String createTime;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 商户名称
	 */
	private String corpName;

	public void setAddresses(String addresses) {
		this.addresses = addresses;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setPointId(String pointId) 
	{
		this.pointId = pointId;
	}

	public String getPointId() 
	{
		return pointId;
	}
	public void setDistrictId(String districtId) 
	{
		this.districtId = districtId;
	}

	public String getDistrictId() 
	{
		return districtId;
	}
	public void setLineId(String lineId) 
	{
		this.lineId = lineId;
	}

	public String getLineId() 
	{
		return lineId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setCountry(String country) 
	{
		this.country = country;
	}

	public String getCountry() 
	{
		return country;
	}
	public void setProvince(String province) 
	{
		this.province = province;
	}

	public String getProvince() 
	{
		return province;
	}
	public void setCity(String city) 
	{
		this.city = city;
	}

	public String getCity() 
	{
		return city;
	}
	public void setDistrict(String district) 
	{
		this.district = district;
	}

	public String getDistrict() 
	{
		return district;
	}
	public void setAdderss(String adderss) 
	{
		this.adderss = adderss;
	}

	public String getAdderss() 
	{
		return adderss;
	}
	public void setLongitude(String longitude) 
	{
		this.longitude = longitude;
	}

	public String getLongitude() 
	{
		return longitude;
	}
	public void setLatitude(String latitude) 
	{
		this.latitude = latitude;
	}

	public String getLatitude() 
	{
		return latitude;
	}
	public void setLocationType(String locationType) 
	{
		this.locationType = locationType;
	}

	public String getLocationType() 
	{
		return locationType;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getAddresses() {
		Object o = CacheUtils.get(Constant.DISPATCH_CACHE);
		if (o == null) {
			return "";
		}
		Map<String, Dispatch> map = (Map<String, Dispatch>)o;
		
		StringBuffer sb = new StringBuffer();
		Dispatch cou = map.get(country);	// 国家
		if (cou != null) {
			sb.append(cou.getName());
		}
		Dispatch pro = map.get(province);	// 省
		if (pro != null) {
			sb.append(pro.getName());
		}
		Dispatch ci = map.get(city);	// 市
		if (ci != null) {
			sb.append(ci.getName());
		}
		Dispatch dis = map.get(district);	// 区
		if (dis != null) {
			sb.append(dis.getName());
		}
		return sb.toString();
	}

	public String getCorpName() {
		Corp c = SystemUtil.getCorpById(corpId);
		if (c == null) {
			return "";
		}
		return c.getCorpName();
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("pointId", getPointId())
            .append("districtId", getDistrictId())
            .append("lineId", getLineId())
            .append("name", getName())
            .append("country", getCountry())
            .append("province", getProvince())
            .append("city", getCity())
            .append("district", getDistrict())
            .append("adderss", getAdderss())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("locationType", getLocationType())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
