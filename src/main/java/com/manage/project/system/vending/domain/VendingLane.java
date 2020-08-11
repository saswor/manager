package com.manage.project.system.vending.domain;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.util.SystemUtil;

/**
 * 售货机货道表 as_vending_lane
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingLane extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	
	private String cabinetId;
	/** 货道主键 */
	private String slaneId;
	/** 售货机编号 */
	private String siteId;
	/** 售货机名称 */
	private String siteName;
	/** 货道编号(从左到右、从上到下) */
	private Integer laneId;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetType;
	/** 机型 */
	private String deviceId;
	/** 货道编号，根据柜型用户输入的货道起始编号开始递增 */
	private Integer laneCode;
	/** 行数 */
	private Integer row;
	/** 列数 */
	private Integer col;
	/** 排数 */
	private Integer arrange;
	/** 货道状态 */
	private String curState;
	/** 状态时间 */
	private String stateTime;
	/** 托管公司编号 */
	private String corpId;
	
	/** 商品编号 */
	private String productId;
	
	/** 商品图片(json存储) */
	private String productPic;
	
	/** 售卖价格 */
	private Float salePrice;
	
	/** 存放容量 */
	private Integer capacity;
	/** 缺货阈值 */
	private Integer warnCap;
	/** 当前容量 */
	private Integer curCap;
	/** 货道状态 1:正常 2:停用 */
	private String laneSate;
	/** 货道停用时间 */
	private String lSateTime;
	private String pic;	// 第一个图片
	private String productName;
	
	private String plogid;	// 所属货道商品关系表的logid
	
	public String getPlogid() {
		return plogid;
	}

	public void setPlogid(String plogid) {
		this.plogid = plogid;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSlaneId(String slaneId) 
	{
		this.slaneId = slaneId;
	}

	public String getSlaneId() 
	{
		return slaneId;
	}
	public void setSiteId(String siteId) 
	{
		this.siteId = siteId;
	}

	public String getSiteId() 
	{
		return siteId;
	}
	public void setSiteName(String siteName) 
	{
		this.siteName = siteName;
	}

	public String getSiteName() 
	{
		return siteName;
	}
	public void setLaneId(Integer laneId) 
	{
		this.laneId = laneId;
	}

	public Integer getLaneId() 
	{
		return laneId;
	}
	public void setCabinetType(String cabinetType) 
	{
		this.cabinetType = cabinetType;
	}

	public String getCabinetType() 
	{
		return cabinetType;
	}
	public void setDeviceId(String deviceId) 
	{
		this.deviceId = deviceId;
	}

	public String getDeviceId() 
	{
		return deviceId;
	}
	public void setLaneCode(Integer laneCode) 
	{
		this.laneCode = laneCode;
	}

	public Integer getLaneCode() 
	{
		return laneCode;
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
	public void setCurState(String curState) 
	{
		this.curState = curState;
	}

	public String getCurState() 
	{
		return curState;
	}
	public void setStateTime(String stateTime) 
	{
		this.stateTime = stateTime;
	}

	public String getStateTime() 
	{
		return stateTime;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

    public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductPic() {
		return productPic;
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}

	public Float getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getWarnCap() {
		return warnCap;
	}

	public void setWarnCap(Integer warnCap) {
		this.warnCap = warnCap;
	}

	public Integer getCurCap() {
		return curCap;
	}

	public void setCurCap(Integer curCap) {
		this.curCap = curCap;
	}

	public String getLaneSate() {
		return laneSate;
	}

	public void setLaneSate(String laneSate) {
		this.laneSate = laneSate;
	}

	public String getlSateTime() {
		return lSateTime;
	}

	public void setlSateTime(String lSateTime) {
		this.lSateTime = lSateTime;
	}

	public String getPic() {
		return SystemUtil.jsonpicToPic(productPic);
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("slaneId", getSlaneId())
            .append("siteId", getSiteId())
            .append("siteName", getSiteName())
            .append("laneId", getLaneId())
            .append("cabinetType", getCabinetType())
            .append("deviceId", getDeviceId())
            .append("laneCode", getLaneCode())
            .append("row", getRow())
            .append("col", getCol())
            .append("arrange", getArrange())
            .append("curState", getCurState())
            .append("stateTime", getStateTime())
            .append("createTime", getCreateTime())
            .append("corpId", getCorpId())
            .toString();
    }
}
