package com.manage.project.system.vending.vo;

import java.util.List;

import com.manage.project.system.vending.domain.VendingCabinet;

/**
 * 售卖机详情页面 vo
 * @author xufeng
 *
 */
public class VendingViewVo {
	private String logid;	// 售卖机logid
	/** 详细地址 */
	private String address;
	/** 托管公司编号 */
	private String corpId;
	/** 托管公司编号 */
	private String corpName;
	/**全部详细地址 */
	private String allAddress;
	/** 售货机名称 */
	private String siteName;
	
	// 基本信息
	/** 售货机编号 */
	private String siteId;
	/** 售货机编码 */
	private String siteCode;
	/** 点位编号 */
	private String pointId;
	/** 点位名称  */
	private String pointName;
	/** 线路编号 */
	private String lineId;
	/** 线路名称 */
	private String lineName;
	/** 区域编号 */
	private String districtId;
	/** 区域名称 */
	private String districtName;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetType;
	/** 货柜类型名称 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetTypeName;
	/** 厂家编号 */
	private String factoryId;
	/** 厂家名称 */
	private String factoryName;
	/** 机型编码 */
	private String deviceId;
	/** 机型 */
	private String device;
	/** 机型名称 */
	private String deviceName;
	/** 网络制式 1:GPRS  2:网口 */
	private String netWork;
	/** 网络制式 1:GPRS  2:网口 */
	private String netWorkName;
	/** 支付类型 可多选从左到右每一位代表支持一种。(1支付宝 2:微信 3:百度钱包 ) */
	private String payType;
	// 支付类型的数组型态，用于前端校验
	private List<String> payTypeList;
	private String payTypeName;
	/** 售卖状态 1:可售卖 2:不可售卖 */
	private String sellState;
	/**
	 * 售卖状态名称
	 */
	private String sellStateName;
	/** 网络状态(0:在线 1:离线) */
	private String netSate;
	/** 网络状态(0:在线 1:离线) */
	private String netSateName;
	/** 状态时间 */
	private String stateTime;
	/** 持续时间 */
	private String continueTime;
	/**
	 * 主柜cabinetId
	 */
	private String cabinetId;
	
	/** 主柜记录编号 */
	private String cabinetLogid;
	
	/**
	 * 柜子名称,页面展示tab名称,页面显示需要
	 */
	private String viewName;
	
	private List<ViewCols> lanes;	// 主货柜货道
//	private List<VendingLane> lanes;	// 主柜货道
	
	private List<VendingCabinet> cabinets;	// 挂载柜

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public List<String> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<String> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getCabinetLogid() {
		return cabinetLogid;
	}

	public void setCabinetLogid(String cabinetLogid) {
		this.cabinetLogid = cabinetLogid;
	}

	public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getAllAddress() {
		return allAddress;
	}

	public void setAllAddress(String allAddress) {
		this.allAddress = allAddress;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getCabinetType() {
		return cabinetType;
	}

	public void setCabinetType(String cabinetType) {
		this.cabinetType = cabinetType;
	}

	public String getCabinetTypeName() {
		return cabinetTypeName;
	}

	public void setCabinetTypeName(String cabinetTypeName) {
		this.cabinetTypeName = cabinetTypeName;
	}

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getNetWork() {
		return netWork;
	}

	public void setNetWork(String netWork) {
		this.netWork = netWork;
	}

	public String getNetWorkName() {
		return netWorkName;
	}

	public void setNetWorkName(String netWorkName) {
		this.netWorkName = netWorkName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getSellState() {
		return sellState;
	}

	public void setSellState(String sellState) {
		this.sellState = sellState;
	}

	public String getSellStateName() {
		return sellStateName;
	}

	public void setSellStateName(String sellStateName) {
		this.sellStateName = sellStateName;
	}

	public String getNetSate() {
		return netSate;
	}

	public void setNetSate(String netSate) {
		this.netSate = netSate;
	}

	public String getNetSateName() {
		return netSateName;
	}

	public void setNetSateName(String netSateName) {
		this.netSateName = netSateName;
	}

	public String getStateTime() {
		return stateTime;
	}

	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}

	public String getContinueTime() {
		return continueTime;
	}

	public void setContinueTime(String continueTime) {
		this.continueTime = continueTime;
	}

	public List<VendingCabinet> getCabinets() {
		return cabinets;
	}

	public void setCabinets(List<VendingCabinet> cabinets) {
		this.cabinets = cabinets;
	}
//
//	public List<VendingLane> getLanes() {
//		return lanes;
//	}
//
//	public void setLanes(List<VendingLane> lanes) {
//		this.lanes = lanes;
//	}

	public List<ViewCols> getLanes() {
		return lanes;
	}

	public void setLanes(List<ViewCols> lanes) {
		this.lanes = lanes;
	}
}
