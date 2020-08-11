package com.manage.project.system.vending.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.vo.Cols;

/**
 * 售货机机型管理，包括主柜和副柜机型表 as_vending_model
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingModel extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 机型编号 */
	private String deviceCode;
	/** 厂家编号 */
	private String factoryId;
	/** 机型编码 */
	private String deviceId;
	/** 起始编号 */
	private Integer startId;
	/** 行数 */
	private Integer row;
	/** 列数 */
	private Integer col;
	/** 行中货道数 */
	private Integer rowNum;
	/** 排数 */
	private Integer arrange;
	/** 总货道数(根据行、列、排、货道自动计算) */
	private Integer laneNum;
	/**存放容量*/
	private Integer capacity;
	/**阈值*/
	private Integer warnCap;
	/** 托管公司编号 */
	private String corpId;
	/**
	 * 货柜类型 01:商店机 02:弹簧机 03:格子机
	 */
	private String cabinetType;
	
	/** 厂家名称*/
	private String factoryName;
	
	/**
	 * 货柜类型 01:商店机 02:弹簧机 03:格子机
	 */
	private String cabinetTypeName;

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getWarnCap() {
		return warnCap;
	}

	public void setWarnCap(Integer warnCap) {
		this.warnCap = warnCap;
	}

	public String getFactoryName() {
		return SystemUtil.parseFactoryId(factoryId);
	}

	public String getCabinetTypeName() {
		return SystemUtil.parseCabinetType(cabinetType);
	}

	public String getCabinetType() {
		return cabinetType;
	}

	public void setCabinetType(String cabinetType) {
		this.cabinetType = cabinetType;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setDeviceCode(String deviceCode) 
	{
		this.deviceCode = deviceCode;
	}

	public String getDeviceCode() 
	{
		return deviceCode;
	}
	public void setFactoryId(String factoryId) 
	{
		this.factoryId = factoryId;
	}

	public String getFactoryId() 
	{
		return factoryId;
	}
	public void setDeviceId(String deviceId) 
	{
		this.deviceId = deviceId;
	}

	public String getDeviceId() 
	{
		return deviceId;
	}
	public void setStartId(Integer startId) 
	{
		this.startId = startId;
	}

	public Integer getStartId() 
	{
		return startId;
	}
	public void setRow(Integer row) 
	{
		this.row = row;
	}

	public Integer getRow() 
	{
		return row;
	}
	public void setCol(Integer col) 
	{
		this.col = col;
	}

	public Integer getCol() 
	{
		return col;
	}
	public void setRowNum(Integer rowNum) 
	{
		this.rowNum = rowNum;
	}

	public Integer getRowNum() 
	{
		return rowNum;
	}
	public void setArrange(Integer arrange) 
	{
		this.arrange = arrange;
	}

	public Integer getArrange() 
	{
		return arrange;
	}
	public void setLaneNum(Integer laneNum) 
	{
		this.laneNum = laneNum;
	}

	public Integer getLaneNum() 
	{
		return laneNum;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("deviceCode", getDeviceCode())
            .append("factoryId", getFactoryId())
            .append("deviceId", getDeviceId())
            .append("startId", getStartId())
            .append("row", getRow())
            .append("col", getCol())
            .append("rowNum", getRowNum())
            .append("arrange", getArrange())
            .append("laneNum", getLaneNum())
            .append("createTime", getCreateTime())
            .append("corpId", getCorpId())
            .toString();
    }
}
