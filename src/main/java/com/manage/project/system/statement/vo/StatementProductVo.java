/**
 * 
 */
package com.manage.project.system.statement.vo;

import com.manage.project.system.statement.domain.StatementProduct;

/**
 * 补货商品售出详情vo
 * 
 * @author zhangjiawei
 * @date 2018年10月19日
 * 
 */
public class StatementProductVo extends StatementProduct{

	private static final long serialVersionUID = 1L;
	
	/** 支付状态1:等待支付 2:支付成功 3:支付失败 */
	private String payState;
	/** 退款状态 0:无 1:全额退款 2:部分退款 2:退款失败 */
	private String returnType;
	/** 出柜类型名称 */
	private String outTypeName;
	/** 支付类型名称 */
	private String payTypeName;
	/** 对账状态名称 */
	private String curStateName;
	/** 支付状态名称 */
	private String payStateName;

	public String getPayStateName() {
		return payStateName;
	}

	public void setPayStateName(String payStateName) {
		this.payStateName = payStateName;
	}

	public String getOutTypeName() {
		return outTypeName;
	}

	public void setOutTypeName(String outTypeName) {
		this.outTypeName = outTypeName;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getCurStateName() {
		return curStateName;
	}

	public void setCurStateName(String curStateName) {
		this.curStateName = curStateName;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

}
