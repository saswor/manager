package com.manage.project.system.supply.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。表 as_supply_config
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class SupplyConfig extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 补货配置编号 */
	private String supplyId;
	/** 托管公司编号 */
	private String corpId;
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
	/** 补货类型 1:全品补齐 2:阈值补齐 */
	private String type;
	/** 提前生成补货单时间 */
	private Integer advTime;
	/** 生成完补货单到补货完成时间段 */
	private Integer finshTime;
	/** 待处理的补货单数量 */
	private Integer pendingNum;
	/** 已完成补货单的数量 */
	private Integer finshNum;
	/** 已补货总数量 */
	private Integer supplyPnum;
	/** 已补商品总数 */
	private Integer sproductNum;
	/** 生成补货单策略 Json */
	private String strategy;
	/** 当前商品容量 */
	private Integer curPNum;
	/** 商品最大存放量 */
	private Integer maxPNum;
	/** 库存等级 1,2,3 */
	private Integer storyLevel;
	/** 第一等级库存 最小到最大百分比 */
	private String fristlevel;
	/** 第二等级库存 最小到最大百分比 */
	private String twolevel;
	/** 第三等级库存 最小到最大百分比 */
	private String threelevel;

	/** 待补货商品数量**/
	private Integer waitSPNum;
	/** 待补货站点数量**/
	private Integer waitSVNum;
	/** 下次补货时间**/
	private String lastSTime;
	/** 每月补货次数**/
	private Integer everyTime;
	/** 描述**/
	private String description;

	private String supplyName;
	/** 开始时间**/
	private String startTime;
	/** 结束时间**/
	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getEveryTime() {
		return everyTime;
	}

	public void setEveryTime(Integer everyTime) {
		this.everyTime = everyTime;
	}

	public void setLogid(String logid)
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSupplyId(String supplyId) 
	{
		this.supplyId = supplyId;
	}

	public String getSupplyId() 
	{
		return supplyId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
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
	public void setAdvTime(Integer advTime) 
	{
		this.advTime = advTime;
	}

	public Integer getAdvTime() 
	{
		return advTime;
	}
	public void setFinshTime(Integer finshTime) 
	{
		this.finshTime = finshTime;
	}

	public Integer getFinshTime() 
	{
		return finshTime;
	}
	public void setPendingNum(Integer pendingNum) 
	{
		this.pendingNum = pendingNum;
	}

	public Integer getPendingNum() 
	{
		return pendingNum;
	}
	public void setFinshNum(Integer finshNum) 
	{
		this.finshNum = finshNum;
	}

	public Integer getFinshNum() 
	{
		return finshNum;
	}
	public void setSupplyPnum(Integer supplyPnum) 
	{
		this.supplyPnum = supplyPnum;
	}

	public Integer getSupplyPnum() 
	{
		return supplyPnum;
	}
	public void setSproductNum(Integer sproductNum) 
	{
		this.sproductNum = sproductNum;
	}

	public Integer getSproductNum() 
	{
		return sproductNum;
	}
	public void setStrategy(String strategy) 
	{
		this.strategy = strategy;
	}

	public String getStrategy() 
	{
		return strategy;
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
	public void setStoryLevel(Integer storyLevel) 
	{
		this.storyLevel = storyLevel;
	}

	public Integer getStoryLevel() 
	{
		return storyLevel;
	}
	public void setFristlevel(String fristlevel) 
	{
		this.fristlevel = fristlevel;
	}

	public String getFristlevel() 
	{
		return fristlevel;
	}
	public void setTwolevel(String twolevel) 
	{
		this.twolevel = twolevel;
	}

	public String getTwolevel() 
	{
		return twolevel;
	}
	public void setThreelevel(String threelevel) 
	{
		this.threelevel = threelevel;
	}

	public String getThreelevel() 
	{
		return threelevel;
	}

	public Integer getWaitSPNum() {
		return waitSPNum;
	}

	public void setWaitSPNum(Integer waitSPNum) {
		this.waitSPNum = waitSPNum;
	}

	public Integer getWaitSVNum() {
		return waitSVNum;
	}

	public void setWaitSVNum(Integer waitSVNum) {
		this.waitSVNum = waitSVNum;
	}

	public String getLastSTime() {
		return lastSTime;
	}

	public void setLastSTime(String lastSTime) {
		this.lastSTime = lastSTime;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("supplyId", getSupplyId())
            .append("corpId", getCorpId())
            .append("name", getName())
            .append("lineId", getLineId())
            .append("wmId", getWmId())
            .append("supplierId", getSupplierId())
            .append("num", getNum())
            .append("type", getType())
            .append("advTime", getAdvTime())
            .append("finshTime", getFinshTime())
            .append("pendingNum", getPendingNum())
            .append("finshNum", getFinshNum())
            .append("supplyPnum", getSupplyPnum())
            .append("sproductNum", getSproductNum())
            .append("strategy", getStrategy())
            .append("curPNum", getCurPNum())
            .append("maxPNum", getMaxPNum())
            .append("storyLevel", getStoryLevel())
            .append("fristlevel", getFristlevel())
            .append("twolevel", getTwolevel())
            .append("threelevel", getThreelevel())
            .append("createTime", getCreateTime())
				.append("waitSPNum",getWaitSPNum())
				.append("waitSVNum", getWaitSVNum())
				.append("lastSTime", getLastSTime())

            .toString();
    }

}
