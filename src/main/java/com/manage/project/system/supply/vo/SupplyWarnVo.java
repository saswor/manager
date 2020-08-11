/**
 * 
 */
package com.manage.project.system.supply.vo;

/**
 * 警告记录信息vo
 * 
 * @author zhangjiawei
 * @date 2018年11月6日
 * 
 */
public class SupplyWarnVo{

	/**区域编号*/
	private String districtId;
	/**区域名称*/
	private String districtName;
	/**线路编号*/
	private String lineId;
	/**线路名称*/
	private String lineName;
	/**点位编号*/
	private String pointId;
	/**点位名称*/
	private String pointName;
	/**售货机编号*/
	private String siteId;
	/**售货机名称*/
	private String siteName;
	/**所属行政区*/
	private String dispatchName;
	/**仓库编号*/
	private String wmId;
	/**仓库名称*/
	private String wmName;
	/**待补货数量*/
	private Integer waitSPNum;
	/**补货员名称*/
	private String supplierName;
	/**补货员编号*/
	private String supplierId;
	/**下次补货时间**/
	private String lastSTime;
	/**缺货紧急度名称*/
	private String warnLevelName;
	
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
	public String getWmId() {
		return wmId;
	}
	public void setWmId(String wmId) {
		this.wmId = wmId;
	}
	public String getWmName() {
		return wmName;
	}
	public void setWmName(String wmName) {
		this.wmName = wmName;
	}
	public Integer getWaitSPNum() {
		return waitSPNum;
	}
	public void setWaitSPNum(Integer waitSPNum) {
		this.waitSPNum = waitSPNum;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getLastSTime() {
		return lastSTime;
	}
	public void setLastSTime(String lastSTime) {
		this.lastSTime = lastSTime;
	}
	public String getStoryLevelName() {
		return warnLevelName;
	}
	public void setWarnLevelName(String warnLevelName) {
		this.warnLevelName = warnLevelName;
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
	public String getDispatchName() {
		return dispatchName;
	}
	public void setDispatchName(String dispatchName) {
		this.dispatchName = dispatchName;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getWarnLevelName() {
		return warnLevelName;
	}
	

}
