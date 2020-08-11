package com.manage.project.system.advert.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 跳转到广告编辑页面vo
 * @author xufeng
 *
 */
public class AdvertEditVo {
	
	private String advertId;	//广告id,删除与编辑时使用
	/** 广告名称 */
	private String name;
	/** 投放方式 1:立即投放 2:延时投放 */
	private String deliveryType;
	/**延时投放时间*/
	private String lazyTime;
	/** 播放平台 01:柜子设备端 02:微信公众号 03:售卖app */
	private String playerPlat;
	/** 下发类型 1:全量 2:增量 */
	private String operType;
	/** 策略  */
	private List<Strategy> strategies;
	/** 播放任务列表  */
	private List<EditAdvertDeviceVo> adevices;
	
	private List<AdvertPlat> advertPlats=new ArrayList<AdvertPlat>();
	
	public String getLazyTime() {
		return lazyTime;
	}
	public void setLazyTime(String lazyTime) {
		this.lazyTime = lazyTime;
	}
	public List<AdvertPlat> getAdvertPlats() {
		return advertPlats;
	}
	public void setAdvertPlats(List<AdvertPlat> advertPlats) {
		this.advertPlats = advertPlats;
	}
	public String getAdvertId() {
		return advertId;
	}
	public void setAdvertId(String advertId) {
		this.advertId = advertId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getPlayerPlat() {
		return playerPlat;
	}
	public void setPlayerPlat(String playerPlat) {
		this.playerPlat = playerPlat;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public List<Strategy> getStrategies() {
		return strategies;
	}
	public void setStrategies(List<Strategy> strategies) {
		this.strategies = strategies;
	}
	public List<EditAdvertDeviceVo> getAdevices() {
		return adevices;
	}
	public void setAdevices(List<EditAdvertDeviceVo> adevices) {
		this.adevices = adevices;
	}
}
