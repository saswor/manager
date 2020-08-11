package com.manage.project.system.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。表 as_corp
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class Corp extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 商户编号 */
	@Excel(name="商户编号")
	private String corpId;
	/** 商户名称 */
	@Excel(name="商户名称")
	private String corpName;
	/** 详细地址 */
	@Excel(name="详细地址")
	private String address;
	/** 服务电话 */
	@Excel(name="服务电话")
	private String tel;
	/** 负责人 */
	@Excel(name="负责人")
	private String leader;
	/** 负责人电话 */
	@Excel(name="负责人电话")
	private String leaderMobile;
	/** 状态 */
	private String curState;
	/**  */
	private String stateTime;
	@Excel(name="状态")
	private String curStateName;
	@Excel(name="创建时间")
	private String createTime;
	
	public String getCurStateName() {
		if("1".equals(curState)) {
			return "正常";
		}
		if("2".equals(curState)) {
			return "停用";
		}
		return "";
		
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setCurStateName(String curStateName) {
		this.curStateName = curStateName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setCorpName(String corpName) 
	{
		this.corpName = corpName;
	}

	public String getCorpName() 
	{
		return corpName;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getAddress() 
	{
		return address;
	}
	public void setTel(String tel) 
	{
		this.tel = tel;
	}

	public String getTel() 
	{
		return tel;
	}
	public void setLeader(String leader) 
	{
		this.leader = leader;
	}

	public String getLeader() 
	{
		return leader;
	}
	public void setLeaderMobile(String leaderMobile) 
	{
		this.leaderMobile = leaderMobile;
	}

	public String getLeaderMobile() 
	{
		return leaderMobile;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("corpId", getCorpId())
            .append("corpName", getCorpName())
            .append("address", getAddress())
            .append("tel", getTel())
            .append("leader", getLeader())
            .append("leaderMobile", getLeaderMobile())
            .append("curState", getCurState())
            .append("stateTime", getStateTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
