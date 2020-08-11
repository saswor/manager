package com.manage.project.system.advert.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 广告配置表 as_advert_strategy
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class AdvertStrategy extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 策略编号 */
	private String strategyId;
	/** 广告编号 */
	private String advertId;
	/** 托管公司编号 */
	private String corpId;
	/** 策略排序编号 */
	private Integer seqId;
	/** 根据平台广告位定义广告位 例如售卖机广告位 0101:首页广告 0102:首页子广告 0103:支付页面广告  */
	private String strategyPoint;
	/**平台广告位名称*/
	private String strategyPointName;
	/** 策略类型 1:每天 2:特定时间 */
	private String strategyType;
	/** 播放开始时间 每天(HH:SS) 特定时间(yyyy-MM-dd HH:mm:ss) */
	private String playSTime;
	/** 播放结束时间 每天(HH:SS) 特定时间(yyyy-MM-dd HH:mm:ss) */
	private String playEtime;
	/** 素材数量 */
	private Integer materialNum;
	/** 当前素材播放所耗时(分钟) */
	private Integer playerTime;
	/** 创建时间 */
	private String createTime;

	public String getStrategyPointName() {
		return strategyPointName;
	}

	public void setStrategyPointName(String strategyPointName) {
		this.strategyPointName = strategyPointName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setStrategyId(String strategyId) 
	{
		this.strategyId = strategyId;
	}

	public String getStrategyId() 
	{
		return strategyId;
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
	public void setSeqId(Integer seqId) 
	{
		this.seqId = seqId;
	}

	public Integer getSeqId() 
	{
		return seqId;
	}
	public void setStrategyPoint(String strategyPoint) 
	{
		this.strategyPoint = strategyPoint;
	}

	public String getStrategyPoint() 
	{
		return strategyPoint;
	}
	public void setStrategyType(String strategyType) 
	{
		this.strategyType = strategyType;
	}

	public String getStrategyType() 
	{
		return strategyType;
	}
	public void setPlaySTime(String playSTime) 
	{
		this.playSTime = playSTime;
	}

	public String getPlaySTime() 
	{
		return playSTime;
	}
	public void setPlayEtime(String playEtime) 
	{
		this.playEtime = playEtime;
	}

	public String getPlayEtime() 
	{
		return playEtime;
	}
	public void setMaterialNum(Integer materialNum) 
	{
		this.materialNum = materialNum;
	}

	public Integer getMaterialNum() 
	{
		return materialNum;
	}
	public void setPlayerTime(Integer playerTime) 
	{
		this.playerTime = playerTime;
	}

	public Integer getPlayerTime() 
	{
		return playerTime;
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
            .append("strategyId", getStrategyId())
            .append("advertId", getAdvertId())
            .append("corpId", getCorpId())
            .append("seqId", getSeqId())
            .append("strategyPoint", getStrategyPoint())
            .append("strategyType", getStrategyType())
            .append("playSTime", getPlaySTime())
            .append("playEtime", getPlayEtime())
            .append("materialNum", getMaterialNum())
            .append("playerTime", getPlayerTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
