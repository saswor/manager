package com.manage.project.system.supply.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.web.domain.BaseEntity;

/**
 * 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。表 as_supply_order
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class SupplyOrder extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 补货编号 */
	private String sOrderId;
	/** 公司编号 */
	private String corpId;
	/** 补货配置编号 */
	private String supplyId;
	/** 补货完成时间 (yyyy-MM-dd HH:mm:ss) */
	private String supplyFTime;
	/** 补货名称 */
	private String name;
	/** 线路编号 */
	private String lineId;
	/** 仓库编号 */
	private String wmId;
	/** 补货员编号 */
	private String supplierId;
	/** 补货站点数量 */
	private Integer num;
	/** 补货类型 1:全补齐 2:阈值补齐 */
	private String type;
	/** 需补货总量 */
	private Integer supplyNum;
	/** 当前商品容量 */
	private Integer curPNum;
	/** 商品最大存放量 */
	private Integer maxPNum;
	/** 补货状态 1:等待补货 2:补货中 2:补货完成 */
	private String curState;
	/** 补货状态变化时间 */
	private String stateTime;
	/** 完成状态 1:正常完成 2:超期完成  */
	private String finshState;
	/** 库存状态 1:未审核 2:已出库 */
	private String stockState;
	/** 库存状态变化时间 */
	private String sStateTime;
	/** 出库人 */
	private String checkId;
	/** 刷新时间 **/
	private String refreshDate;
	/**出库数量*/
	private Integer outNum;
	
	private Integer supplyrNum;
	
	private Integer supplynNum;
	
	private Integer supplydNum;
	
	private Integer supplylNum;
	
	private String supplydType;
	
	public Integer getSupplyrNum() {
		return supplyrNum;
	}

	public void setSupplyrNum(Integer supplyrNum) {
		this.supplyrNum = supplyrNum;
	}

	public Integer getSupplynNum() {
		return supplynNum;
	}

	public void setSupplynNum(Integer supplynNum) {
		this.supplynNum = supplynNum;
	}

	public Integer getSupplydNum() {
		return supplydNum;
	}

	public void setSupplydNum(Integer supplydNum) {
		this.supplydNum = supplydNum;
	}

	public Integer getSupplylNum() {
		return supplylNum;
	}

	public void setSupplylNum(Integer supplylNum) {
		this.supplylNum = supplylNum;
	}

	public String getSupplydType() {
		return supplydType;
	}

	public void setSupplydType(String supplydType) {
		this.supplydType = supplydType;
	}


	private float totalPrice;
	private String siteId;
	private String wmName;
	private String supplierName;
	
	/** 开始时间 */
	private String startDate;
	/** 结束时间 */
	private String endDate;
	
	public String getsOrderId() {
		return sOrderId;
	}

	public void setsOrderId(String sOrderId) {
		this.sOrderId = sOrderId;
	}

	public String getsStateTime() {
		return sStateTime;
	}

	public void setsStateTime(String sStateTime) {
		this.sStateTime = sStateTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getWmName() {
		return wmName;
	}

	public void setWmName(String wmName) {
		this.wmName = wmName;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getOutNum() {
		return outNum;
	}

	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(String refreshDate) {
		this.refreshDate = refreshDate;
	}

	public void setLogid(String logid)
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSOrderId(String sOrderId) 
	{
		this.sOrderId = sOrderId;
	}

	public String getSOrderId() 
	{
		return sOrderId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setSupplyId(String supplyId) 
	{
		this.supplyId = supplyId;
	}

	public String getSupplyId() 
	{
		return supplyId;
	}
	public void setSupplyFTime(String supplyFTime) 
	{
		this.supplyFTime = supplyFTime;
	}

	public String getSupplyFTime() 
	{
		return supplyFTime;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setLineId(String lineId) 
	{
		this.lineId = lineId;
	}

	public String getLineId() 
	{
		return lineId;
	}
	public void setWmId(String wmId) 
	{
		this.wmId = wmId;
	}

	public String getWmId() 
	{
		return wmId;
	}
	public void setSupplierId(String supplierId) 
	{
		this.supplierId = supplierId;
	}

	public String getSupplierId() 
	{
		return supplierId;
	}
	public void setNum(Integer num) 
	{
		this.num = num;
	}

	public Integer getNum() 
	{
		return num;
	}
	public void setType(String type) 
	{
		this.type = type;
	}

	public String getType() 
	{
		return type;
	}
	public void setSupplyNum(Integer supplyNum) 
	{
		this.supplyNum = supplyNum;
	}

	public Integer getSupplyNum() 
	{
		return supplyNum;
	}
	public void setCurPNum(Integer curPNum) 
	{
		this.curPNum = curPNum;
	}

	public Integer getCurPNum() 
	{
		return curPNum;
	}
	public void setMaxPNum(Integer maxPNum) 
	{
		this.maxPNum = maxPNum;
	}

	public Integer getMaxPNum() 
	{
		return maxPNum;
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
	public void setFinshState(String finshState) 
	{
		this.finshState = finshState;
	}

	public String getFinshState() 
	{
		return finshState;
	}
	public void setStockState(String stockState) 
	{
		this.stockState = stockState;
	}

	public String getStockState() 
	{
		return stockState;
	}
	public void setSStateTime(String sStateTime) 
	{
		this.sStateTime = sStateTime;
	}

	public String getSStateTime() 
	{
		return sStateTime;
	}
	public void setCheckId(String checkId) 
	{
		this.checkId = checkId;
	}

	public String getCheckId() 
	{
		return checkId;
	}


    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("sOrderId", getSOrderId())
            .append("corpId", getCorpId())
            .append("supplyId", getSupplyId())
            .append("supplyFTime", getSupplyFTime())
            .append("name", getName())
            .append("lineId", getLineId())
            .append("wmId", getWmId())
            .append("supplierId", getSupplierId())
            .append("num", getNum())
            .append("type", getType())
            .append("supplyNum", getSupplyNum())
            .append("curPNum", getCurPNum())
            .append("maxPNum", getMaxPNum())
            .append("curState", getCurState())
            .append("stateTime", getStateTime())
            .append("finshState", getFinshState())
            .append("stockState", getStockState())
            .append("sStateTime", getSStateTime())
            .append("checkId", getCheckId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
