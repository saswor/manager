package com.manage.project.system.vendingPoint.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 点位的线路表 as_vending_line
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingLine extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 线路编号 */
	private String lineId;
	/** 线路编码，业务使用 */
	@Excel(name="线路编码",column="A")
	private String code; 
	/** 线路名称 */
	@Excel(name="线路名称",column="B")
	private String name;
	/** 区域编号 */
	@Excel(name="区域",isExport=false,column="C")
	private String districtId;
	/**区域名称*/
	@Excel(name="区域名称",isImport=false)
	private String districtName;
	/** 描述 */
	@Excel(name="描述",column="D")
	private String description;
	/** 创建时间 */
	@Excel(name="创建时间",column="E",isImport=false)
	private String createTime;
	/** 点位数量 */
	private Integer lineNum;
	/** 点位在线数量 */
	private Integer onlineNum;
	/** 托管公司编号 */
	private String corpId;
	/** 当前人商户id */
	private String cid;
	
	/**公司名称*/
	private String corpName;
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setLineId(String lineId) 
	{
		this.lineId = lineId;
	}

	public String getLineId() 
	{
		return lineId;
	}
	public void setDistrictId(String districtId) 
	{
		this.districtId = districtId;
	}

	public String getDistrictId() 
	{
		return districtId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}
	public void setLineNum(Integer lineNum) 
	{
		this.lineNum = lineNum;
	}

	public Integer getLineNum() 
	{
		return lineNum;
	}
	public void setOnlineNum(Integer onlineNum) 
	{
		this.onlineNum = onlineNum;
	}

	public Integer getOnlineNum() 
	{
		return onlineNum;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("lineId", getLineId())
            .append("code", getCode())
            .append("districtId", getDistrictId())
            .append("name", getName())
            .append("description", getDescription())
            .append("lineNum", getLineNum())
            .append("onlineNum", getOnlineNum())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
