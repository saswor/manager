package com.manage.project.system.stock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 仓库采购记录表 as_stock_ppurchase
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class StockPpurchase extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 仓采商品购记录编号 */
	private String wppurchaseId;
	/** 采购单编号 */
	private String wpurchaseId;
	/** 仓库编号 */
	private String stockId;
	/** 仓库名称 */
	private String stokcName;
	/** 商品编号 */
	private String productId;
	/** 商品名称 */
	private String productName;
	/** 供应商编号 */
	private String supplierId;
	/** 采购数量 */
	private Integer pnum;
	/** 采购单价 */
	private Float buyPrice;
	/** 采购总价 */
	private Float totalPrice;
	/** 托管公司编号 */
	private String corpId;
	
	/** 当前库存总数量 */
	private Integer curNum = 0;
	
	private int isDel = 0;	// 删除标识, 0未删除，1删除

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public Integer getCurNum() {
		return curNum;
	}

	public void setCurNum(Integer curNum) {
		this.curNum = curNum;
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
	public void setSupplierId(String supplierId) 
	{
		this.supplierId = supplierId;
	}

	public String getSupplierId() 
	{
		return supplierId;
	}
	
	public String getWppurchaseId() {
		return wppurchaseId;
	}

	public void setWppurchaseId(String wppurchaseId) {
		this.wppurchaseId = wppurchaseId;
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

	public void setBuyPrice(Float buyPrice) 
	{
		this.buyPrice = buyPrice;
	}

	public Float getBuyPrice() 
	{
		return buyPrice;
	}
	public void setTotalPrice(Float totalPrice) 
	{
		this.totalPrice = totalPrice;
	}

	public Float getTotalPrice() 
	{
		return totalPrice;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("stockId", getStockId())
            .append("stokcName", getStokcName())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("supplierId", getSupplierId())
            .append("buyPrice", getBuyPrice())
            .append("totalPrice", getTotalPrice())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
