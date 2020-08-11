package com.manage.project.system.vending.vo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 售卖机vo
 * @author xufeng
 *
 */
public class VendingVo {
	private List<VendingCabinetVo> cabinets;	// 货柜
	private List<Cols> lanes;	// 主货柜货道
	/** 记录编号 */
	private String logid;
	/** 主柜记录编号 */
	private String cabinetLogid;
	/** 主柜货柜id */
	private String cabinetId;
	/** 售货机编号 */
	private String siteId;
	/** 售货机编码 */
	private String siteCode;
	/** 售货机名称 */
	private String siteName;
	/** 厂家编号 */
	private String factoryId;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetType;
	/** 机型编码 */
	private String deviceId;
	/** 区域编号 */
	private String districtId;
	/** 线路编号 */
	private String lineId;
	/** 点位编号 */
	private String pointId;
	/** 经度 */
	private String longitude;
	/** 维度 */
	private String latitude;
	/** 支付类型 可多选从左到右每一位代表支持一种。(1支付宝 2:微信 3:百度钱包 ) */
	private String payType;
	// 支付类型的数组型态，用于前端校验
	private List<String> payTypeList;
	/** 播放视频 1:支持 2:无 */
	private String mediaType;
	/** 上线日期 */
	private String onlineTime;
	/** 初始化时间 */
	private String initTime;
	/** 售货机状态 1:已认证售货机 2:未认证售货机 3:已删除售货机 */
	private String curState;
	/** 状态时间 */
	private String stateTime;
	/** 网络制式 1:GPRS  2:网口 */
	private String netWork;
	/** 网络状态(0:在线 1:离线) */
	private String netSate;
	/** 网络状态时间(刷新时间/离线时间) */
	private String netTime;
	/** 售卖状态 1:可售卖 2:不可售卖 */
	private String sellState;
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
	/**
	 * 售卖状态名称
	 */
	private String sellStateName;

	private String cabinetTypeName;
	/**
	 * 柜子名称,页面展示tab名称,页面显示需要
	 */
	private String viewName;
	
	/** 托管公司编号 */
	private String corpName;
	
	/**全部详细地址 */
	private String allAddress;
	/** 点位名称  */
	private String pointName;
	/** 线路名称 */
	private String lineName;
	/** 区域名称 */
	private String districtName;
	/** 厂家名称 */
	private String factoryName;
	/** 机型名称 */
	private String deviceName;
	/** 网络制式 1:GPRS  2:网口 */
	private String netWorkName;
	private String payTypeName;
	/** 网络状态(0:在线 1:离线) */
	private String netSateName;
	
	/** 持续时间 */
	private String continueTime;
	/** 机型 */
	private String device;
	
	private boolean dis = false;
	
	// 前端使用，控制选择模板按钮，货柜有商品返回false
	private boolean isShowTemplate = true;
	
	public boolean getIsShowTemplate() {
		return isShowTemplate;
	}

	public void setIsShowTemplate(boolean isShowTemplate) {
		this.isShowTemplate = isShowTemplate;
	}

	public boolean isDis() {
		return dis;
	}

	public void setDis(boolean dis) {
		this.dis = dis;
	}

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

	public String getContinueTime() {
		return continueTime;
	}

	public void setContinueTime(String continueTime) {
		this.continueTime = continueTime;
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

	public String getMconfigId() {
		return mconfigId;
	}

	public void setMconfigId(String mconfigId) {
		this.mconfigId = mconfigId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getNetWorkName() {
		return netWorkName;
	}

	public void setNetWorkName(String netWorkName) {
		this.netWorkName = netWorkName;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getNetSateName() {
		return netSateName;
	}

	public void setNetSateName(String netSateName) {
		this.netSateName = netSateName;
	}

	public void setSellStateName(String sellStateName) {
		this.sellStateName = sellStateName;
	}

	public void setCabinetTypeName(String cabinetTypeName) {
		this.cabinetTypeName = cabinetTypeName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
	public String getAllAddress() {
		return allAddress;
	}

	public void setAllAddress(String allAddress) {
		this.allAddress = allAddress;
	}
	
	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	
	//01:商店机 02:弹簧机 03:格子机 
	public String getCabinetTypeName() {
		if (cabinetType == null) {
			return "";
		}
		if (cabinetType.equals("01")) {
			return "商店机";
		} else if (cabinetType.equals("02")) {
			return "弹簧机";
		} else if (cabinetType.equals("03")) {
			return "格子机 ";
		}
		return cabinetTypeName;
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

	public String getSellStateName() {
		if (sellState == null) {
			return "";
		}
		if (sellState.equals("1")) {
			return "可售卖";
		} else if (sellState.equals("2")) {
			return "不可售卖";
		} 
		return sellStateName;
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
	
	public List<VendingCabinetVo> getCabinets() {
		return cabinets;
	}
	public void setCabinets(List<VendingCabinetVo> cabinets) {
		this.cabinets = cabinets;
	}

	public List<Cols> getLanes() {
		return lanes;
	}

	public void setLanes(List<Cols> lanes) {
		this.lanes = lanes;
	}

	public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public static void main(String[] args) {
		VendingVo vo = new VendingVo();
		vo.setAddress("1");
		vo.setCabinetType("2");
		List<VendingCabinetVo> cabinets = new  ArrayList<VendingCabinetVo>();
		VendingCabinetVo vendingCabinetVo = new VendingCabinetVo();
		vendingCabinetVo.setCabinetName("dafd");
		vendingCabinetVo.setCabinetType("1");
		cabinets.add(vendingCabinetVo);
		vo.setCabinets(cabinets);
		System.out.println(JSON.toJSONString(vo));
	}
}
