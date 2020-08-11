package com.manage.project.system.advert.vo;

/** 播放任务列表 */
public class Adevice {
	/** 设备编号 */
	private String deviceId;
	/** 设备平台 01:柜子设备端 02:微信公众号 03:售卖app */
	private String playerPlat;
	/** 状态 1:等待执行 2:开始执行 4:结束执行 */
	private String curState;
	
	public Adevice() {
		
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getPlayerPlat() {
		return playerPlat;
	}
	public void setPlayerPlat(String playerPlat) {
		this.playerPlat = playerPlat;
	}
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
}
