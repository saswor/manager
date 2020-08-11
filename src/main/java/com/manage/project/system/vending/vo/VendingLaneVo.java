package com.manage.project.system.vending.vo;

/**
 * 货道Vo
 * @author xufeng
 *
 */
public class VendingLaneVo {
	private VendingLanepVo lanep;	// 货道商品
	
	/** 记录编号 */
	private String logid = "";
	/** 货道主键 */
	private String slaneId = "";
	/** 售货机编号 */
	private String siteId = "";
	/** 售货机名称 */
	private String siteName = "";
	/** 货道编号(从左到右、从上到下) */
	private Integer laneId = 0;
	/** 货柜类型 01:商店机 02:弹簧机 03:格子机 */
	private String cabinetType = "";
	/** 机型 */
	private String deviceId = "";
	/** 货道编号，根据柜型用户输入的货道起始编号开始递增 */
	private Integer laneCode = 0;
	/** 行数 */
	private Integer row;
	/** 列数 */
	private Integer col;
	/** 排数 */
	private Integer arrange;
	/** 货道状态 */
	private String curState = "1";
	/** 状态时间 */
	private String stateTime;
	/** 托管公司编号 */
	private String corpId = "";
	/**存放容量*/
	private Integer capacity;
	/**阈值*/
	private Integer warnCap;
	/**
	 * 货柜id
	 */
	private String cabinetId = "";
	
	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getWarnCap() {
		return warnCap;
	}

	public void setWarnCap(Integer warnCap) {
		this.warnCap = warnCap;
	}

	public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSlaneId(String slaneId) 
	{
		this.slaneId = slaneId;
	}

	public String getSlaneId() 
	{
		return slaneId;
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
	public void setLaneId(Integer laneId) 
	{
		this.laneId = laneId;
	}

	public Integer getLaneId() 
	{
		return laneId;
	}
	public void setCabinetType(String cabinetType) 
	{
		this.cabinetType = cabinetType;
	}

	public String getCabinetType() 
	{
		return cabinetType;
	}
	public void setDeviceId(String deviceId) 
	{
		this.deviceId = deviceId;
	}

	public String getDeviceId() 
	{
		return deviceId;
	}
	public void setLaneCode(Integer laneCode) 
	{
		this.laneCode = laneCode;
	}

	public Integer getLaneCode() 
	{
		return laneCode;
	}
	public void setRow(Integer row) 
	{
		this.row = row;
	}

	public Integer getRow() 
	{
		return row;
	}
	public void setCol(Integer col) 
	{
		this.col = col;
	}

	public Integer getCol() 
	{
		return col;
	}
	public void setArrange(Integer arrange) 
	{
		this.arrange = arrange;
	}

	public Integer getArrange() 
	{
		return arrange;
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

	public VendingLanepVo getLanep() {
		return lanep;
	}

	public void setLanep(VendingLanepVo lanep) {
		this.lanep = lanep;
	}

	
	
}
