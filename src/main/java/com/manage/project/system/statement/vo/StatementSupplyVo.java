/**
 * 
 */
package com.manage.project.system.statement.vo;

import com.manage.project.system.statement.domain.StatementSupply;

/**
 * 补货对账vo
 * 
 * @author zhangjiawei
 * @date 2018年10月16日
 * 
 */
public class StatementSupplyVo extends StatementSupply{

	private static final long serialVersionUID = 1L;
	/**区域名称*/
	private String districtName;
	/**线路名称*/
	private String lineName;
	/**点位编号*/
	private String pointId;
	/**点位名称*/
	private String pointName;
	/**站点名称*/
	private String siteName;
	/**仓库名称*/
	private String wmName;
	/** 销售状态名称 */
	private String salteStateName;
	/** 对账状态名称 */
	private String statementStateName;
	/** 结算状态名称 */
	private String curStateName;

	public String getSalteStateName() {
		return salteStateName;
	}

	public void setSalteStateName(String salteStateName) {
		this.salteStateName = salteStateName;
	}

	public String getStatementStateName() {
		return statementStateName;
	}

	public void setStatementStateName(String statementStateName) {
		this.statementStateName = statementStateName;
	}

	public String getCurStateName() {
		return curStateName;
	}

	public void setCurStateName(String curStateName) {
		this.curStateName = curStateName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
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

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getWmName() {
		return wmName;
	}

	public void setWmName(String wmName) {
		this.wmName = wmName;
	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	
}
