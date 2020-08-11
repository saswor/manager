package com.manage.project.system.supply.vo;

public class SupplyOutListVo {

	private String sOrderId;
    private String lineId;
    private String wmId;
    private String wmName;
    private String stockState;
    private Integer outNum;
    private float totalPrice;
    private String supplierName;
    private String createTime;
    private String supplydType;
    
	private Integer supplyrNum;
	
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
	public String getsOrderId() {
		return sOrderId;
	}
	public void setsOrderId(String sOrderId) {
		this.sOrderId = sOrderId;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getWmId() {
		return wmId;
	}
	public void setWmId(String wmId) {
		this.wmId = wmId;
	}
	public String getWmName() {
		return wmName;
	}
	public void setWmName(String wmName) {
		this.wmName = wmName;
	}
	public String getStockState() {
		return stockState;
	}
	public void setStockState(String stockState) {
		this.stockState = stockState;
	}
	public Integer getOutNum() {
		return outNum;
	}
	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSupplydType() {
		return supplydType;
	}
	public void setSupplydType(String supplydType) {
		this.supplydType = supplydType;
	}
    
}
