package com.manage.project.system.stock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 仓库商品库存存量表 as_stock_warehouse
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class StockWarehouse extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 事件编号 */
	private String wstockId;
	/** 仓库编号 */
	@Excel(name="仓库编号")
	private String stockId;
	/** 仓库名称 */
	@Excel(name="仓库名称")
	private String stokcName;
	/** 商品编号 */
	@Excel(name="商品编号",column="A")
	private String productId;
	/** 商品名称 */
	@Excel(name="商品名称",column="B")
	private String productName;
	/** 总库存数量 */
	@Excel(name="历史库存总数量",column="C")
	private Integer totalNum;
	/** 当前库存总数量 */
	@Excel(name="当前库存总数量",column="D")
	private Integer curNum;
	@Excel(name="过期总数量",column="E")
	private Integer overNum;
	/** 警戒值 */
	@Excel(name="警戒值",column="F")
	private Integer warnNum;
	/** 托管公司编号 */
	private String corpId;
	
	private String managerName;	// 联系人
	
	private Integer pNum;	// 采购数量
	private Float buyPrice;
	private Float price;	// 采购单价
	private Float totalPrice;	// 采购总价
	
	private int isDel = 0;	// 删除标识, 0未删除，1删除

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getpNum() {
		return pNum;
	}

	public void setpNum(Integer pNum) {
		this.pNum = pNum;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setWstockId(String wstockId) 
	{
		this.wstockId = wstockId;
	}

	public String getWstockId() 
	{
		return wstockId;
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
	public void setTotalNum(Integer totalNum) 
	{
		this.totalNum = totalNum;
	}

	public Integer getTotalNum() 
	{
		return totalNum;
	}
	public void setCurNum(Integer curNum) 
	{
		this.curNum = curNum;
	}

	public Integer getCurNum() 
	{
		return curNum;
	}
	public void setOverNum(Integer overNum) 
	{
		this.overNum = overNum;
	}

	public Integer getOverNum() 
	{
		return overNum;
	}
	public void setWarnNum(Integer warnNum) 
	{
		this.warnNum = warnNum;
	}

	public Integer getWarnNum() 
	{
		return warnNum;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

    public Float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Float buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("wstockId", getWstockId())
            .append("stockId", getStockId())
            .append("stokcName", getStokcName())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("totalNum", getTotalNum())
            .append("curNum", getCurNum())
            .append("overNum", getOverNum())
            .append("warnNum", getWarnNum())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
