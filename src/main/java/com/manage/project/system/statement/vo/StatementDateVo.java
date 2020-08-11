/**
 * 
 */
package com.manage.project.system.statement.vo;

/**
 * 对账上次上传账单截止日期
 * 
 * @author zhangjiawei
 * @date 2018年10月29日
 * 
 */
public class StatementDateVo {

	/**支付宝上次对账日期*/
	private String alipayDate;
	/**微信上次对账日期*/
	private String wechatDate;
	/**创建时间*/
	private String createTime;

	public String getAlipayDate() {
		return alipayDate;
	}

	public void setAlipayDate(String alipayDate) {
		this.alipayDate = alipayDate;
	}

	public String getWechatDate() {
		return wechatDate;
	}

	public void setWechatDate(String wechatDate) {
		this.wechatDate = wechatDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
		
}
