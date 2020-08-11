package com.manage.project.system.index.vo.column;

import java.util.List;

/**
 * 首页营收柱图vo,用于组装前台柱图需要的格式
 * @author xufeng
 *
 */
public class SaleNumVo {
	private String name;	// 名称
	private String type;	// 类型
	private List<Integer> data;	// 数据
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}
	
}
