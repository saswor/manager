package com.manage.project.system.vendingEvent.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 售货机的事件列表 as_vending_event
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingEvent extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
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
	/** 事件类型 所有事件类型:00  app升级:01 固件升级:02 vsi升级:03  门事件:04 工控机重启:05 补货开始:06 补货结束:07 同步货道:08 同步商品:09 同步机型:10 下发广告:11 修改货道:12 点位更改:13 取拖事件:14  */
	private String eventType;
	/** 托管公司编号 */
	private String corpId;
	
	private String btime;	// 开始时间
	
	private String etime;	// 结束时间
	
	/** 线路名称 */
	private String lineName;
	/** 点位名称 */
	private String pointName;
	
	/** 事件类型 所有事件类型:00  app升级:01 固件升级:02 vsi升级:03  门事件:04 工控机重启:05 补货开始:06 补货结束:07 同步货道:08 同步商品:09 同步机型:10 下发广告:11 修改货道:12 点位更改:13 取拖事件:14  */
	private String eventTypeName;
	
	public String getEventTypeName() {
		return SystemUtil.parseEventType(eventType);
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
	public void setEventType(String eventType) 
	{
		this.eventType = eventType;
	}

	public String getEventType() 
	{
		return eventType;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
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
            .append("eventType", getEventType())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
