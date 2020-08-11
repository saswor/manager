package com.manage.project.system.vending.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.common.Constant;

/**
 * 具体售货机的升级任务列表 as_vending_upgrade_task
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class VendingUpgradeTask extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/**升级任务编号*/
	private String taskId;
	/** 售货机编号 */
	private String siteId;
	/** 售货机名称 */
	private String siteName;
	/** 升级编号 */
	private String upgradeId;
	/** 升级状态[01:准备升级 02:正在升级 03:升级成功 04:升级失败] */
	private String state;
	/** 创建时间 */
	private String createTime;
	/** 托管公司编号 */
	private String corpId;
	
	private String uName;
	
	private String lineName;
	
	private String seqId;
	
	private String factoryName;
	
	private Integer signalValue;
	
	private String netSateName;
	
	private String site;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getNetSateName() {
		return netSateName;
	}

	public void setNetSateName(String netSateName) {
		this.netSateName = netSateName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public Integer getSignalValue() {
		return signalValue;
	}

	public void setSignalValue(Integer signalValue) {
		this.signalValue = signalValue;
	}

	public String getStateName() {
		if(Constant.UPGRADE_TASK_STATE_WAIT.equals(state)) {
			return "准备升级";
		}else if(Constant.UPGRADE_TASK_STATE_UPDATING.equals(state)) {
			return "正在升级";
		}else if(Constant.UPGRADE_TASK_STATE_SUCCESS.equals(state)) {
			return "升级成功";
		}else if(Constant.UPGRADE_TASK_STATE_FAILED.equals(state)) {
			return "升级失败";
		}else {
			return "";
		}
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
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
	public void setUpgradeId(String upgradeId) 
	{
		this.upgradeId = upgradeId;
	}

	public String getUpgradeId() 
	{
		return upgradeId;
	}
	public void setState(String state) 
	{
		this.state = state;
	}

	public String getState() 
	{
		return state;
	}
	public void setCreateTime(String createTime) 
	{
		this.createTime = createTime;
	}

	public String getCreateTime() 
	{
		return createTime;
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
            .append("siteId", getSiteId())
            .append("siteName", getSiteName())
            .append("upgradeId", getUpgradeId())
            .append("state", getState())
            .append("createTime", getCreateTime())
            .append("corpId", getCorpId())
            .toString();
    }
}
