package com.manage.project.system.vending.vo;

import java.util.List;

import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vendingPoint.domain.VendingPoint;

/**
 * 关联售卖机，查询结果vo
 * @author xufeng
 *
 */
public class RelationSelectResultVo {
	/** 售货机编号 */
	private String siteId;
	/** 售货机编码 */
	private String siteCode;
	/** 售货机名称 */
	private String siteName;
	/** 线路编号 */
	private String lineId;
	/** 托管公司编号 */
	private String corpId;
	/** 点位编号 */
	private String pointId;
	/**
	 * 线路名称
	 */
	private String lineName;
	/** 区域编号 */
	private String districtId;
	/** 区域名称 */
	private String districtName;
	
	/** 详细地址 */
	private String address;
	
	/** 创建时间 */
    private String createTime;
    
    /**
     * 货柜列表
     */
    private List<RelationSelectCabinetVo> cabinets;

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
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

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getAddress() {
		VendingPoint vendingPoint = SystemUtil.getVendingPointFromCache(pointId);
		if (vendingPoint == null) {
			return "";
		}
		return vendingPoint.getAdderss();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<RelationSelectCabinetVo> getCabinets() {
		return cabinets;
	}

	public void setCabinets(List<RelationSelectCabinetVo> cabinets) {
		this.cabinets = cabinets;
	}

	public String getLineName() {
		return SystemUtil.getVendingLineNameFromCache(lineId);
	}

	public String getDistrictName() {
		return SystemUtil.getVendingDistrictNameFromCache(districtId);
	}
}
