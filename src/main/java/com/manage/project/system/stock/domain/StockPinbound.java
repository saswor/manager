package com.manage.project.system.stock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 仓库采购记录表 as_stock_pinbound
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class StockPinbound extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 仓库入库商品记录编号 */
	private String wpInboundId;
	/** 仓库入库记录编号 */
	private String winboundId;
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
	/** 入库数量 */
	private Integer pnum;
	/** 入库采购单价 */
	private Float buyPrice;
	/** 入库采购总价 */
	private Float totalPrice;
	/** 托管公司编号 */
	private String corpId;
	// 采购数量
	private Integer buyNum;
	// 未入库数量
	private Integer nopNum;
	/** 入库类型 1：正常入库 2：:冲正 */
	private String inboundType;
	private Integer reInboundNum;
	
	public Integer getNopNum() {
		if (buyNum == null || pnum == null) {
			return 0;
		}
		return buyNum - pnum;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}

	public String getWpInboundId() {
		return wpInboundId;
	}

	public void setWpInboundId(String wpInboundId) {
		this.wpInboundId = wpInboundId;
	}

	public String getWinboundId() {
		return winboundId;
	}

	public void setWinboundId(String winboundId) {
		this.winboundId = winboundId;
	}

	public Integer getPnum() {
		return pnum;
	}

	public void setPnum(Integer pnum) {
		this.pnum = pnum;
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

    public String getInboundType() {
		return inboundType;
	}

	public void setInboundType(String inboundType) {
		this.inboundType = inboundType;
	}

	public Integer getReInboundNum() {
		return reInboundNum;
	}

	public void setReInboundNum(Integer reInboundNum) {
		this.reInboundNum = reInboundNum;
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
