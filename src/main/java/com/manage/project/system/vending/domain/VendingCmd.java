package com.manage.project.system.vending.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.common.Constant;

/**
 * 售货机命令，按站点队列执行表 as_vending_cmd
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public class VendingCmd extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 命令编号 */
	private String cmdId;
	/** 命令对象编号 售货机编号、商品编号、订单编号 */
	private String cmdCode;
	/** 命令类型 01:售货机 02:商品 03: 订单 */
	private String cmdType;
	/** 命令及时性 1:即时 2:可队列 */
	private String cmdLazy;
	/** 命令类型 */
	private String cmd;
	/** 命令内容 */
	private String cont;
	/** 创建时间 */
	private String createTime;
	/** 执行状态0:执行中 1:执行成功 2:执行失败 */
	private String state;
	/** 状态时间 */
	private String stateTime;
	/** 执行结果 */
	private String result;
	/** 托管公司编号 */
	private String corpId;
	/**命令名称*/
	private String cmdName;
	/**状态名称*/
	private String stateName;

	public String getCmdName() {
		String result=Constant.cmdMap.get(cmd);
		if(result!=null) {
			return result;
		}else {
			return cmdName;
		}
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	public String getStateName() {
		if(Constant.VENDING_CMD_STATE_EXCUTING.equals(state)) {
			return "执行中";
		}else if(Constant.VENDING_CMD_STATE_SUCCESS.equals(state)) {
			return "执行成功";
		}else if(Constant.VENDING_CMD_STATE_FAILED.equals(state)) {
			return "执行失败";
		}else {
			return stateName;
		}
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setCmdId(String cmdId) 
	{
		this.cmdId = cmdId;
	}

	public String getCmdId() 
	{
		return cmdId;
	}
	public void setCmdCode(String cmdCode) 
	{
		this.cmdCode = cmdCode;
	}

	public String getCmdCode() 
	{
		return cmdCode;
	}
	public void setCmdType(String cmdType) 
	{
		this.cmdType = cmdType;
	}

	public String getCmdType() 
	{
		return cmdType;
	}
	public void setCmdLazy(String cmdLazy) 
	{
		this.cmdLazy = cmdLazy;
	}

	public String getCmdLazy() 
	{
		return cmdLazy;
	}
	public void setCmd(String cmd) 
	{
		this.cmd = cmd;
	}

	public String getCmd() 
	{
		return cmd;
	}
	public void setCont(String cont) 
	{
		this.cont = cont;
	}

	public String getCont() 
	{
		return cont;
	}
	public void setCreateTime(String createTime) 
	{
		this.createTime = createTime;
	}

	public String getCreateTime() 
	{
		return createTime;
	}
	public void setState(String state) 
	{
		this.state = state;
	}

	public String getState() 
	{
		return state;
	}
	public void setStateTime(String stateTime) 
	{
		this.stateTime = stateTime;
	}

	public String getStateTime() 
	{
		return stateTime;
	}
	public void setResult(String result) 
	{
		this.result = result;
	}

	public String getResult() 
	{
		return result;
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
            .append("cmdId", getCmdId())
            .append("cmdCode", getCmdCode())
            .append("cmdType", getCmdType())
            .append("cmdLazy", getCmdLazy())
            .append("cmd", getCmd())
            .append("cont", getCont())
            .append("createTime", getCreateTime())
            .append("state", getState())
            .append("stateTime", getStateTime())
            .append("result", getResult())
            .append("corpId", getCorpId())
            .toString();
    }
}
