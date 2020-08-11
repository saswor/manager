package com.manage.project.system.advert.vo;

import java.util.List;

/**
 * 折扣查看页面vo
 * @author xufeng
 *
 */
public class FavourableViewVo {
	/** 优惠名称 */
	private String name;
	/** 参与优惠支付方式 可多选 微信类型代号,支付宝类型代号 */
	private String payTypeName;
	/** 优惠类型 1:统一优惠 2:分时段优惠 */
	private String favTypeName;
	/** 有效开始日期 (yyyy-MM-dd) */
	private String validSTime;
	/** 失效结束日期 (yyyy-MM-dd) */
	private String validETime;
	
	private List<Ftime> ftimes;
	
	public List<Ftime> getFtimes() {
		return ftimes;
	}

	public void setFtimes(List<Ftime> ftimes) {
		this.ftimes = ftimes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getFavTypeName() {
		return favTypeName;
	}

	public void setFavTypeName(String favTypeName) {
		this.favTypeName = favTypeName;
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

	public class Ftime {
		/** 开始时间(HH:mm:ss) */
		private String favSTime;
		/** 结束日期(HH:mm:ss) */
		private String favETime;
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
		public Float getDiscount() {
			return discount;
		}
		public void setDiscount(Float discount) {
			this.discount = discount;
		}
	}
}
