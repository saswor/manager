package com.manage.project.system.base.domain;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 系统sequence表
 * @author xufenfg
 *
 */
public class SequenceId extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/** 记录编号 */
	private String logid;
	/** 商户编号 */
	private String corpId;
	/** 表名称 */
	private String name;
	private String id;
	private String description;
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
