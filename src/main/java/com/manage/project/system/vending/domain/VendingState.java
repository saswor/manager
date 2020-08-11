package com.manage.project.system.vending.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.common.utils.StringUtils;
import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.common.Constant;

/**
 * 保存设备的状态，由终端设备上报，通过设备可远程控制设备的系统重启、应用重启、配置获取、配置下发。表 as_vending_state
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class VendingState extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 售货机编号 */
	@Excel(name="售货机编号")
	private String siteId;
	/** 售货机名称 */
	@Excel(name="售货机名称")
	private String siteName;
	/** 设备序列号 */
	@Excel(name="设备序列号")
	private String seqId;
	/** 固件版本 */
	@Excel(name="固件版本")
	private String vFirmware;
	/** 视频监控软件版本 */
	@Excel(name="VCS版本")
	private String vVCS;
	/** Ip:端口 */
	private String ipAddress;
	/** 信号值 */
	@Excel(name="信号值")
	private Integer signalValue;
	/** sim卡号 */
	@Excel(name="ICCID")
	private String iccid;
	/** 连接时长(分钟) */
	private Integer contime;
	/** 离线时长(分钟) */
	private Integer loseContime;
	/** 分辨率 */
	@Excel(name="分辨率")
	private String resoution;
	/** 屏幕类型 1:横屏 2:竖屏 */
	private String screenType;
	/** 安卓版本 */
	private String vAndroid;
	/** 基带版本 */
	@Excel(name="基带版本")
	private String vBaseband;
	/** 上报时间 */
	private String reportTime;
	/** 网络状态(0:在线 1:离线) */
	private String netSate;
	/** 托管公司编号 */
	private String corpId;
	
	@Excel(name="网络状态名称")
	private String netSateName;
	
	private String site;
	/**平台类型*/
	private String platType;
	@Excel(name="平台类型")
	private String platTypeName;
	
	/** 屏幕类型 1:横屏 2:竖屏 */
	@Excel(name="屏幕类型")
	private String screenTypeName;
	
	public String getScreenTypeName() {
		if(StringUtils.isNotEmpty(screenTypeName)) {
			return screenTypeName;
		}else if("1".equals(screenType)){
			return "横屏";
		}else if("2".equals(screenType)){
			return "竖屏";
		}else {
			return "";
		}
	}

	public void setScreenTypeName(String screenTypeName) {
		this.screenTypeName = screenTypeName;
	}

	public String getPlatTypeName() {
		if(Constant.PLATTYPE_ANDROID.equals(platType)) {
			return "安卓";
		}else if(Constant.PLATTYPE_WINDOWS.equals(platType)) {
			return "windows";
		}else if(Constant.PLATTYPE_SINGLECHIP.equals(platType)) {
			return "单片机";
		}else if(Constant.PLATTYPE_BASE.equals(platType)) {
			return "基本款";
		}else {
			return platType;
		}
	}
	
	public void setPlatTypeName(String platTypeName) {
		this.platTypeName = platTypeName;
	}
	
	public String getPlatType() {
		return platType;
	}

	public void setPlatType(String platType) {
		this.platType = platType;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getNetSateName() {
		if("0".equals(netSate)) {
			return "在线";
		}
		if("1".equals(netSate)) {
			return "离线";
		}else {
			return netSateName;
		}
	}

	public void setNetSateName(String netSateName) {
		this.netSateName = netSateName;
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
	public void setSeqId(String seqId) 
	{
		this.seqId = seqId;
	}

	public String getSeqId() 
	{
		return seqId;
	}
	public void setVFirmware(String vFirmware) 
	{
		this.vFirmware = vFirmware;
	}

	public String getVFirmware() 
	{
		return vFirmware;
	}
	public void setVVCS(String vVCS) 
	{
		this.vVCS = vVCS;
	}

	public String getVVCS() 
	{
		return vVCS;
	}
	public void setIpAddress(String ipAddress) 
	{
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() 
	{
		return ipAddress;
	}
	public void setSignalValue(Integer signalValue) 
	{
		this.signalValue = signalValue;
	}

	public Integer getSignalValue() 
	{
		return signalValue;
	}
	public void setIccid(String iccid) 
	{
		this.iccid = iccid;
	}

	public String getIccid() 
	{
		return iccid;
	}
	public void setContime(Integer contime) 
	{
		this.contime = contime;
	}

	public Integer getContime() 
	{
		return contime;
	}
	public void setLoseContime(Integer loseContime) 
	{
		this.loseContime = loseContime;
	}

	public Integer getLoseContime() 
	{
		return loseContime;
	}
	public void setResoution(String resoution) 
	{
		this.resoution = resoution;
	}

	public String getResoution() 
	{
		return resoution;
	}
	public void setScreenType(String screenType) 
	{
		this.screenType = screenType;
	}

	public String getScreenType() 
	{
		return screenType;
	}
	public void setVAndroid(String vAndroid) 
	{
		this.vAndroid = vAndroid;
	}

	public String getVAndroid() 
	{
		return vAndroid;
	}
	public void setVBaseband(String vBaseband) 
	{
		this.vBaseband = vBaseband;
	}

	public String getVBaseband() 
	{
		if("no message".equals(vBaseband)) {
			return "";
		}else {
			return vBaseband;
		}
	}
	public void setReportTime(String reportTime) 
	{
		this.reportTime = reportTime;
	}

	public String getReportTime() 
	{
		return reportTime;
	}
	public void setNetSate(String netSate) 
	{
		this.netSate = netSate;
	}

	public String getNetSate() 
	{
		return netSate;
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
            .append("seqId", getSeqId())
            .append("vFirmware", getVFirmware())
            .append("vVCS", getVVCS())
            .append("ipAddress", getIpAddress())
            .append("signalValue", getSignalValue())
            .append("iccid", getIccid())
            .append("contime", getContime())
            .append("loseContime", getLoseContime())
            .append("resoution", getResoution())
            .append("screenType", getScreenType())
            .append("vAndroid", getVAndroid())
            .append("vBaseband", getVBaseband())
            .append("reportTime", getReportTime())
            .append("netSate", getNetSate())
            .append("corpId", getCorpId())
            .toString();
    }
}
