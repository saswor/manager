/**
 * 
 */
package com.manage.project.system.statement.vo;

import com.manage.common.utils.StringUtils;
import com.manage.project.system.statement.domain.OrderApply;

/**
 * 订单信息vo
 * 
 * @author zhangjiawei
 * @date 2018年10月18日
 * 
 */
public class OrderVo extends OrderApply{

	private static final long serialVersionUID = 1L;

	/** 点位名称 */
	private String pointName;
	/** 当前状态 01:申请 02:支付成功03:支付失败 04:提前取货 05:客户已取货 
	 *  05:客户取消 06:出货故障 07:已回收08:退款 09:完结 */
	private String operAction;
	/** 货道开始号 */
	private int laneSId;
	/** 货道结束号 */
	private int laneEId;
	/** 商品编号 */
	private String productId;
	/** 商品名称 */
	private String productName;
	/** 创建日期 */
	private String createDate;
	/** 支付方式名称 */
	private String payTypeName;
	/** 支付状态名称 */
	private String payStateName;
	/** 当前状态名称*/
	private String curStateName;
	/** 异常状态名称 */
	private String abnomarlStateName;
	/**退款状态名称 */
	private String returnTypeName;
	/**商品标准售价*/
	private float productSalePrice;
	/**商品标准售价*/
	private float productPayPrice;
	/**商品标准售价*/
	private float productProfitMoney;
	/** 当前状态名称 */
	private String operActionName;
	/**开始日期*/
	private String startDate;
	/**结束日期*/
	private String endDate;
	/**支付流水号*/
	private String pTradeNo;
	
	public String getpTradeNo() {
		return pTradeNo;
	}
	public void setpTradeNo(String pTradeNo) {
		this.pTradeNo = pTradeNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	private int saleNum;
	private String proboxId;
	
	private String orderType;
	
	
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public float getProductSalePrice() {
		return productSalePrice;
	}
	public void setProductSalePrice(float productSalePrice) {
		this.productSalePrice = productSalePrice;
	}
	public float getProductPayPrice() {
		return productPayPrice;
	}
	public void setProductPayPrice(float productPayPrice) {
		this.productPayPrice = productPayPrice;
	}
	public float getProductProfitMoney() {
		return productProfitMoney;
	}
	public void setProductProfitMoney(float productProfitMoney) {
		this.productProfitMoney = productProfitMoney;
	}
	public String getOperActionName() {
		return operActionName;
	}
	public void setOperActionName(String operActionName) {
		this.operActionName = operActionName;
	}
	public String getPayTypeName() {
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
	public String getPayStateName() {
		return payStateName;
	}
	public void setPayStateName(String payStateName) {
		this.payStateName = payStateName;
	}
	public String getCurStateName() {
		return curStateName;
	}
	public void setCurStateName(String curStateName) {
		this.curStateName = curStateName;
	}
	public String getAbnomarlStateName() {
		if(StringUtils.isEmpty(abnomarlStateName)) {
			if ("0".equals(getAbnomarlState())) {
				return "无";
			} else if("1".equals(getAbnomarlState())){
				return "取货故障";
			} else if("2".equals(getAbnomarlState())){
				return "客户取消";
			} else if("3".equals(getAbnomarlState())){
				return "未取取消";
			} else {
				return "";
			}
		}else {
			return abnomarlStateName;
		}
		
	}
	public void setAbnomarlStateName(String abnomarlStateName) {
		this.abnomarlStateName = abnomarlStateName;
	}
	public String getReturnTypeName() {
		return returnTypeName;
	}
	public void setReturnTypeName(String returnTypeName) {
		this.returnTypeName = returnTypeName;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getOperAction() {
		return operAction;
	}
	public void setOperAction(String operAction) {
		this.operAction = operAction;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}
	public String getProboxId() {
		return proboxId;
	}
	public void setProboxId(String proboxId) {
		this.proboxId = proboxId;
	}
	
	
}
