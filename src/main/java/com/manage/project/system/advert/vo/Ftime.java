package com.manage.project.system.advert.vo;

/**
 * 优惠时间段 FavourableSaveVo的组成部分
 * @author xufeng
 *
 */
public class Ftime {
	/** 开始时间(HH:mm:ss) */
	private String favSTime = "";
	/** 结束日期(HH:mm:ss) */
	private String favETime = "";
	/** 优惠方式 01:购买折扣 02:消费立减 */
	private String favWay = "";
	/** 时间段优惠金额 根据优惠方式 存储 购买折扣->折扣价(0.1~9.9)，消费立减->立减金额(可带一位小数点) */
	private Float discount;
	
	public String getFavSTime() {
		return favSTime;
	}
	public void setFavSTime(String favSTime) {
		this.favSTime = favSTime;
	}
	public String getFavETime() {
		return favETime;
	}
	public void setFavETime(String favETime) {
		this.favETime = favETime;
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
}
