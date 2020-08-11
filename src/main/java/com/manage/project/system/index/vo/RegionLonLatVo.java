package com.manage.project.system.index.vo;

import java.io.Serializable;

/**
 * 首页地图使用，返回点位行政区划及经纬度
 * @author xufeng
 *
 */
public class RegionLonLatVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String latitude;	// 纬度
	private String longitude;	// 经度
	private String region;	// 行政区划id
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	
}
