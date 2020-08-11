package com.manage.project.system.advert.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 广告播放对象设置，也叫播放任务列表 as_advert_device
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class AdvertDevice extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 播放对象编号 */
	private String advDeviceId;
	/** 广告编号 */
	private String advertId;
	/** 托管公司编号 */
	private String corpId;
	/** 设备编号 */
	private String deviceId;
	/** 设备平台 01:柜子设备端 02:微信公众号 03:售卖app */
	private String playerPlat;
	/** 状态 1:等待执行 2:开始执行 4:结束执行 */
	private String curState;
	/** 状态变化时间 */
	private String stateTime;
	/** 创建时间 */
	private String createTime;

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setAdvDeviceId(String advDeviceId) 
	{
		this.advDeviceId = advDeviceId;
	}

	public String getAdvDeviceId() 
	{
		return advDeviceId;
	}
	public void setAdvertId(String advertId) 
	{
		this.advertId = advertId;
	}

	public String getAdvertId() 
	{
		return advertId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setDeviceId(String deviceId) 
	{
		this.deviceId = deviceId;
	}

	public String getDeviceId() 
	{
		return deviceId;
	}
	public void setPlayerPlat(String playerPlat) 
	{
		this.playerPlat = playerPlat;
	}

	public String getPlayerPlat() 
	{
		return playerPlat;
	}
	public void setCurState(String curState) 
	{
		this.curState = curState;
	}

	public String getCurState() 
	{
		return curState;
	}
	public void setStateTime(String stateTime) 
	{
		this.stateTime = stateTime;
	}

	public String getStateTime() 
	{
		return stateTime;
	}
	public void setCreateTime(String createTime) 
	{
		this.createTime = createTime;
	}

	public String getCreateTime() 
	{
		return createTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("advDeviceId", getAdvDeviceId())
            .append("advertId", getAdvertId())
            .append("corpId", getCorpId())
            .append("deviceId", getDeviceId())
            .append("playerPlat", getPlayerPlat())
            .append("curState", getCurState())
            .append("stateTime", getStateTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
