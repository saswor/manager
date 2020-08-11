package com.manage.project.system.base.vo;

/**
 * 只显示用户id和name
 * @author xufeng
 *
 */
public class UserIdNameVo {
	private String userId;
	private String userName;
	private String loginName;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
