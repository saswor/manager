package com.manage.project.system.advert.vo;

import java.util.List;

/**
 * 保存广告位vo
 * @author xufeng
 *
 */
public class AdvertSaveVo {
	
	private String advertId;	//广告id,删除与编辑时使用
	/** 广告名称 */
	private String name;
	/** 投放方式 1:立即投放 2:延时投放 */
	private String deliveryType;
	/** 播放平台 01:柜子设备端 02:微信公众号 03:售卖app */
	private String playerPlat;
	/** 下发类型 1:全量 2:增量 */
	private String operType;
	/** 延时投放时间(yyyy-MM-dd HH:mm:ss) */
	private String lazyTime;
	// 状态 1:等待执行 2:执行中 3:执行完成 4:已删除
	private String curState;
	/** 策略  */
	private List<Strategy> strategies;
	/** 播放任务列表  */
	private List<Adevice> adevices;
	/**自定义广告位*/
	private List<AdvertPlat> advertPlats;

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
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
	public AdvertSaveVo() {
		
	}
	public String getLazyTime() {
		return lazyTime;
	}
	public void setLazyTime(String lazyTime) {
		this.lazyTime = lazyTime;
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
	
	public List<Adevice> getAdevices() {
		return adevices;
	}
	public void setAdevices(List<Adevice> adevices) {
		this.adevices = adevices;
	}

	
	
	
	
}
