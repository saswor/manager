package com.manage.project.system.vending.vo;

import java.util.List;

import com.manage.common.utils.StringUtils;
import com.manage.project.system.util.SystemUtil;

/**
 * 货柜vo
 * @author xufeng
 *
 */
public class VendingCabinetVo {
	private List<Cols> lanes;	// 货柜货道
	
//	private List<VendingLaneVo> laneList;	// 
	
//	public static void main(String[] args) {
//		List<Cols> lanes = new ArrayList<Cols>();
//		
//		List<VendingLaneVo> cols = new ArrayList<VendingLaneVo>();
//		VendingLaneVo vo = new VendingLaneVo();
//		vo.setArrange(1);
//		cols.add(vo);
//		
//		Cols a = new Cols();
//		a.setCols(cols);
//		lanes.add(a);
//		System.out.println(JSON.toJSONString(lanes));
//	}
	
	/** 记录编号 */
	private String logid;
	/** 售货机编号 */
	private String siteId;
	/** 售货机名称 */
	private String siteName;
	/** 货柜排列(从左到右) 1:开始递增 */
	private Integer seqId;
	/** 机型编码 */
	private String deviceId;
	/** 工控机编号 */
	private String vmcId;
	/** 外挂类型 1:是 2:否 */
	private String hangType;
	/** 串口号 */
	private String pointCode;
	/** 厂家编号 */
	private String factoryId;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetType;
	/** 描述 */
	private String description;
	/** 托管公司编号 */
	private String corpId;
	/** 上报时间 */
	private String reportTime;
	// 货柜名称
	private String cabinetName;
	
	/** 外挂类型名称 1:是 2:否 */
	private String hangTypeName;
	/** 机型 */
	private String deviceName;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetTypeName;
	/** 厂家 */
	private String factoryName;
	/** 货柜id */
	private String cabinetId;
	/**
	 * 柜子名称,页面展示tab名称,页面显示需要
	 */
	private String viewName;
	// 前端使用，控制选择模板按钮，货柜有商品返回false
	private boolean isShowTemplate = true;
	
	/** 机型 */
	private String device;
	// // 修改售卖机时，前端要求字段，控制是否可编辑厂商
	private boolean dis = false;
	
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

	public boolean getIsShowTemplate() {
		return isShowTemplate;
	}

	public void setIsShowTemplate(boolean isShowTemplate) {
		this.isShowTemplate = isShowTemplate;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public String getCabinetName() {
		if(StringUtils.isEmpty(cabinetName)) {
			return "主柜";
		}else {
			return cabinetName;
		}	
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
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
	public void setSiteName(String siteName) 
	{
		this.siteName = siteName;
	}

	public String getSiteName() 
	{
		return siteName;
	}
	public void setSeqId(Integer seqId) 
	{
		this.seqId = seqId;
	}

	public Integer getSeqId() 
	{
		return seqId;
	}
	public void setDeviceId(String deviceId) 
	{
		this.deviceId = deviceId;
	}

	public String getDeviceId() 
	{
		return deviceId;
	}
	public void setVmcId(String vmcId) 
	{
		this.vmcId = vmcId;
	}

	public String getVmcId() 
	{
		return vmcId;
	}
	public void setHangType(String hangType) 
	{
		this.hangType = hangType;
	}

	public String getHangType() 
	{
		return hangType;
	}
	public void setPointCode(String pointCode) 
	{
		this.pointCode = pointCode;
	}

	public String getPointCode() 
	{
		return pointCode;
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
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setReportTime(String reportTime) 
	{
		this.reportTime = reportTime;
	}

	public String getReportTime() 
	{
		return reportTime;
	}

	public List<Cols> getLanes() {
		return lanes;
	}

	public void setLanes(List<Cols> lanes) {
		this.lanes = lanes;
	}

	public String getHangTypeName() {
		return SystemUtil.parseHangType(hangType);
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getCabinetTypeName() {
		return SystemUtil.parseCabinetType(cabinetType);
	}

	public String getFactoryName() {
		return factoryName;
	}
	
}
