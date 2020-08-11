package com.manage.project.system.index.vo;

/**
 * 首页websocket返回的vo
 * @author xufeng
 *
 */
public class IndexDataVo {
	private int type;	// 返回类型，1为运营总览，2为近一月总览,3营收分析,4今日销售汇总,5今日销售点位排行,6今日销售商品排行
	private Object data;	// 返回数据
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
