package com.manage.project.system.base.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录返回值
 * @author xufeng
 *
 */
public class LoginReturnVo {
	private String reTCode = "0000";
	private List<String> roles = new ArrayList<String>();
	private String token;
	private String introduction = "introduction";
	private String avatar = "";
	private String name = "name";
	
	
	public String getReTCode() {
		return reTCode;
	}
	public void setReTCode(String reTCode) {
		this.reTCode = reTCode;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
