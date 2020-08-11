package com.manage.project.system.index.vo;

import java.io.Serializable;

/**
 * 首页仪表盘Vo
 * 
 * @author xufeng
 *
 */
public class IndexSummaryVo implements Serializable {
	
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private OperateReviewVo operateReviewVo; // 首页运营总鉴
	private OneMonthReviewVo todayReviewVo; // 首页,近一月总览 今天
	private OneMonthReviewVo yesterdayReviewVo; // 首页,近一月总览 昨天
	private OneMonthReviewVo sevenReviewVo; // 首页,近一月总览 近7天
	private OneMonthReviewVo thirtyReviewVo; // 首页,近一月总览 近30天
	private TodaySaleVo TodaySaleVo; // 今日销售点位排行榜
	public OperateReviewVo getOperateReviewVo() {
		return operateReviewVo;
	}
	public void setOperateReviewVo(OperateReviewVo operateReviewVo) {
		this.operateReviewVo = operateReviewVo;
	}
	public OneMonthReviewVo getTodayReviewVo() {
		return todayReviewVo;
	}
	public void setTodayReviewVo(OneMonthReviewVo todayReviewVo) {
		this.todayReviewVo = todayReviewVo;
	}
	public OneMonthReviewVo getYesterdayReviewVo() {
		return yesterdayReviewVo;
	}
	public void setYesterdayReviewVo(OneMonthReviewVo yesterdayReviewVo) {
		this.yesterdayReviewVo = yesterdayReviewVo;
	}
	public OneMonthReviewVo getSevenReviewVo() {
		return sevenReviewVo;
	}
	public void setSevenReviewVo(OneMonthReviewVo sevenReviewVo) {
		this.sevenReviewVo = sevenReviewVo;
	}
	public OneMonthReviewVo getThirtyReviewVo() {
		return thirtyReviewVo;
	}
	public void setThirtyReviewVo(OneMonthReviewVo thirtyReviewVo) {
		this.thirtyReviewVo = thirtyReviewVo;
	}
	public TodaySaleVo getTodaySaleVo() {
		return TodaySaleVo;
	}
	public void setTodaySaleVo(TodaySaleVo todaySaleVo) {
		TodaySaleVo = todaySaleVo;
	}

	
}
