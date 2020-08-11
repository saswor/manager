package com.manage.project.system.advert.vo;

import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 广告任务列表结果vo
 * @author xufeng
 *
 */
public class AdvertDeviceSelectVo {
	private String advName;	// 广告名称
	private String deviceId; // 售卖机id
	private String siteName; // 售卖机名称
	private String curState;	// 状态
	private String curStateName;	// 状态
	private String createTime;	// 创建时间
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getCurStateName() {
		return SystemUtil.parseFavState(curState);
	}
	public String getAdvName() {
		return advName;
	}
	public void setAdvName(String advName) {
		this.advName = advName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
