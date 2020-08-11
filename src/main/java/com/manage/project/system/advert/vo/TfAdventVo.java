package com.manage.project.system.advert.vo;

/**
 * 投放信息vo,用于查询广告投放信息时的展示
 * @author xufeng
 *
 */
public class TfAdventVo {
	/** 网络状态(0:在线 1:离线) */
	private String netSateName;
	/** 售货机名称 */
	private String siteName;
	/** 售货机编号 */
	private String siteId;
	/** 线路名称 */
	private String lineName;
	/** 售卖状态名称 */
	private String sellStateName;
	public String getNetSateName() {
		return netSateName;
	}
	public void setNetSateName(String netSateName) {
		this.netSateName = netSateName;
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
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getSellStateName() {
		return sellStateName;
	}
	public void setSellStateName(String sellStateName) {
		this.sellStateName = sellStateName;
	}
}