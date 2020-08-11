package com.manage.project.system.advert.vo;

import java.util.List;

/**
 * 用于新增优惠的vo
 * @author xufeng
 *
 */
public class FavourableSaveVo {
	
	/** 优惠编号 */
	private String favourableId;
	/** 优惠名称 */
	private String name = "";
	/** 参与优惠支付方式 可多选 微信类型代号,支付宝类型代号 */
	private String payType = "";
	/** 优惠类型 1:统一优惠 2:分时段优惠 */
	private String favType = "";
	/** 有效开始日期 (yyyy-MM-dd) */
	private String validSTime = "";
	/** 失效结束日期 (yyyy-MM-dd) */
	private String validETime = "";
	/** 状态 1:等待执行 2:正在执行 3:执行完成 4:已删除 */
	private String curState = "";
	/** 优惠方式 01:购买折扣 02:消费立减 */
	private String favWay = "";
	/** 优惠对象 1:整机 2:单品 */
	private String favTarget = "";
	/** 优惠时间段 */
	private List<Ftime> ftimes;
	// 优惠商品或售卖机
	private List<Fobject> fobjects;
	
	public String getFavourableId() {
		return favourableId;
	}

	public void setFavourableId(String favourableId) {
		this.favourableId = favourableId;
	}

	public String getFavWay() {
		return favWay;
	}

	public void setFavWay(String favWay) {
		this.favWay = favWay;
	}

	public String getFavTarget() {
		return favTarget;
	}

	public void setFavTarget(String favTarget) {
		this.favTarget = favTarget;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getFavType() {
		return favType;
	}

	public void setFavType(String favType) {
		this.favType = favType;
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

	public String getCurState() {
		return curState;
	}

	public void setCurState(String curState) {
		this.curState = curState;
	}

	public List<Ftime> getFtimes() {
		return ftimes;
	}

	public void setFtimes(List<Ftime> ftimes) {
		this.ftimes = ftimes;
	}

	public List<Fobject> getFobjects() {
		return fobjects;
	}

	public void setFobjects(List<Fobject> fobjects) {
		this.fobjects = fobjects;
	}
}
