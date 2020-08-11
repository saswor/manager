package com.manage.project.system.statement.vo;

public class IOrderBoxVo {
	/**
	 * 记录编号
	 */
	private String logid;
	/**
	 * 商品货道编号
	 */
	private String proboxId;
	/**
	 * 公司编号
	 */
	private String corpId;
	/**
	 * 订单商品编号
	 */
	private String prodetailId;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 终端订单编号
	 */
	private String torderId;
	/**
	 * 售货机编号
	 */
	private String siteId;
	/**
	 * 售货机名称
	 */
	private String siteName;
	/**
	 * 货道开始号
	 */
	private Integer laneSId;
	/**
	 * 货道结束号
	 */
	private Integer laneEId;
	/**
	 * 商品编号
	 */
	private String productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品采购价
	 */
	private Float buyPrice;
	/**
	 * 商品标准售价
	 */
	private Float salePrice;
	/**
	 * 商品支付价格
	 */
	private Float payPrice;
	/**
	 * 优惠金额
	 */
	private Float favPrice;
	/**
	 * 退款金额
	 */
	private Float returnPrice;
	/**
	 * 毛利
	 */
	private Float profitMoney;
	/**
	 * 补货单号
	 */
	private String supplyId;
	/**
	 * 出柜编号
	 */
	private String outIndex;
	/**
	 * 出柜状态 1:未出柜(代表商品还在柜子中) 2:正常已出柜(代表商品已存放在出货口) 3:异常已出柜(货道出货失败可以另外货道代替出货) 4:出柜失败
	 */
	private String outState;
	/**
	 * 出柜状态 1:未出柜(代表商品还在柜子中) 2:正常已出柜(代表商品已存放在出货口) 3:异常已出柜(货道出货失败可以另外货道代替出货) 4:出柜失败
	 */
	private String outStateName;
	/**
	 * 状态时间
	 */
	private String stateTime;
	/**
	 * 创建时间
	 */
	private String create_time;
	
	//虚拟字段
	/**
	 * 开始时间	
	 */
	private String btime;	
    /**
     * 结束时间		
     */
	/**
	 * 货道差异编号
	 */
	private String lsdifferId;
	private String etime;	 
	
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getProboxId() {
		return proboxId;
	}
	public void setProboxId(String proboxId) {
		this.proboxId = proboxId;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getProdetailId() {
		return prodetailId;
	}
	public void setProdetailId(String prodetailId) {
		this.prodetailId = prodetailId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTorderId() {
		return torderId;
	}
	public void setTorderId(String torderId) {
		this.torderId = torderId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public Integer getLaneSId() {
		return laneSId;
	}
	public void setLaneSId(Integer laneSId) {
		this.laneSId = laneSId;
	}
	public Integer getLaneEId() {
		return laneEId;
	}
	public void setLaneEId(Integer laneEId) {
		this.laneEId = laneEId;
	}
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
	public Float getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Float buyPrice) {
		this.buyPrice = buyPrice;
	}
	public Float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}
	public Float getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Float payPrice) {
		this.payPrice = payPrice;
	}
	public Float getFavPrice() {
		return favPrice;
	}
	public void setFavPrice(Float favPrice) {
		this.favPrice = favPrice;
	}
	public Float getReturnPrice() {
		return returnPrice;
	}
	public void setReturnPrice(Float returnPrice) {
		this.returnPrice = returnPrice;
	}
	public Float getProfitMoney() {
		return profitMoney;
	}
	public void setProfitMoney(Float profitMoney) {
		this.profitMoney = profitMoney;
	}
	public String getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}
	public String getOutIndex() {
		return outIndex;
	}
	public void setOutIndex(String outIndex) {
		this.outIndex = outIndex;
	}
	public String getOutState() {
		return outState;
	}
	public void setOutState(String outState) {
		this.outState = outState;
	}
	public String getStateTime() {
		return stateTime;
	}
	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getLsdifferId() {
		return lsdifferId;
	}
	public void setLsdifferId(String lsdifferId) {
		this.lsdifferId = lsdifferId;
	}
	public String getOutStateName() {
		return outStateName;
	}
	public void setOutStateName(String outStateName) {
		this.outStateName = outStateName;
	}

}
