package com.manage.project.system.vending.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 售货机配货模板表 as_vending_pconfig
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingPconfig extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 模板编号 */
	private String mConfigId;
	/** 模板名称 */
	@Excel(name = "模板名称", column = "A")
	private String name;
	/** 货柜排列(从左到右) */
	private Integer seqId;
	/** 厂家编号 */	
	private String factoryId;
	/** 厂家名称 */
	@Excel(name = "厂家名称", column = "B")
	private String factoryName;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetType;
	/** 货柜类型名称 01:商店机 02:弹簧机 03:格子机 */
	@Excel(name = "货柜类型", column = "C")
	private String cabinetTypeName;
	/** 机型编号 */
	private String deviceId;
	/** 机型编码 */
	@Excel(name = "机型编码", column = "D")
	private String deviceCode;
	/** 托管公司编号 */
	private String corpId;
	
	private List<VendingPlane> planes;	// 模板货道 

	public String getmConfigId() {
		return mConfigId;
	}

	public void setmConfigId(String mConfigId) {
		this.mConfigId = mConfigId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public void setCabinetTypeName(String cabinetTypeName) {
		this.cabinetTypeName = cabinetTypeName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setMConfigId(String mConfigId) 
	{
		this.mConfigId = mConfigId;
	}

	public String getMConfigId() 
	{
		return mConfigId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setSeqId(Integer seqId) 
	{
		this.seqId = seqId;
	}

	public Integer getSeqId() 
	{
		return seqId;
	}
	public void setFactoryId(String factoryId) 
	{
		this.factoryId = factoryId;
	}

	public String getFactoryId() 
	{
		return factoryId;
	}
	public void setCabinetType(String cabinetType) 
	{
		this.cabinetType = cabinetType;
	}

	public String getCabinetType() 
	{
		return cabinetType;
	}
	public void setDeviceId(String deviceId) 
	{
		this.deviceId = deviceId;
	}

	public String getDeviceId() 
	{
		return deviceId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public String getFactoryName() {	
		return SystemUtil.parseFactoryId(factoryId);
	}

	//01:商店机 02:弹簧机 03:格子机 
	public String getCabinetTypeName() {
		return SystemUtil.parseCabinetType(cabinetType);
	}
	
    public List<VendingPlane> getPlanes() {
		return planes;
	}

	public void setPlanes(List<VendingPlane> planes) {
		this.planes = planes;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("mConfigId", getMConfigId())
            .append("name", getName())
            .append("seqId", getSeqId())
            .append("factoryId", getFactoryId())
            .append("cabinetType", getCabinetType())
            .append("deviceId", getDeviceId())
            .append("createTime", getCreateTime())
            .append("corpId", getCorpId())
            .toString();
    }
}
