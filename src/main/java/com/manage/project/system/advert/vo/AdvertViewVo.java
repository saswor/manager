package com.manage.project.system.advert.vo;

import java.util.List;

/**
 * 广告查看返回给前台的vo
 * @author xufeng
 *
 */
public class AdvertViewVo {
	private String name;	// 广告名称
	private String curStateName;	// 投放状态
	private List<AdvStrategy> advStrategies;	// 投放策略
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurStateName() {
		return curStateName;
	}

	public void setCurStateName(String curStateName) {
		this.curStateName = curStateName;
	}

	public List<AdvStrategy> getAdvStrategies() {
		return advStrategies;
	}

	public void setAdvStrategies(List<AdvStrategy> advStrategies) {
		this.advStrategies = advStrategies;
	}

	// 策略
	public class AdvStrategy {
		private String strategyPointName;	// 策略名称
		private String strateStr;	// 广告策略
		private List<AdvMstrategy> advMstrategies;	// 素材
		
		public AdvStrategy() {}
		public String getStrategyPointName() {
			return strategyPointName;
		}
		public void setStrategyPointName(String strategyPointName) {
			this.strategyPointName = strategyPointName;
		}
		public String getStrateStr() {
			return strateStr;
		}
		public void setStrateStr(String strateStr) {
			this.strateStr = strateStr;
		}
		public List<AdvMstrategy> getAdvMstrategies() {
			return advMstrategies;
		}
		public void setAdvMstrategies(List<AdvMstrategy> advMstrategies) {
			this.advMstrategies = advMstrategies;
		}
	}
	
	// 策略拥有的素材
	public class AdvMstrategy {
		public String mediaName;	// 素材名称
		public String mediaTypeName;	// 素材文件类型 1:视频 2:图片 3:文本
		public String mediaUrl;	// 文件地址
		public String getMediaName() {
			return mediaName;
		}
		public void setMediaName(String mediaName) {
			this.mediaName = mediaName;
		}
		public String getMediaTypeName() {
			return mediaTypeName;
		}
		public void setMediaTypeName(String mediaTypeName) {
			this.mediaTypeName = mediaTypeName;
		}
		public String getMediaUrl() {
			return mediaUrl;
		}
		public void setMediaUrl(String mediaUrl) {
			this.mediaUrl = mediaUrl;
		}
	}
}
