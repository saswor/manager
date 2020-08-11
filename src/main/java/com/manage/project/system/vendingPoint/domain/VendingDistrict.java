package com.manage.project.system.vendingPoint.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.util.SystemUtil;

/**
 * 管理线路的区域表 as_vending_district
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingDistrict extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 区域编号 */
	private String districtId;
	/** 区域编码 */
	@Excel(name="区域编码",column="A")
	private String code;
	/** 区域名称 */
	@Excel(name="区域名称",column="B")
	private String name;
	/** 负责人名称 */
	@Excel(name="负责人姓名",column="C")
	private String manager;
	/** 负责人联系方式 */
	@Excel(name="联系方式",column="D")
	private String mobile;
	/** 描述 */
	@Excel(name="描述",column="E")
	private String description;
	/** 托管公司编号 */
	private String corpId;
	/**商户名称*/
	@Excel(name="商户名称",column="F",isImport=false)
	private String corpName;
	/**创建时间*/
	@Excel(name="创建时间",column="G",isImport=false)
	private String createTime;
	/**
	 * 当前操作用户所在商户id
	 */
	private String cid;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
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
	public void setCode(String code) 
	{
		this.code = code;
	}

	public String getCode() 
	{
		return code;
	}
	public void setManager(String manager) 
	{
		this.manager = manager;
	}

	public String getManager() 
	{
		return manager;
	}
	public void setMobile(String mobile) 
	{
		this.mobile = mobile;
	}

	public String getMobile() 
	{
		return mobile;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

    public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCorpName() {
		Corp c = SystemUtil.getCorpById(corpId);
		if (c == null) {
			return "";
		}
		return c.getCorpName();
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("districtId", getDistrictId())
            .append("name", getName())
            .append("code", getCode())
            .append("manager", getManager())
            .append("mobile", getMobile())
            .append("description", getDescription())
            .append("createTime", getCreateTime())
            .append("corpId", getCorpId())
            .toString();
    }
}
