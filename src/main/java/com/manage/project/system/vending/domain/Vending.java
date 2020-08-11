package com.manage.project.system.vending.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.common.utils.StringUtils;
import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 售货机的基本，主柜表 as_vending
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class Vending extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 售货机编号 */
	private String siteId;
	/** 售货机编码 */
	@Excel(name="售货机编码",column="A")
	private String siteCode;
	/** 售货机名称 */
	@Excel(name="售货机名称",column="B")
	private String siteName;
	/** 厂家编号 */
	private String factoryId;
	/**
	 * 厂家名称
	 */
	@Excel(name="厂家",column="C")
	private String factoryName;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetType;
	@Excel(name="货柜类型",column="D")
	private String cabinetTypeName;
	/** 机型编码 */
	private String deviceId;
	/** 机型 */
	@Excel(name="机型",column="E")
	private String deviceCode;
	/** 区域编号 */
	private String districtId;
	/** 线路编号 */
	private String lineId;
	/** 线路名称 */
	@Excel(name="线路",isImport=false)
	private String lineName;
	/** 点位编号 */
	private String pointId;
	/** 点位名称 */
	@Excel(name="点位",column="F")
	private String pointName;
	/** 经度 */
	private String longitude;
	/** 维度 */
	private String latitude;
	/** 网络制式 1:GPRS  2:网口 */
	private String netWork;
	/** 网络制式 1:GPRS  2:网口 */
	@Excel(name="网络类型",column="G")
	private String netWorkName;
	/** 售卖状态 1:可售卖 2:不可售卖 */
	private String sellState;
	/**
	 * 售卖状态名称
	 */
	@Excel(name="售卖状态",column="H")
	private String sellStateName;
	/** 支付类型 可多选从左到右每一位代表支持一种。(1支付宝 2:微信 3:百度钱包 ) */
	private String payType;
	/** 支付类型 可多选从左到右每一位代表支持一种。(1支付宝 2:微信 3:百度钱包 ) */
	@Excel(name="支付类型",column="I")
	private String payTypeName;
	/** 播放视频 1:支持 2:无 */
	private String mediaType;
	/** 播放视频 1:支持 2:无 */
	@Excel(name="播放视频",column="J")
	private String mediaTypeName;
	/** 上线日期 */
	private String onlineTime;
	/** 初始化时间 */
	private String initTime;
	/** 售货机状态 1:已认证售货机 2:未认证售货机 3:已删除售货机 */
	private String curState;
	/** 状态时间 */
	private String stateTime;
	
	/** 网络状态(0:在线 1:离线) */
	private String netSateName;
	/** 网络状态(0:在线 1:离线) */
	private String netSate;
	/** 网络状态时间(刷新时间/离线时间) */
	private String netTime;
	
	/** 状态时间 */
	private String sellTime;
	/** 货道数量 */
	private Integer laneNum;
	/** 商品存放最大数 */
	private Integer pmaxNum;
	/** 当前商品数量 */
	private Integer pcurNum;
	/** 库存级别(用于进度条显示分三级) */
	private Integer stockLevel;
	/** 托管公司编号 */
	private String corpId;
	/** 配货模板编号 */
	private String mconfigId;
	/** 售货机配置文件路径 */
	private String configFile;
	/** 描述 */
	private String description;
	/** 详细地址 */
	private String address;

	private String supplyState;
	private String supplySTime;
	private String underState;
	private String underSTime;
	
	public String getMediaTypeName() {
		return mediaTypeName;
	}

	public void setMediaTypeName(String mediaTypeName) {
		this.mediaTypeName = mediaTypeName;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public void setCabinetTypeName(String cabinetTypeName) {
		this.cabinetTypeName = cabinetTypeName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public void setNetWorkName(String netWorkName) {
		this.netWorkName = netWorkName;
	}

	public void setSellStateName(String sellStateName) {
		this.sellStateName = sellStateName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public void setNetSateName(String netSateName) {
		this.netSateName = netSateName;
	}

	/**平台版本 01:安卓 02:windows 03:单片机 */
	private String platType;
	
	public String getPlatType() {
		return platType;
	}

	public void setPlatType(String platType) {
		this.platType = platType;
	}

	public String getSupplyState() {
		return supplyState;
	}

	public void setSupplyState(String supplyState) {
		this.supplyState = supplyState;
	}

	public String getSupplySTime() {
		return supplySTime;
	}

	public void setSupplySTime(String supplySTime) {
		this.supplySTime = supplySTime;
	}

	public String getUnderState() {
		return underState;
	}

	public void setUnderState(String underState) {
		this.underState = underState;
	}

	public Integer getPmaxNum() {
		return pmaxNum;
	}

	public void setPmaxNum(Integer pmaxNum) {
		this.pmaxNum = pmaxNum;
	}

	public Integer getPcurNum() {
		return pcurNum;
	}

	public void setPcurNum(Integer pcurNum) {
		this.pcurNum = pcurNum;
	}

	public String getLineName() {
		if(StringUtils.isNotEmpty(lineName)) {
			return lineName;
		}
		return SystemUtil.getVendingLineNameFromCache(lineId);
	}

	public String getNetSateName() {
		if(StringUtils.isNotEmpty(netSateName)) {
			return netSateName;
		}
		return SystemUtil.parseNetSate(netSate);
	}

	public String getPayTypeName() {
		if(StringUtils.isNotEmpty(payTypeName)) {
			return payTypeName;
		}
		return SystemUtil.parsePayType(payType);
	}

	public String getNetWorkName() {
		if(StringUtils.isNotEmpty(netWorkName)) {
			return netWorkName;
		}
		return SystemUtil.parseNetWork(netWork);
	}

	public String getFactoryName() {
		if(StringUtils.isNotEmpty(factoryName)) {
			return factoryName;
		}
		return SystemUtil.parseFactoryId(factoryId);
	}

	//01:商店机 02:弹簧机 03:格子机 
	public String getCabinetTypeName() {
//		if (cabinetType == null) {
//			return "";
//		}
//		if (cabinetType.equals("01")) {
//			return "商店机";
//		} else if (cabinetType.equals("02")) {
//			return "弹簧机";
//		} else if (cabinetType.equals("03")) {
//			return "格子机 ";
//		}
		if(StringUtils.isNotEmpty(cabinetTypeName)) {
			return cabinetTypeName;
		}
		return SystemUtil.parseCabinetType(cabinetType);
	}

	public String getSellStateName() {
		if(StringUtils.isNotEmpty(sellStateName)) {
			return sellStateName;
		}
		return SystemUtil.parseSellState(sellState);
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSiteId(String siteId) 
	{
		this.siteId = siteId;
	}

	public String getSiteId() 
	{
		return siteId;
	}
	public void setSiteCode(String siteCode) 
	{
		this.siteCode = siteCode;
	}

	public String getSiteCode() 
	{
		return siteCode;
	}
	public void setSiteName(String siteName) 
	{
		this.siteName = siteName;
	}

	public String getSiteName() 
	{
		return siteName;
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
	public void setLongitude(String longitude) 
	{
		this.longitude = longitude;
	}

	public String getLongitude() 
	{
		return longitude;
	}
	public void setLatitude(String latitude) 
	{
		this.latitude = latitude;
	}

	public String getLatitude() 
	{
		return latitude;
	}
	public void setPayType(String payType) 
	{
		this.payType = payType;
	}

	public String getPayType() 
	{
		return payType;
	}
	public void setMediaType(String mediaType) 
	{
		this.mediaType = mediaType;
	}

	public String getMediaType() 
	{
		return mediaType;
	}
	public void setOnlineTime(String onlineTime) 
	{
		this.onlineTime = onlineTime;
	}

	public String getOnlineTime() 
	{
		return onlineTime;
	}
	public void setInitTime(String initTime) 
	{
		this.initTime = initTime;
	}

	public String getInitTime() 
	{
		return initTime;
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
	public void setNetWork(String netWork) 
	{
		this.netWork = netWork;
	}

	public String getNetWork() 
	{
		return netWork;
	}
	public void setNetSate(String netSate) 
	{
		this.netSate = netSate;
	}

	public String getNetSate() 
	{
		return netSate;
	}
	public void setNetTime(String netTime) 
	{
		this.netTime = netTime;
	}

	public String getNetTime() 
	{
		return netTime;
	}
	public void setSellState(String sellState) 
	{
		this.sellState = sellState;
	}

	public String getSellState() 
	{
		return sellState;
	}
	public void setSellTime(String sellTime) 
	{
		this.sellTime = sellTime;
	}

	public String getSellTime() 
	{
		return sellTime;
	}
	public void setLaneNum(Integer laneNum) 
	{
		this.laneNum = laneNum;
	}

	public Integer getLaneNum() 
	{
		return laneNum;
	}

	public void setStockLevel(Integer stockLevel) 
	{
		this.stockLevel = stockLevel;
	}

	public Integer getStockLevel() 
	{
		return stockLevel;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	
	

	public void setConfigFile(String configFile) 
	{
		this.configFile = configFile;
	}

	public String getConfigFile() 
	{
		return configFile;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getAddress() 
	{
		return address;
	}

    public String getMconfigId() {
		return mconfigId;
	}

	public void setMconfigId(String mconfigId) {
		this.mconfigId = mconfigId;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("siteId", getSiteId())
            .append("siteCode", getSiteCode())
            .append("siteName", getSiteName())
            .append("factoryId", getFactoryId())
            .append("cabinetType", getCabinetType())
            .append("deviceId", getDeviceId())
            .append("districtId", getDistrictId())
            .append("lineId", getLineId())
            .append("pointId", getPointId())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("payType", getPayType())
            .append("mediaType", getMediaType())
            .append("onlineTime", getOnlineTime())
            .append("initTime", getInitTime())
            .append("curState", getCurState())
            .append("stateTime", getStateTime())
            .append("netWork", getNetWork())
            .append("netSate", getNetSate())
            .append("netTime", getNetTime())
            .append("sellState", getSellState())
            .append("sellTime", getSellTime())
            .append("laneNum", getLaneNum())
            .append("stockLevel", getStockLevel())
            .append("corpId", getCorpId())
            .append("configFile", getConfigFile())
            .append("createTime", getCreateTime())
            .append("description", getDescription())
            .append("address", getAddress())
            .toString();
    }
}
