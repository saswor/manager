package com.manage.project.system.base.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端使用的菜单树结构
 * @author xufeng
 *
 */
public class MenuVo {
	private Long id;
	private String label;
	private Long parentId;
	private List<MenuVo> children = new ArrayList<MenuVo>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<MenuVo> getChildren() {
		return children;
	}
	public void setChildren(List<MenuVo> children) {
		this.children = children;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
