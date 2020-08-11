package com.manage.project.system.advert.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 广告配置表 as_advert_config
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class AdvertConfig extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 广告编号 */
	private String advertId;
	/** 托管公司编号 */
	private String corpId;
	/** 广告描述或名称 */
	private String name;
	/** 投放方式 1:立即投放 2:延时投放 */
	private String deliveryType;
	/** 延时投放时间(yyyy-MM-dd HH:mm:ss) */
	private String lazyTime;
	/** 下发类型 1:全量 2:增量 */
	private String operType;
	/** 素材数量 */
	private Integer materialNum;
	/** 播放设备数量 */
	private Integer playerNum;
	/** 播放平台 01:柜子设备端 02:微信公众号 03:售卖app */
	private String playerPlat;
	/** 状态 1:等待执行 2:执行中 3:执行完成 4:已删除 */
	private String curState;
	/** 创建时间 */
	private String createTime;
	/** 下发类型 1:全量 2:增量 */
	private String operTypeName;
	/** 投放方式 1:立即投放 2:延时投放 */
	private String deliveryTypeName;
	/** 状态 1:等待执行 2:执行中 3:执行完成 4:已删除 */
	private String curStateName;
	
	public String getOperTypeName() {
		return SystemUtil.parseTransferType(operType);
	}

	public String getDeliveryTypeName() {
		return SystemUtil.parseDeliveryType(deliveryType);
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
	public void setAdvertId(String advertId) 
	{
		this.advertId = advertId;
	}

	public String getAdvertId() 
	{
		return advertId;
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
	public void setDeliveryType(String deliveryType) 
	{
		this.deliveryType = deliveryType;
	}

	public String getDeliveryType() 
	{
		return deliveryType;
	}
	public void setLazyTime(String lazyTime) 
	{
		this.lazyTime = lazyTime;
	}

	public String getLazyTime() 
	{
		return lazyTime;
	}
	public void setOperType(String operType) 
	{
		this.operType = operType;
	}

	public String getOperType() 
	{
		return operType;
	}
	public void setMaterialNum(Integer materialNum) 
	{
		this.materialNum = materialNum;
	}

	public Integer getMaterialNum() 
	{
		return materialNum;
	}
	public void setPlayerNum(Integer playerNum) 
	{
		this.playerNum = playerNum;
	}

	public Integer getPlayerNum() 
	{
		return playerNum;
	}
	public void setPlayerPlat(String playerPlat) 
	{
		this.playerPlat = playerPlat;
	}

	public String getPlayerPlat() 
	{
		return playerPlat;
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
            .append("advertId", getAdvertId())
            .append("corpId", getCorpId())
            .append("name", getName())
            .append("deliveryType", getDeliveryType())
            .append("lazyTime", getLazyTime())
            .append("operType", getOperType())
            .append("materialNum", getMaterialNum())
            .append("playerNum", getPlayerNum())
            .append("playerPlat", getPlayerPlat())
            .append("curState", getCurState())
            .append("createTime", getCreateTime())
            .toString();
    }
}
