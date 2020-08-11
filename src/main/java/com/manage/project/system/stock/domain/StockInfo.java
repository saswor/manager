package com.manage.project.system.stock.domain;

import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.util.SystemUtil;

/**
 * 仓库信息表 as_stock_info
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class StockInfo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 仓库编号 */
	private String stockId;
	/** 仓库名称 */
	private String stockName;
	/** 管理人 */
	private String managerName;
	/** 仓库名称 */
	private String mobile;
	/** 地区 */
	private String districtId;
	/** 托管公司编号 */
	private String corpId;
	
	private String districtName;
	
	private String corpName;
	
	public String getCorpName() {
		Corp c = SystemUtil.getCorpById(corpId);
		if (c == null) {
			return "";
		}
		return c.getCorpName();
	}
	
	public String getDistrictName() {
		return SystemUtil.getVendingDistrictNameFromCache(districtId);
	}
	
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
}
