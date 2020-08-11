package com.manage.project.system.advert.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 广告配置表 as_advert_mstrategy
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class AdvertMstrategy extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 策略素材编号 */
	private String mstrategyId;
	/** 策略编号 */
	private String strategyId;
	/** 广告编号 */
	private String advertId;
	/** 托管公司编号 */
	private String corpId;
	/** 策略中素材排序编号 */
	private Integer seqId;
	/** 素材编号 */
	private String materialId;
	/** 素材名称 */
	private String mediaName;
	/** 素材云端HTTP地址 */
	private String mediaUrl;
	/** 素材云端HTTP缩略图地址 */
	private String mediaSUrl;
	/** 素材文件类型 1:视频 2:图片 3:文本 */
	private String mediaType;
	/** 播放耗时(文本和图片需要设置时间，视频自动计算) */
	private Integer playerTime;
	/** 素材播放次数 */
	private Integer playerTimes;
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
	public void setMstrategyId(String mstrategyId) 
	{
		this.mstrategyId = mstrategyId;
	}

	public String getMstrategyId() 
	{
		return mstrategyId;
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
	public void setMaterialId(String materialId) 
	{
		this.materialId = materialId;
	}

	public String getMaterialId() 
	{
		return materialId;
	}
	public void setMediaName(String mediaName) 
	{
		this.mediaName = mediaName;
	}

	public String getMediaName() 
	{
		return mediaName;
	}
	public void setMediaUrl(String mediaUrl) 
	{
		this.mediaUrl = mediaUrl;
	}

	public String getMediaUrl() 
	{
		return mediaUrl;
	}
	public void setMediaSUrl(String mediaSUrl) 
	{
		this.mediaSUrl = mediaSUrl;
	}

	public String getMediaSUrl() 
	{
		return mediaSUrl;
	}
	public void setMediaType(String mediaType) 
	{
		this.mediaType = mediaType;
	}

	public String getMediaType() 
	{
		return mediaType;
	}
	public void setPlayerTime(Integer playerTime) 
	{
		this.playerTime = playerTime;
	}

	public Integer getPlayerTime() 
	{
		return playerTime;
	}
	public void setPlayerTimes(Integer playerTimes) 
	{
		this.playerTimes = playerTimes;
	}

	public Integer getPlayerTimes() 
	{
		return playerTimes;
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
            .append("mstrategyId", getMstrategyId())
            .append("strategyId", getStrategyId())
            .append("advertId", getAdvertId())
            .append("corpId", getCorpId())
            .append("seqId", getSeqId())
            .append("materialId", getMaterialId())
            .append("mediaName", getMediaName())
            .append("mediaUrl", getMediaUrl())
            .append("mediaSUrl", getMediaSUrl())
            .append("mediaType", getMediaType())
            .append("playerTime", getPlayerTime())
            .append("playerTimes", getPlayerTimes())
            .append("createTime", getCreateTime())
            .toString();
    }
}
