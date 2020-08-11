package com.manage.project.common.vo;

/**
 * 通用vo,用于接收json格式请求参数，进行删除等操作
 * @author xufeng
 *
 */
public class CommonVo {
	private String ids;	// 接收批量删除数据id
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
}
