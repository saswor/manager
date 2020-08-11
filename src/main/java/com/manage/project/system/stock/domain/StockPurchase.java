package com.manage.project.system.stock.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.util.SystemUtil;

/**
 * 仓库采购记录表 as_stock_purchase
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class StockPurchase extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 仓采购记录编号 */
	@Excel(name="采购单号",column="A")
	private String wpurchaseId;

	/** 仓库编号 */
	@Excel(name="仓库编号",column="B")
	private String stockId;
	/** 仓库名称 */
	@Excel(name="仓库名称",column="C")
	private String stokcName;
	/** 商品编号 */
	private String productId;
	/** 商品名称 */
	private String productName;
	/** 总采购数量 */
	@Excel(name="总采购数量",column="E")
	private Integer pnum;
	/** 采购商品种类数 */
	@Excel(name="采购商品种类数",column="D")
	private Integer tnum;
	/** 总采购价 */
	@Excel(name="总采购价 ",column="F")
	private Float totalPrice;
	/** 采购状态 1:正常 2:删除 */
	@Excel(name="采购状态  ",column="H")
	private String curState;
	/** 采购状态变化时间 */
	private String stateTime;
	/** 审核状态 1:未审核 2:审核通过 3:审核失败 */
	private String checkState;
	/** 审核状态变化时间 */
	private String checkTime;
	/** 审核人编号 */
	private String checkId;
	/** 库存状态 1:未入库 2:已入库 */
	@Excel(name="库存状态  ",column="I")
	private String stockState;
	/** 库存状态变化时间 */
	private String stockSTime;
	/** 描述 */
	private String description;
	/** 托管公司编号 */
	private String corpId;
	
	private String supplyId;
	@Excel(name="申请人 ",column="G")
	private String supplyName;
	
	/** 审核人姓名 */
	private String checkName;
	
	//  审核状态文字  1:未审核 2:审核通过 3:审核失败
	private String checkStateName;
	/**采购状态*/
	private String curStateName;
	/**库存状态*/
	
	private String stockStateName;
	
	private List<StockPpurchase> stockPpurchases=new ArrayList<StockPpurchase>();

	public List<StockPpurchase> getStockPpurchases() {
		return stockPpurchases;
	}

	public void setStockPpurchases(List<StockPpurchase> stockPpurchases) {
		this.stockPpurchases = stockPpurchases;
	}

	public String getStockStateName() {
		if (stockState == null) {
			return "";
		}
		if (stockState.equals("1")) {
			return "未入库";
		}
		if (stockState.equals("2")) {
			return "已入库";
		}
		return "";
	}

	public String getCurStateName() {
		if (curState == null) {
			return "";
		}
		if (curState.equals("1")) {
			return "正常";
		}
		if (curState.equals("2")) {
			return "删除";
		}
		return "";
	}
	
	public String getCheckStateName() {
		if (checkState == null) {
			return "";
		}
		if (checkState.equals("1")) {
			return "未审核";
		}
		if (checkState.equals("2")) {
			return "审核通过";
		}
		if (checkState.equals("3")) {
			return "审核失败";
		}
		return "";
	}

	public void setCheckStateName(String checkStateName) {
		this.checkStateName = checkStateName;
	}

	public String getCheckName() {
		if (checkId == null || checkId.equals("")) {
			return "";
		}
		User user = SystemUtil.getUserById(Long.valueOf(checkId));
		if (user == null) {
			return "";
		}
		return user.getUserName();
	}

	public String getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setStockId(String stockId) 
	{
		this.stockId = stockId;
	}

	public String getStockId() 
	{
		return stockId;
	}
	public void setStokcName(String stokcName) 
	{
		this.stokcName = stokcName;
	}

	public String getStokcName() 
	{
		return stokcName;
	}
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setProductName(String productName) 
	{
		this.productName = productName;
	}

	public String getProductName() 
	{
		return productName;
	}
	
	public String getWpurchaseId() {
		return wpurchaseId;
	}

	public void setWpurchaseId(String wpurchaseId) {
		this.wpurchaseId = wpurchaseId;
	}

	public Integer getPnum() {
		return pnum;
	}

	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}

	public Integer getTnum() {
		return tnum;
	}

	public void setTnum(Integer tnum) {
		this.tnum = tnum;
	}

	public void setTotalPrice(Float totalPrice) 
	{
		this.totalPrice = totalPrice;
	}

	public Float getTotalPrice() 
	{
		return totalPrice;
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
	public void setCheckState(String checkState) 
	{
		this.checkState = checkState;
	}

	public String getCheckState() 
	{
		return checkState;
	}
	public void setCheckTime(String checkTime) 
	{
		this.checkTime = checkTime;
	}

	public String getCheckTime() 
	{
		return checkTime;
	}
	public void setCheckId(String checkId) 
	{
		this.checkId = checkId;
	}

	public String getCheckId() 
	{
		return checkId;
	}
	public void setStockState(String stockState) 
	{
		this.stockState = stockState;
	}

	public String getStockState() 
	{
		return stockState;
	}
	public void setStockSTime(String stockSTime) 
	{
		this.stockSTime = stockSTime;
	}

	public String getStockSTime() 
	{
		return stockSTime;
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

    public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public void setCurStateName(String curStateName) {
		this.curStateName = curStateName;
	}

	public void setStockStateName(String stockStateName) {
		this.stockStateName = stockStateName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("wPurchaseId", getWpurchaseId())
            .append("stockId", getStockId())
            .append("stokcName", getStokcName())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("pNum", getPnum())
            .append("tNum", getTnum())
            .append("totalPrice", getTotalPrice())
            .append("curState", getCurState())
            .append("stateTime", getStateTime())
            .append("checkState", getCheckState())
            .append("checkTime", getCheckTime())
            .append("checkId", getCheckId())
            .append("stockState", getStockState())
            .append("stockSTime", getStockSTime())
            .append("description", getDescription())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
