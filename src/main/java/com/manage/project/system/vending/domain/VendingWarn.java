package com.manage.project.system.vending.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.common.utils.DateUtils;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 设备告警表 as_vending_warn
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingWarn extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	private String btime;	// 开始时间
	
	private String etime;	// 结束时间
	
	/** 记录编号 */
	private String logid;
	/** 事件编号 */
	private String eventId;
	/** 区域编号 */
	private String districtId;
	/** 线路编号 */
	private String lineId;
	/** 点位编号 */
	private String pointId;
	/** 售货机编号 */
	private String siteId;
	/** 售货机名称 */
	private String siteName;
	/** 事件类型 所有事件类型:00  01:系统故障 02:通讯故障 03:网络异常 04:缺货 */
	private String warnType;
	/** 告警状态 1:产生 2:恢复 */
	private String warnState;
	/** 告警内容 */
	private String warnCont;
	/** 告警级别 1:严重 2:重要 3:一般 */
	private String level;
	/** 状态 1:未解决 2:已处理 */
	private String curState;
	/** 托管公司编号 */
	private String corpId;
	
	/** 状态 1:未解决 2:已处理 */
	private String curStateName;
	
	/** 告警级别 1:严重 2:重要 3:一般 */
	private String levelName;
	
	/** 告警状态 1:产生 2:恢复 */
	private String warnStateName;
	
	/** 事件类型 所有事件类型:00  01:系统故障 02:通讯故障 03:网络异常 04:缺货 */
	private String warnTypeName;
	
	/** 区域名称 */
	private String districtName;
	/** 线路名称 */
	private String lineName;
	/** 点位名称 */
	private String pointName;
	
	private String continuedTime;// 持续时间
	
	public String getContinuedTime() {
		if (this.getCreateTime() == null) {
			return "";
		}
		return DateUtils.subTime(DateUtils.getNowDate(), DateUtils.parseStrToDate(this.getCreateTime()));
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setEventId(String eventId) 
	{
		this.eventId = eventId;
	}

	public String getEventId() 
	{
		return eventId;
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
	public void setPointId(String pointId) 
	{
		this.pointId = pointId;
	}

	public String getPointId() 
	{
		return pointId;
	}
	public void setSiteId(String siteId) 
	{
		this.siteId = siteId;
	}

	public String getSiteId() 
	{
		return siteId;
	}
	public void setSiteName(String siteName) 
	{
		this.siteName = siteName;
	}

	public String getSiteName() 
	{
		return siteName;
	}
	public void setWarnType(String warnType) 
	{
		this.warnType = warnType;
	}

	public String getWarnType() 
	{
		return warnType;
	}
	public void setWarnState(String warnState) 
	{
		this.warnState = warnState;
	}

	public String getWarnState() 
	{
		return warnState;
	}
	public void setWarnCont(String warnCont) 
	{
		this.warnCont = warnCont;
	}

	public String getWarnCont() 
	{
		return warnCont;
	}
	public void setLevel(String level) 
	{
		this.level = level;
	}

	public String getLevel() 
	{
		return level;
	}
	public void setCurState(String curState) 
	{
		this.curState = curState;
	}

	public String getCurState() 
	{
		return curState;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

    public String getBtime() {
		return btime;
	}

	public void setBtime(String btime) {
		this.btime = btime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}
	
	public String getCurStateName() {
		return SystemUtil.parseWarnCurState(curState);
	}

	public String getLevelName() {
		return SystemUtil.parseWarnLevel(level);
	}

	public String getWarnStateName() {
		return SystemUtil.parseWarnState(warnState);
	}

	public String getWarnTypeName() {
		return SystemUtil.parseWarnType(warnType);
	}

	public String getDistrictName() {
		return SystemUtil.getVendingDistrictNameFromCache(districtId);
	}

	public String getLineName() {
		return SystemUtil.getVendingLineNameFromCache(lineId);
	}

	public String getPointName() {
		return SystemUtil.getVendingPointNameFromCache(pointId);
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("eventId", getEventId())
            .append("districtId", getDistrictId())
            .append("lineId", getLineId())
            .append("pointId", getPointId())
            .append("siteId", getSiteId())
            .append("siteName", getSiteName())
            .append("warnType", getWarnType())
            .append("warnState", getWarnState())
            .append("warnCont", getWarnCont())
            .append("level", getLevel())
            .append("curState", getCurState())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
