//package com.manage.project.system.vending.vo;
//
//import java.util.List;
//
//import com.manage.project.system.util.SystemUtil;
//
//public class CabinetLaneVo {
//	private List<Cols> lanes;	// 货柜货道
//	/** 记录编号 */
//	private String logid;
//	/** 售货机编号 */
//	private String siteId;
//	/** 售货机名称 */
//	private String siteName;
//	/** 货柜排列(从左到右) 1:开始递增 */
//	private Integer seqId;
//	/** 机型编码 */
//	private String deviceId;
//	/** 工控机编号 */
//	private String vmcId;
//	/** 外挂类型 1:是 2:否 */
//	private String hangType;
//	/** 串口号 */
//	private String pointCode;
//	/** 厂家编号 */
//	private String factoryId;
//	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
//	private String cabinetType;
//	/** 描述 */
//	private String description;
//	/** 托管公司编号 */
//	private String corpId;
//	/** 上报时间 */
//	private String reportTime;
//	// 货柜名称
//	private String cabinetName;
//	
//	/** 外挂类型名称 1:是 2:否 */
//	private String hangTypeName;
//	/** 机型 */
//	private String deviceName;
//	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
//	private String cabinetTypeName;
//	/** 厂家 */
//	private String factoryName;
//	/** 货柜id */
//	private String cabinetId;
//	/**
//	 * 柜子名称,页面展示tab名称,页面显示需要
//	 */
//	private String viewName;
//	
//	public String getViewName() {
//		return viewName;
//	}
//
//	public void setViewName(String viewName) {
//		this.viewName = viewName;
//	}
//
//	public String getCabinetId() {
//		return cabinetId;
//	}
//
//	public void setCabinetId(String cabinetId) {
//		this.cabinetId = cabinetId;
//	}
//
//	public String getCabinetName() {
//		return cabinetName;
//	}
//
//	public void setCabinetName(String cabinetName) {
//		this.cabinetName = cabinetName;
//	}
//
//	public void setLogid(String logid) 
//	{
//		this.logid = logid;
//	}
//
//	public String getLogid() 
//	{
//		return logid;
//	}
//	public void setSiteId(String siteId) 
//	{
//		this.siteId = siteId;
//	}
//
//	public String getSiteId() 
//	{
//		return siteId;
//	}
//	public void setSiteName(String siteName) 
//	{
//		this.siteName = siteName;
//	}
//
//	public String getSiteName() 
//	{
//		return siteName;
//	}
//	public void setSeqId(Integer seqId) 
//	{
//		this.seqId = seqId;
//	}
//
//	public Integer getSeqId() 
//	{
//		return seqId;
//	}
//	public void setDeviceId(String deviceId) 
//	{
//		this.deviceId = deviceId;
//	}
//
//	public String getDeviceId() 
//	{
//		return deviceId;
//	}
//	public void setVmcId(String vmcId) 
//	{
//		this.vmcId = vmcId;
//	}
//
//	public String getVmcId() 
//	{
//		return vmcId;
//	}
//	public void setHangType(String hangType) 
//	{
//		this.hangType = hangType;
//	}
//
//	public String getHangType() 
//	{
//		return hangType;
//	}
//	public void setPointCode(String pointCode) 
//	{
//		this.pointCode = pointCode;
//	}
//
//	public String getPointCode() 
//	{
//		return pointCode;
//	}
//	public void setFactoryId(String factoryId) 
//	{
//		this.factoryId = factoryId;
//	}
//
//	public String getFactoryId() 
//	{
//		return factoryId;
//	}
//	public void setCabinetType(String cabinetType) 
//	{
//		this.cabinetType = cabinetType;
//	}
//
//	public String getCabinetType() 
//	{
//		return cabinetType;
//	}
//	public void setDescription(String description) 
//	{
//		this.description = description;
//	}
//
//	public String getDescription() 
//	{
//		return description;
//	}
//	public void setCorpId(String corpId) 
//	{
//		this.corpId = corpId;
//	}
//
//	public String getCorpId() 
//	{
//		return corpId;
//	}
//	public void setReportTime(String reportTime) 
//	{
//		this.reportTime = reportTime;
//	}
//
//	public String getReportTime() 
//	{
//		return reportTime;
//	}
//
//	public List<Cols> getLanes() {
//		return lanes;
//	}
//
//	public void setLanes(List<Cols> lanes) {
//		this.lanes = lanes;
//	}
//
//	public String getHangTypeName() {
//		return SystemUtil.parseHangType(hangType);
//	}
//
//	public String getDeviceName() {
//		return deviceName;
//	}
//
//	public String getCabinetTypeName() {
//		return SystemUtil.parseCabinetType(cabinetType);
//	}
//
//	public String getFactoryName() {
//		return factoryName;
//	}
//}
