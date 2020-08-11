package com.manage.project.system.vending.domain;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 售货机货道表 as_vending_plane
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingPlane extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 模板货道编号 */
	private String mLaneId;
	/** 模板编号 */
	private String mConfigId;
	/** 模板名称 */
	private String name;
	/** 货道开始编号(一个商品占多个货道) */
	@Excel(name = "货道开始编号", column = "A")
	private Integer laneSId;
	/** 货道结束编号(如果只有一个商品则只有开始货道编号) */
	@Excel(name = "货道结束编号", column = "B")
	private Integer laneEId;
	/** 行数 */
	private Integer row;
	/** 列数 */
	private Integer col;
	/** 排数 */
	private Integer arrange;
	/** 商品编号 */
	@Excel(name = "商品编号", column = "C")
	private String productId;
	/** 商品名称 */
	@Excel(name = "商品名称", column = "D")
	private String productName;
	/** 商品图片(json存储) */
	private String productPic;
	/** 售卖价格 */
	@Excel(name = "售卖价格", column = "E")
	private Float salePrice;
	/** 存放容量 */
	@Excel(name = "存放容量", column = "F")
	private Integer capacity;
	/** 缺货阈值 */
	@Excel(name = "缺货阈值", column = "G")
	private Integer warnCap;
	/** 托管公司编号 */
	private String corpId;

	
	private String pic;	// 第一个图片
	
	private String laneSate;	// 货道状态 1:正常 2:停用
	
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLaneSate() {
		return laneSate;
	}

	public void setLaneSate(String laneSate) {
		this.laneSate = laneSate;
	}

	public static void main(String[] args) {
		String a = "[{'os':'03','pic':'8889-0000002_android_180X180_nomarl.png','px':'180X180','type':'1'},{'os':'02','pic':'8889-0000002_wechat_180X180_thumbnail'}]";
		List<Map> list = JSONObject.parseArray(a, Map.class);
		String pic = (String)list.get(0).get("pic");
		System.out.println(pic);
		
//		try {
//    		JSONObject json = JSONObject.parseObject(a);
//    	System.out.println(json);
//        	List<Map<String, String>> t = (List<Map<String, String>>)json;
//        	if (t == null || t.isEmpty()) {
//        		
//        	}
//        	Map<String, String> m = t.get(0);
//        	System.out.println(m.get("pic"));
//    	} catch (Exception e) {
//    		
//    	}
	}
	public String getPic() {
		return SystemUtil.jsonpicToPic(productPic);
	}

	public String getProductName() {
//		return "";
		return SystemUtil.getProductNameById(productId);
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setMLaneId(String mLaneId) 
	{
		this.mLaneId = mLaneId;
	}

	public String getMLaneId() 
	{
		return mLaneId;
	}
	public void setMConfigId(String mConfigId) 
	{
		this.mConfigId = mConfigId;
	}

	public String getMConfigId() 
	{
		return mConfigId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setLaneSId(Integer laneSId) 
	{
		this.laneSId = laneSId;
	}

	public Integer getLaneSId() 
	{
		return laneSId;
	}
	public void setLaneEId(Integer laneEId) 
	{
		this.laneEId = laneEId;
	}

	public Integer getLaneEId() 
	{
		return laneEId;
	}
	public void setRow(Integer row) 
	{
		this.row = row;
	}

	public Integer getRow() 
	{
		return row;
	}
	public void setCol(Integer col) 
	{
		this.col = col;
	}

	public Integer getCol() 
	{
		return col;
	}
	public void setArrange(Integer arrange) 
	{
		this.arrange = arrange;
	}

	public Integer getArrange() 
	{
		return arrange;
	}
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setProductPic(String productPic) 
	{
		this.productPic = productPic;
	}

	public String getProductPic() 
	{
		return productPic;
	}
	public void setSalePrice(Float salePrice) 
	{
		this.salePrice = salePrice;
	}

	public Float getSalePrice() 
	{
		return salePrice;
	}
	public void setCapacity(Integer capacity) 
	{
		this.capacity = capacity;
	}

	public Integer getCapacity() 
	{
		return capacity;
	}
	public void setWarnCap(Integer warnCap) 
	{
		this.warnCap = warnCap;
	}

	public Integer getWarnCap() 
	{
		return warnCap;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("mLaneId", getMLaneId())
            .append("mConfigId", getMConfigId())
            .append("name", getName())
            .append("laneSId", getLaneSId())
            .append("laneEId", getLaneEId())
            .append("row", getRow())
            .append("col", getCol())
            .append("arrange", getArrange())
            .append("productId", getProductId())
            .append("productPic", getProductPic())
            .append("salePrice", getSalePrice())
            .append("capacity", getCapacity())
            .append("warnCap", getWarnCap())
            .append("createTime", getCreateTime())
            .append("corpId", getCorpId())
            .toString();
    }
}
