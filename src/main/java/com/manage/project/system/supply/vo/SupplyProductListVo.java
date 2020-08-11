package com.manage.project.system.supply.vo;

public class SupplyProductListVo {

	private String productId;
	/**出库数量*/
	private Integer outNum;
	/**追加出库数量*/
	private Integer pNum;
	private Float buyPrice;
	private Integer supplyrNum;
	private Integer supplynNum;
	private Integer supplydNum;
	private Integer supplylNum;
	
	public Integer getpNum() {
		return pNum;
	}
	public void setpNum(Integer pNum) {
		this.pNum = pNum;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getOutNum() {
		return outNum;
	}
	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}
	public Float getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Float buyPrice) {
		this.buyPrice = buyPrice;
	}
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
	
}
