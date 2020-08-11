package com.manage.project.system.statement.vo;

import java.util.List;

public class OrderApplyDetailVo {

	private List<OrderApplyVo> orderApplyInfo;
	private List<OrderProductVo> orderProductInfo;
	private List<OrderBoxVo> orderBoxInfo;
	
	public List<OrderApplyVo> getOrderApplyInfo() {
		return orderApplyInfo;
	}
	public void setOrderApplyInfo(List<OrderApplyVo> orderApplyInfo) {
		this.orderApplyInfo = orderApplyInfo;
	}
	public List<OrderProductVo> getOrderProductInfo() {
		return orderProductInfo;
	}
	public void setOrderProductInfo(List<OrderProductVo> orderProductInfo) {
		this.orderProductInfo = orderProductInfo;
	}
	public List<OrderBoxVo> getOrderBoxInfo() {
		return orderBoxInfo;
	}
	public void setOrderBoxInfo(List<OrderBoxVo> orderBoxInfo) {
		this.orderBoxInfo = orderBoxInfo;
	}
	
}
