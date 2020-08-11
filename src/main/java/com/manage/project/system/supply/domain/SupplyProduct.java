package com.manage.project.system.supply.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 补货商品记录表 as_supply_product
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class SupplyProduct extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 补货商品编号 */
	private String sProductId;
	/** 补货单编号 */
	@Excel(name="补货编号",column="A")
	private String sOrderId;
	
	@Excel(name="线路名称",column="B")
	private String lineName;
	@Excel(name="商品名称",column="C")
	private String productName;
	
	/** 采购单价 */
	@Excel(name="采购价格",column="D")
	private Float buyPrice;
	
	/** 公司编号 */
	private String corpId;
	/** 补货配置编号 */
	private String supplyId;
	/** 商品编号 */
	private String productId;
	/** 需补货数量 */
	private Integer supplyNum;
	/** 实际出库数量(拿到手的数量) */
	private Integer outNum;
	/**仓库出库数量*/
	@Excel(name="出库数量",column="E")
	private Integer stockOutNum;
	/** 补货状态 1:等待补货 2:补货完成 */
	private String curState;
	@Excel(name="实际补货数量",column="F")
	private Integer supplyrNum;
	/** 补货状态变化时间 */
	@Excel(name="补货时间",column="G")
	private String stateTime;
	
	
	
	private String lineId;
	
	private Integer supplynNum;
	
	private Integer supplydNum;
	
	private Integer supplylNum;

	public Integer getSupplyrNum() {
		return supplyrNum;
	}

	public void setSupplyrNum(Integer supplyrNum) {
		this.supplyrNum = supplyrNum;
	}

	public Integer getSupplynNum() {
		return supplynNum;
	}

	public void setSupplynNum(Integer supplynNum) {
		this.supplynNum = supplynNum;
	}

	public Integer getSupplydNum() {
		return supplydNum;
	}

	public void setSupplydNum(Integer supplydNum) {
		this.supplydNum = supplydNum;
	}

	public Integer getSupplylNum() {
		return supplylNum;
	}

	public void setSupplylNum(Integer supplylNum) {
		this.supplylNum = supplylNum;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return SystemUtil.getVendingLineNameFromCache(lineId);
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getProductName() {
		return SystemUtil.getProductNameById(productId);
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSProductId(String sProductId) 
	{
		this.sProductId = sProductId;
	}

	public String getSProductId() 
	{
		return sProductId;
	}
	public void setSOrderId(String sOrderId) 
	{
		this.sOrderId = sOrderId;
	}

	public String getSOrderId() 
	{
		return sOrderId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setSupplyId(String supplyId) 
	{
		this.supplyId = supplyId;
	}

	public String getSupplyId() 
	{
		return supplyId;
	}
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setSupplyNum(Integer supplyNum) 
	{
		this.supplyNum = supplyNum;
	}

	public Integer getSupplyNum() 
	{
		return supplyNum;
	}
	public void setOutNum(Integer outNum) 
	{
		this.outNum = outNum;
	}

	public Integer getOutNum() 
	{
		return outNum;
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
	public void setBuyPrice(Float buyPrice) 
	{
		this.buyPrice = buyPrice;
	}

	public Float getBuyPrice() 
	{
		return buyPrice;
	}


    public String getsProductId() {
		return sProductId;
	}

	public void setsProductId(String sProductId) {
		this.sProductId = sProductId;
	}

	public String getsOrderId() {
		return sOrderId;
	}

	public void setsOrderId(String sOrderId) {
		this.sOrderId = sOrderId;
	}

	public Integer getStockOutNum() {
		return stockOutNum;
	}

	public void setStockOutNum(Integer stockOutNum) {
		this.stockOutNum = stockOutNum;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("sProductId", getSProductId())
            .append("sOrderId", getSOrderId())
            .append("corpId", getCorpId())
            .append("supplyId", getSupplyId())
            .append("productId", getProductId())
            .append("supplyNum", getSupplyNum())
            .append("outNum", getOutNum())
            .append("curState", getCurState())
            .append("stateTime", getStateTime())
            .append("buyPrice", getBuyPrice())
            .append("createTime", getCreateTime())
            .toString();
    }
}
