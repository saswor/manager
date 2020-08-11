package com.manage.project.system.vending.vo;

/**
 * 售货机货道商品vo(下架)
 * 
 * @author zhangjiawei
 * @date 2018-11-19
 */
public class UnderProductVo {
	
	/**记录编号*/
	private String logid;
	/**售货机编号*/
	private String siteId;
	/**售货机名称*/
	private String siteName;
	/**线路编号*/
	private String lineId;
	/**线路名称*/
	private String lineName;
	/**区域编号*/
	private String districtId;
	/**区域名称*/
	private String districtName;
	/**点位编号*/
	private String pointId;
	/**所属行政区*/
	private String dispatch;
	/**创建时间*/
	private String createTime;
	/**商品编号*/
	private String productId;
	/**商品名称*/
	private String productName;
	/**商品全名*/
	private String fullName;
	/**当前容量*/
	private String curCap;
	/**货道开始号*/
	private String laneSId;
	/**货道结束号*/
	private String laneEId;
	/**托管公司编号*/
	private String corpId;
	
	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCurCap() {
		return curCap;
	}

	public void setCurCap(String curCap) {
		this.curCap = curCap;
	}

	public String getLaneSId() {
		return laneSId;
	}

	public void setLaneSId(String laneSId) {
		this.laneSId = laneSId;
	}

	public String getLaneEId() {
		return laneEId;
	}

	public void setLaneEId(String laneEId) {
		this.laneEId = laneEId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public String getDispatch() {
		return dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
