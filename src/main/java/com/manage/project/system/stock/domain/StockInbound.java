package com.manage.project.system.stock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.common.utils.StringUtils;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.util.SystemUtil;

/**
 * 仓库入库记录表 as_stock_inbound
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class StockInbound extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 仓库入库记录编号 */
	private String winboundId;
	/** 仓采购记录编号 */
	private String wpurchaseId;
	/** 仓库编号 */
	private String stockId;
	/** 仓库名称 */
	private String stokcName;
	/** 总入库数量 */
	private Integer pnum;
	/** 入库商品种类数 */
	private Integer tnum;
	/** 入库采购总价 */
	private Float totalPrice;
	/** 当前状态 1:未入库 2:已入库 */
	private String curState;
	/** 当前状态 1:未入库 2:已入库 */
	private String curStateName;
	/** 当前状态变化时间 */
	private String stateTime;
	/** 入库人编号 */
	private String inboundId;
	/** 入库人姓名 */
	private String inboundName;
	/** 托管公司编号 */
	private String corpId;
	/**
	 * 采购状态 全部为空。1:正常 2:删除
	 */
	private String buyState;
	
	/** 未入库数量 */
	private Integer nopNum;
	
	/**
	 * 采购状态 全部为空。1:正常 2:删除
	 */
	private String buyStateName;
	/** 总采购数量 */
	private Integer buyNum;
	
	/** 入库类型 1：正常入库 2：:冲正 */
	private String inboundType;
	
	private String inboundTypeName;	
	/**关联入库单*/
	private String linkWinboundId;	
	
	public String getLinkWinboundId() {
		return linkWinboundId;
	}

	public void setLinkWinboundId(String linkWinboundId) {
		this.linkWinboundId = linkWinboundId;
	}

	public String getInboundTypeName() {
		if("1".equals(inboundType)) {
			return "正常入库";
		}else if("2".equals(inboundType)) {
			return "冲正";
		}else {
			return "";
		}
	}

	public void setInboundTypeName(String inboundTypeName) {
		this.inboundTypeName = inboundTypeName;
	}

	public Integer getNopNum() {
		if (pnum == null || buyNum == null) {
			return 0;
		}
		return buyNum - pnum;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public String getInboundName() {
		if (StringUtils.isEmpty(inboundId)) {
			return "";
		}
		User user = SystemUtil.getUserByLoginId(corpId, inboundId);
		if (user == null) {
			return "";
		}
		return user.getUserName();
	}

	public String getCurStateName() {
		return SystemUtil.parseInputState(curState);
	}

	public String getBuyStateName() {
		return SystemUtil.parseInputState(buyState);
		//return SystemUtil.parseBuyState(buyState);
	}

	public String getBuyState() {
		return buyState;
	}

	public void setBuyState(String buyState) {
		this.buyState = buyState;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getInboundId() {
		return inboundId;
	}

	public void setInboundId(String inboundId) {
		this.inboundId = inboundId;
	}

	public void setStockId(String stockId) 
	{
		this.stockId = stockId;
	}

	public String getStockId() 
	{
		return stockId;
	}
	public void setStokcName(String stokcName) 
	{
		this.stokcName = stokcName;
	}

	public String getStokcName() 
	{
		return stokcName;
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

	
	
    public String getWinboundId() {
		return winboundId;
	}

	public void setWinboundId(String winboundId) {
		this.winboundId = winboundId;
	}

	public String getWpurchaseId() {
		return wpurchaseId;
	}

	public void setWpurchaseId(String wpurchaseId) {
		this.wpurchaseId = wpurchaseId;
	}

	public Integer getPnum() {
		return pnum;
	}

	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}

	public Integer getTnum() {
		return tnum;
	}

	public void setTnum(Integer tnum) {
		this.tnum = tnum;
	}

	public String getInboundType() {
		return inboundType;
	}

	public void setInboundType(String inboundType) {
		this.inboundType = inboundType;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("stockId", getStockId())
            .append("stokcName", getStokcName())
            .append("totalPrice", getTotalPrice())
            .append("curState", getCurState())
            .append("stateTime", getStateTime())
            .append("inboundId", getInboundId())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
