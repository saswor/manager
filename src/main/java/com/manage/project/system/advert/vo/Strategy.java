package com.manage.project.system.advert.vo;

import java.util.List;

/**
 * 策略
 */
public class Strategy {
	
	private String strategyId;	// 策略id,删除与编辑时使用
	/** 根据平台广告位定义广告位 例如售卖机广告位 0101:首页广告 0102:首页子广告 0103:支付页面广告  */
	private String strategyPoint;
	/**平台广告位名称*/
	private String strategyPointName;
	/** 策略排序编号 */
	private Integer seqId;
	/** 策略类型 1:每天 2:特定时间 */
	private String strategyType;
	/** 播放开始时间 每天(HH:SS) 特定时间(yyyy-MM-dd HH:mm:ss) */
	private String playSTime;
	/** 播放结束时间 每天(HH:SS) 特定时间(yyyy-MM-dd HH:mm:ss) */
	private String playEtime;
	
	private String isDel;	//1删除
	public String getIsDel() {
		return isDel;
	}
	
	public String getStrategyPointName() {
		return strategyPointName;
	}

	public void setStrategyPointName(String strategyPointName) {
		this.strategyPointName = strategyPointName;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	/** 素材  */
	private List<Mstrategy> mstrategies;
	
	public Strategy() {
		
	}
	
	

	public String getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}

	public String getStrategyPoint() {
		return strategyPoint;
	}
	public void setStrategyPoint(String strategyPoint) {
		this.strategyPoint = strategyPoint;
	}
	public Integer getSeqId() {
		return seqId;
	}
	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}
	public String getStrategyType() {
		return strategyType;
	}
	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
	}
	public String getPlaySTime() {
		return playSTime;
	}
	public void setPlaySTime(String playSTime) {
		this.playSTime = playSTime;
	}
	public String getPlayEtime() {
		return playEtime;
	}
	public void setPlayEtime(String playEtime) {
		this.playEtime = playEtime;
	}
	public List<Mstrategy> getMstrategies() {
		return mstrategies;
	}
	public void setMstrategies(List<Mstrategy> mstrategies) {
		this.mstrategies = mstrategies;
	}
	
}