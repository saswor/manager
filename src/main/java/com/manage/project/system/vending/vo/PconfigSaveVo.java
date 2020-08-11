package com.manage.project.system.vending.vo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.project.system.util.SystemUtil;

public class PconfigSaveVo {
	/** 记录编号 */
	private String logid;
	/** 模板编号 */
	private String mConfigId;
	/** 模板名称 */
	private String name;
	/** 货柜排列(从左到右) */
	private Integer seqId;
	/** 厂家编号 */
	private String factoryId;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetType;
	/** 机型编码 */
	private String deviceId;
	/** 机型编号 */
	private String device;
	/** 托管公司编号 */
	private String corpId;

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	/** 厂家名称 */
	private String factoryName;
	/** 货柜类型名称 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetTypeName;
	
	private String cabs;	// 货柜id,以逗号分割,用于关联货柜使用
	
//	private List<VendingPlane> planes;	// 模板货道 
	private List<Cols> lanes;	// 货柜货道

	public String getmConfigId() {
		return mConfigId;
	}

	public void setmConfigId(String mConfigId) {
		this.mConfigId = mConfigId;
	}

	public String getCabs() {
		return cabs;
	}

	public void setCabs(String cabs) {
		this.cabs = cabs;
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
//		return "";
		return SystemUtil.parseFactoryId(factoryId);
	}

	//01:商店机 02:弹簧机 03:格子机 
	public String getCabinetTypeName() {
//		return "";
		return SystemUtil.parseCabinetType(cabinetType);
	}
	
	public List<Cols> getLanes() {
		return lanes;
	}

	public void setLanes(List<Cols> lanes) {
		this.lanes = lanes;
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
            
            .append("corpId", getCorpId())
            .toString();
    }
}
