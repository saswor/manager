package com.manage.project.system.product.vo;

import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.project.system.product.domain.ProductLunder;

/**
 * 下架商品信息vo
 * 
 * @author zhangjiawei
 * @date 2018-11-19
 */
public class ProductLunderVo{

	private static final long serialVersionUID = 1L;
	/**商品名称*/
	@Excel(name="商品名称")
	private String productName;
	/**商品编码*/
	@Excel(name="商品编码")
	private String productCode;
	/**售货机编码*/
	@Excel(name="售货机编码")
	private String siteCode;
	/** 售货机名称 */
	@Excel(name="售货机编码")
	private String siteName;
	/**线路编码*/
	@Excel(name="线路编码")
	private String lineCode;
	/**线路名称*/
	@Excel(name="线路名称")
	private String lineName;
	/**区域编码*/
	@Excel(name="区域编码")
	private String districtCode;
	/**区域名称*/
	@Excel(name="区域名称")
	private String districtName;
	
	private String beginTime;
	
	private String endTime;
	
	/** 记录编号 */
	private String logid;
	/** 下架记录编号 */
	private String lunderId;
	/** 商品下架编号 */
	private String underId;
	/** 区域编号 */
	private String districtId;
	/** 线路编号 */
	private String lineId;
	/** 点位编号 */
	private String pointId;
	/** 售货机编号 */
	private String siteId;
	/** 商品编号 */
	private String productId;
	/** 下架数量 */
	@Excel(name="下架数量")
	private Integer underNum;
	/** 货道开始编号 */
	@Excel(name="货道开始编号")
	private Integer laneSId;
	/** 货道结束编号 */
	@Excel(name="货道结束编号")
	private Integer laneEId;
	/** 托管公司编号 */
	private String corpId;
	/** 创建时间 */
	@Excel(name="下架时间")
	private String createTime;
	
	private String vUnderId;
	
	private String curState;

	public String getvUnderId() {
		return vUnderId;
	}

	public void setvUnderId(String vUnderId) {
		this.vUnderId = vUnderId;
	}

	public String getCurState() {
		return curState;
	}

	public void setCurState(String curState) {
		this.curState = curState;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setLunderId(String lunderId) 
	{
		this.lunderId = lunderId;
	}

	public String getLunderId() 
	{
		return lunderId;
	}
	public void setUnderId(String underId) 
	{
		this.underId = underId;
	}

	public String getUnderId() 
	{
		return underId;
	}
	public void setDistrictId(String districtId) 
	{
		this.districtId = districtId;
	}

	public String getDistrictId() 
	{
		return districtId;
	}
	public void setLineId(String lineId) 
	{
		this.lineId = lineId;
	}

	public String getLineId() 
	{
		return lineId;
	}
	public void setPointId(String pointId) 
	{
		this.pointId = pointId;
	}

	public String getPointId() 
	{
		return pointId;
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
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setUnderNum(Integer underNum) 
	{
		this.underNum = underNum;
	}

	public Integer getUnderNum() 
	{
		return underNum;
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
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setCreateTime(String createTime) 
	{
		this.createTime = createTime;
	}

	public String getCreateTime() 
	{
		return createTime;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
}
