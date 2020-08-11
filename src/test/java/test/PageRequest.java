//package test;
//
//import com.alibaba.fastjson.JSON;
//
//public class PageRequest {
//
//	private String name = "";
//	private int pageSize = 1;
//	private int pageNum = 1;
//	private String orderByColumn = "createTime";
//	private String isAsc = "desc";	
//	
//	public String getName() {
//		return name;
//	}
//
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//
//	public int getPageNum() {
//		return pageNum;
//	}
//
//
//	public void setPageNum(int pageNum) {
//		this.pageNum = pageNum;
//	}
//
//
//	public String getOrderByColumn() {
//		return orderByColumn;
//	}
//
//
//	public void setOrderByColumn(String orderByColumn) {
//		this.orderByColumn = orderByColumn;
//	}
//
//
//	public String getIsAsc() {
//		return isAsc;
//	}
//
//
//	public void setIsAsc(String isAsc) {
//		this.isAsc = isAsc;
//	}
//
//
//	public static void main(String[] args) {
//		PageRequest pr = new PageRequest();
//		System.out.println(JSON.toJSONString(pr));
//	}
//
//}
