package com.manage.project.system.advert.vo;

import java.util.ArrayList;
import java.util.List;

import com.manage.project.system.advert.domain.AdvertStrategy;

/**
 * 自定义广告位
 * 
 * @author zhangjiawei
 *
 */
public class AdvertPlat {

	/**广告位编号*/
	private String strategyPoint;
	/**广告位名称*/
	private String strategyPointName;
	/**策略*/
	private List<Strategy> strategies=new ArrayList<Strategy>();
	
	public String getStrategyPoint() {
		return strategyPoint;
	}
	public void setStrategyPoint(String strategyPoint) {
		this.strategyPoint = strategyPoint;
	}
	public String getStrategyPointName() {
		return strategyPointName;
	}
	public void setStrategyPointName(String strategyPointName) {
		this.strategyPointName = strategyPointName;
	}
	public List<Strategy> getStrategies() {
		return strategies;
	}
	public void setStrategies(List<Strategy> strategies) {
		this.strategies = strategies;
	}
	
	
}
