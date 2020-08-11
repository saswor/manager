package com.manage.project.system.advert.vo;

import org.apache.commons.lang.SystemUtils;

import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;

/**
 * 广告编辑使用，投放设备vo
 * @author xufeng
 *
 */
public class EditAdvertDeviceVo {
	private String advDeviceId;	// 播放对象编号
	private String deviceId; // 售卖机id或公众号
	private String playerPlat;	// 设备平台 01:柜子设备端 02:微信公众号 03:售卖app
	private String deviceName;	// 售卖机或公众号名称
	private String curState;
	
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
	public String getDeviceName() {
		if (playerPlat != null && playerPlat.equals("01")) {
			Vending v = SystemUtil.getVendingBase(deviceId);
			if (v != null) {
				return v.getSiteName();
			}
		}
		return "";
	}
	public String getAdvDeviceId() {
		return advDeviceId;
	}
	public void setAdvDeviceId(String advDeviceId) {
		this.advDeviceId = advDeviceId;
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
	
}
