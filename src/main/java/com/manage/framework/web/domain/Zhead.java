package com.manage.framework.web.domain;

import java.io.Serializable;

/**
 * 返回信息头部
 * @author xufeng
 *
 */
public class Zhead implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String SUCCESS = "0000";
	public static final String ERROR = "1111";

	/**
	 * 返回代码，返回代码为4位数字，不带正负符号。 默认0000表示成功，其他表示错误。
	 */
	private String reTCode = SUCCESS;
	
	/**
	 * 返回信息，信息长度<=100,必须传英文信息，也可不传.
	 */
	private String retMsg;
	
	public Zhead() {

	}
	
	public Zhead(String retMsg) {
		this.retMsg = retMsg;
	}
	
	public Zhead(String reTCode, String retMsg) {
		this.reTCode = reTCode;
		this.retMsg = retMsg;
	}

	public String getReTCode() {
		return reTCode;
	}

	public void setReTCode(String reTCode) {
		this.reTCode = reTCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
}
