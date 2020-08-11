package com.manage.project.system.base.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 区域目录树管理，每个公司拥有自己的区域。表 sys_region
 * 
 * @author xufeng
 * @date 2018-09-03
 */
public class Dispatch extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	private String id;
	/** 区域名称 */
	private String name;
	/** 区域全称 */
	private String namepath;
	/** 区域等级 0:根目录 1:国 2:省 3:市 4:区 */
	private Integer level;
	/** 父级区域编号 */
	private String parentId;
	
	private Date addtime;
	
	private Date modifytime;

	public String getExcelString() {
		return name+"--"+id;
	}

    public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getNamepath() {
		return namepath;
	}



	public void setNamepath(String namepath) {
		this.namepath = namepath;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}



	public Date getAddtime() {
		return addtime;
	}



	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}



	public Date getModifytime() {
		return modifytime;
	}



	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
}
