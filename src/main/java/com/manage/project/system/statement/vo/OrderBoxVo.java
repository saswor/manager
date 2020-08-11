package com.manage.project.system.statement.vo;

public class OrderBoxVo {

	private String productId;
	private String productName;
	private int laneSId;
	private int laneEId;
	private String outState;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getLaneSId() {
		return laneSId;
	}
	public void setLaneSId(int laneSId) {
		this.laneSId = laneSId;
	}
	public int getLaneEId() {
		return laneEId;
	}
	public void setLaneEId(int laneEId) {
		this.laneEId = laneEId;
	}
	public String getOutState() {
		return outState;
	}
	public void setOutState(String outState) {
		this.outState = outState;
	}
	
}
