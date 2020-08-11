package com.manage.project.system.advert.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 优惠对象列，在下单的时候可通过此方便查询优惠对象的优惠，需根据《优惠 3.2.4.1》和《优惠时间段3.2.4.2》定时更新此，此保存对象的最新优惠。根据优惠时间段更新此最新优惠表 as_favourable_object
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class FavourableObject extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 优惠对象编号 */
	private String favObjectId;
	/** 托管公司编号 */
	private String corpId;
	/** 优惠对象 1:整机 2:单品 */
	private String favType;
	/** 优惠站点编号或商品的编号 */
	private String favObjId;
	/** 优惠方式 01:购买折扣 02:消费立减 */
	private String favWay;
	/** 参与优惠支付方式 可多选 微信类型代号,支付宝类型代号 */
	private String payType;
	/** 有效开始日期 (yyyy-MM-dd HH:mm:ss) */
	private String validSTime;
	/** 失效结束日期 (yyyy-MM-dd HH:mm:ss) */
	private String validETime;
	/** 根据优惠方式 购买折扣->折扣价(0.1~9.9)，消费立减->立减金额(可带一位小数点) */
	private Float discount;
	/** 创建时间 */
	private String createTime;
	/** 优惠编号 */
	private String favourableId;
	
	public String getFavourableId() {
		return favourableId;
	}

	public void setFavourableId(String favourableId) {
		this.favourableId = favourableId;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setFavObjectId(String favObjectId) 
	{
		this.favObjectId = favObjectId;
	}

	public String getFavObjectId() 
	{
		return favObjectId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setFavType(String favType) 
	{
		this.favType = favType;
	}

	public String getFavType() 
	{
		return favType;
	}
	public void setFavObjId(String favObjId) 
	{
		this.favObjId = favObjId;
	}

	public String getFavObjId() 
	{
		return favObjId;
	}
	public void setFavWay(String favWay) 
	{
		this.favWay = favWay;
	}

	public String getFavWay() 
	{
		return favWay;
	}
	public void setPayType(String payType) 
	{
		this.payType = payType;
	}

	public String getPayType() 
	{
		return payType;
	}
	public void setValidSTime(String validSTime) 
	{
		this.validSTime = validSTime;
	}

	public String getValidSTime() 
	{
		return validSTime;
	}
	public void setValidETime(String validETime) 
	{
		this.validETime = validETime;
	}

	public String getValidETime() 
	{
		return validETime;
	}
	public void setDiscount(Float discount) 
	{
		this.discount = discount;
	}

	public Float getDiscount() 
	{
		return discount;
	}
	public void setCreateTime(String createTime) 
	{
		this.createTime = createTime;
	}

	public String getCreateTime() 
	{
		return createTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("favObjectId", getFavObjectId())
            .append("corpId", getCorpId())
            .append("favType", getFavType())
            .append("favObjId", getFavObjId())
            .append("favWay", getFavWay())
            .append("payType", getPayType())
            .append("validSTime", getValidSTime())
            .append("validETime", getValidETime())
            .append("discount", getDiscount())
            .append("createTime", getCreateTime())
            .toString();
    }
}
