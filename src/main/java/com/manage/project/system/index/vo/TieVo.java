package com.manage.project.system.index.vo;

import java.util.List;
import java.util.Map;

/**
 * 今日销售商品top10 饼状图 数据格式
 * @author xufeng
 *
 */
public class TieVo {
	private List<Map<String, Integer>> list;

	public List<Map<String, Integer>> getList() {
		return list;
	}

	public void setList(List<Map<String, Integer>> list) {
		this.list = list;
	}
	
}
