package com.manage.project.system.index.vo.reqparam;

import java.util.Date;

/**
 * 运营分析请求参数
 * @author xufeng
 *
 */
public class AnalysisSelectVo {
	private String corpId;	// 托管公司编号
	private Date bdate;	// 开始月份
	private Date edate;	// 结束月份
	private int type;	// 0查询，1近半年,2近1年
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	
	public Date getBdate() {
		return bdate;
	}
	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
