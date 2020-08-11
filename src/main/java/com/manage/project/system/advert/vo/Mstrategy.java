package com.manage.project.system.advert.vo;

/**
 * 素材
 */
public class Mstrategy {
	
	private String mstrategyId;	// 策略素材编号，删除编辑时使用
	/** 策略中素材排序编号 */
	private Integer seqId;
	/** 素材编号 */
	private String materialId;
	/** 素材名称 */
	private String mediaName;
	/** 素材云端HTTP地址 */
	private String mediaUrl;
	/** 素材文件类型 1:视频 2:图片 3:文本 */
	private String mediaType;
	/** 播放耗时(文本和图片需要设置时间，视频自动计算) */
	private Integer playerTime;
	/** 播放次数 */
	private Integer playerTimes;
	private String isDel;	//1删除
	private String mediaTypeName;	// 类型名字
	

	public Integer getPlayerTimes() {
		return playerTimes;
	}

	public void setPlayerTimes(Integer playerTimes) {
		this.playerTimes = playerTimes;
	}

	public String getMediaTypeName() {
		return mediaTypeName;
	}

	public void setMediaTypeName(String mediaTypeName) {
		this.mediaTypeName = mediaTypeName;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	public Mstrategy() {
		
	}
	
	public String getMstrategyId() {
		return mstrategyId;
	}

	public void setMstrategyId(String mstrategyId) {
		this.mstrategyId = mstrategyId;
	}

	public Integer getSeqId() {
		return seqId;
	}
	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public Integer getPlayerTime() {
		return playerTime;
	}
	public void setPlayerTime(Integer playerTime) {
		this.playerTime = playerTime;
	}	
}
