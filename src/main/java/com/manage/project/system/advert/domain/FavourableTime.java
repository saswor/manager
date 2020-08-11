package com.manage.project.system.advert.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。表 as_favourable_time
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class FavourableTime extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 时间段优惠 */
	private String favTimeId;
	/** 优惠编号 */
	private String favourableId;
	/** 托管公司编号 */
	private String corpId;
	/** 有效开始日期 (yyyy-MM-dd) */
	private String validSTime;
	/** 失效结束日期 (yyyy-MM-dd) */
	private String validETime;
	/** 开始时间(HH:mm:ss) */
	private String favSTime;
	/** 结束日期(HH:mm:ss) */
	private String favETime;
	/** 优惠方式 01:购买折扣 02:消费立减 */
	private String favWay;
	/** 时间段优惠金额 根据优惠方式 存储 购买折扣->折扣价(0.1~9.9)，消费立减->立减金额(可带一位小数点) */
	private Float discount;
	/** 状态 1:等待执行 2:正在执行 3:执行完成 */
	private String curState;
	/** 创建时间 */
	private String createTime;

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setFavTimeId(String favTimeId) 
	{
		this.favTimeId = favTimeId;
	}

	public String getFavTimeId() 
	{
		return favTimeId;
	}
	public void setFavourableId(String favourableId) 
	{
		this.favourableId = favourableId;
	}

	public String getFavourableId() 
	{
		return favourableId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
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
	public void setFavSTime(String favSTime) 
	{
		this.favSTime = favSTime;
	}

	public String getFavSTime() 
	{
		return favSTime;
	}
	public void setFavETime(String favETime) 
	{
		this.favETime = favETime;
	}

	public String getFavETime() 
	{
		return favETime;
	}
	public void setFavWay(String favWay) 
	{
		this.favWay = favWay;
	}

	public String getFavWay() 
	{
		return favWay;
	}
	public void setDiscount(Float discount) 
	{
		this.discount = discount;
	}

	public Float getDiscount() 
	{
		return discount;
	}
	public void setCurState(String curState) 
	{
		this.curState = curState;
	}

	public String getCurState() 
	{
		return curState;
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
            .append("favTimeId", getFavTimeId())
            .append("favourableId", getFavourableId())
            .append("corpId", getCorpId())
            .append("validSTime", getValidSTime())
            .append("validETime", getValidETime())
            .append("favSTime", getFavSTime())
            .append("favETime", getFavETime())
            .append("favWay", getFavWay())
            .append("discount", getDiscount())
            .append("curState", getCurState())
            .append("createTime", getCreateTime())
            .toString();
    }
}
