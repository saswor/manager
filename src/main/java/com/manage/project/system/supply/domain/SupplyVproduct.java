package com.manage.project.system.supply.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 补货站点需要补货的货道商品，根据流水可检查商品的过期时间表 as_supply_vproduct
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class SupplyVproduct extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 售货机补货商品编号 */
	private String sVendingId;
	/** 补货单编号 */
	private String sOrderId;
	/** 补货站点编号 */
	private String vOrderId;
	/** 补货商品编号 */
	private String sProductId;
	/** 公司编号 */
	private String corpId;
	/** 区域编号 */
	private String districtId;
	/** 线路编号 */
	private String lineId;
	/** 点位编号 */
	private String pointId;
	/** 售货机编号 */
	private String siteId;
	/** 开始货道编号 */
	private Integer laneSId;
	/** 结束货道编号 */
	private Integer laneEId;
	/** 商品编号 */
	private String productId;
	/** 补货数量 */
	private Integer supplyNum;
	/** 实补数量 */
	private Integer rSupplyNum;
	/** 已售卖数量 */
	private Integer saleNum;
	/** 是否已出柜 1:未出柜 2:已出柜 */
	private String finshState;
	/** 补货状态 1:等待补货 2:补货完成 */
	private String curState;
	/** 补货状态变化时间 */
	private String stateTime;
	/** 商品过期时间 */
	private String invalidTime;
	/** 商品是否过期 1:是 2:否 */
	private String invalidState;
	/** 采购单价 */
	private Float buyPrice;
	
	public String getVOrderId() {
		return vOrderId;
	}
	
	public String getvOrderId() {
		return vOrderId;
	}

	public void setvOrderId(String vOrderId) {
		this.vOrderId = vOrderId;
	}
	
	public void setVOrderId(String vOrderId) {
		this.vOrderId = vOrderId;
	}

	public String getSProductId() {
		return sProductId;
	}
	
	public String getsProductId() {
		return sProductId;
	}

	public void setsProductId(String sProductId) {
		this.sProductId = sProductId;
	}
	
	public void setSProductId(String sProductId) {
		this.sProductId = sProductId;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSVendingId(String sVendingId) 
	{
		this.sVendingId = sVendingId;
	}

	public String getSVendingId() 
	{
		return sVendingId;
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
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setSupplyNum(Integer supplyNum) 
	{
		this.supplyNum = supplyNum;
	}

	public Integer getSupplyNum() 
	{
		return supplyNum;
	}
	public void setRSupplyNum(Integer rSupplyNum) 
	{
		this.rSupplyNum = rSupplyNum;
	}

	public Integer getRSupplyNum() 
	{
		return rSupplyNum;
	}
	public void setSaleNum(Integer saleNum) 
	{
		this.saleNum = saleNum;
	}

	public Integer getSaleNum() 
	{
		return saleNum;
	}
	public void setFinshState(String finshState) 
	{
		this.finshState = finshState;
	}

	public String getFinshState() 
	{
		return finshState;
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
	public void setInvalidTime(String invalidTime) 
	{
		this.invalidTime = invalidTime;
	}

	public String getInvalidTime() 
	{
		return invalidTime;
	}
	public void setInvalidState(String invalidState) 
	{
		this.invalidState = invalidState;
	}

	public String getInvalidState() 
	{
		return invalidState;
	}
	public void setBuyPrice(Float buyPrice) 
	{
		this.buyPrice = buyPrice;
	}

	public Float getBuyPrice() 
	{
		return buyPrice;
	}




    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("sVendingId", getSVendingId())
            .append("sOrderId", getSOrderId())
            .append("corpId", getCorpId())
            .append("districtId", getDistrictId())
            .append("lineId", getLineId())
            .append("pointId", getPointId())
            .append("siteId", getSiteId())
            .append("laneSId", getLaneSId())
            .append("laneEId", getLaneEId())
            .append("productId", getProductId())
            .append("supplyNum", getSupplyNum())
            .append("rSupplyNum", getRSupplyNum())
            .append("saleNum", getSaleNum())
            .append("finshState", getFinshState())
            .append("curState", getCurState())
            .append("stateTime", getStateTime())
            .append("invalidTime", getInvalidTime())
            .append("invalidState", getInvalidState())
            .append("buyPrice", getBuyPrice())
            .append("createTime", getCreateTime())
            .toString();
    }
}
