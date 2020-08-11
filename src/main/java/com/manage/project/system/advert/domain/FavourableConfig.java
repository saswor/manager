package com.manage.project.system.advert.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 商品优惠
促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。 注: 1.同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。 2.同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的。表 as_favourable_config
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class FavourableConfig extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 优惠编号 */
	private String favourableId;
	/** 托管公司编号 */
	private String corpId;
	/** 优惠名称 */
	private String name;
	/** 优惠方式 01:购买折扣 02:消费立减 */
	private String favWay;
	/** 优惠对象 1:整机 2:单品 */
	private String favTarget;
	/** 参与优惠支付方式 可多选 微信类型代号,支付宝类型代号 */
	private String payType;
	/** 优惠类型 1:统一优惠 2:分时段优惠 */
	private String favType;
	/** 有效开始日期 (yyyy-MM-dd) */
	private String validSTime;
	/** 失效结束日期 (yyyy-MM-dd) */
	private String validETime;
	/** 状态 1:等待执行 2:正在执行 3:执行完成 4:已删除 */
	private String curState;
	/** 优惠对象数量 */
	private Integer favNum;
	/** 已优惠金额总数 */
	private Float favMoney;
	/** 已优惠次数总数 */
	private Integer favTimes;
	/** 创建时间 */
	private String createTime;
	
	/** 优惠方式 01:购买折扣 02:消费立减 */
	private String favWayName;
	/** 优惠对象 1:整机 2:单品 */
	private String favTargetName;
	/** 优惠类型 1:统一优惠 2:分时段优惠 */
	private String favTypeName;
	/** 状态 1:等待执行 2:正在执行 3:执行完成 4:已删除 */
	private String curStateName;
	
	public String getFavWayName() {
		return SystemUtil.parseFavWay(favWay);
	}

	public String getFavTargetName() {
		return SystemUtil.parseFavTarget(favTarget);
	}

	public String getFavTypeName() {
		return SystemUtil.parseFavType(favType);
	}

	public String getCurStateName() {
		return SystemUtil.parseFavState(curState);
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
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
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setFavWay(String favWay) 
	{
		this.favWay = favWay;
	}

	public String getFavWay() 
	{
		return favWay;
	}
	public void setFavTarget(String favTarget) 
	{
		this.favTarget = favTarget;
	}

	public String getFavTarget() 
	{
		return favTarget;
	}
	public void setPayType(String payType) 
	{
		this.payType = payType;
	}

	public String getPayType() 
	{
		return payType;
	}
	public void setFavType(String favType) 
	{
		this.favType = favType;
	}

	public String getFavType() 
	{
		return favType;
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
	public void setCurState(String curState) 
	{
		this.curState = curState;
	}

	public String getCurState() 
	{
		return curState;
	}
	public void setFavNum(Integer favNum) 
	{
		this.favNum = favNum;
	}

	public Integer getFavNum() 
	{
		return favNum;
	}
	public void setFavMoney(Float favMoney) 
	{
		this.favMoney = favMoney;
	}

	public Float getFavMoney() 
	{
		return favMoney;
	}
	public void setFavTimes(Integer favTimes) 
	{
		this.favTimes = favTimes;
	}

	public Integer getFavTimes() 
	{
		return favTimes;
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
            .append("favourableId", getFavourableId())
            .append("corpId", getCorpId())
            .append("name", getName())
            .append("favWay", getFavWay())
            .append("favTarget", getFavTarget())
            .append("payType", getPayType())
            .append("favType", getFavType())
            .append("validSTime", getValidSTime())
            .append("validETime", getValidETime())
            .append("curState", getCurState())
            .append("favNum", getFavNum())
            .append("favMoney", getFavMoney())
            .append("favTimes", getFavTimes())
            .append("createTime", getCreateTime())
            .toString();
    }
}
