package com.manage.project.system.advert.vo;

/**
 * 优惠对象
 * @author xufeng
 *
 */
public class Fobject {
	/** 优惠对象 1:整机 2:单品 */
	private String favType = "";
	/** 优惠站点编号或商品的编号 */
	private String favObjId = "";
	/** 优惠方式 01:购买折扣 02:消费立减 */
	private String favWay = "";
	/** 根据优惠方式 购买折扣->折扣价(0.1~9.9)，消费立减->立减金额(可带一位小数点) */
	private Float discount;
	/** 参与优惠支付方式 可多选 微信类型代号,支付宝类型代号 */
	private String payType = "";
	/** 有效开始日期 (yyyy-MM-dd HH:mm:ss) */
	private String validSTime = "";
	/** 失效结束日期 (yyyy-MM-dd HH:mm:ss) */
	private String validETime = "";
	public String getFavType() {
		return favType;
	}
	public void setFavType(String favType) {
		this.favType = favType;
	}
	public String getFavObjId() {
		return favObjId;
	}
	public void setFavObjId(String favObjId) {
		this.favObjId = favObjId;
	}
	public String getFavWay() {
		return favWay;
	}
	public void setFavWay(String favWay) {
		this.favWay = favWay;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getValidSTime() {
		return validSTime;
	}
	public void setValidSTime(String validSTime) {
		this.validSTime = validSTime;
	}
	public String getValidETime() {
		return validETime;
	}
	public void setValidETime(String validETime) {
		this.validETime = validETime;
	}
}
