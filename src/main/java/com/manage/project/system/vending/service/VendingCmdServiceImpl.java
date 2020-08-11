package com.manage.project.system.vending.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.common.constant.Constants;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.server.domain.ServerMessage;
import com.manage.project.system.server.service.IServerService;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingCmd;
import com.manage.project.system.vending.mapper.VendingCmdMapper;

/**
 * 售货机命令，按站点队列执行 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class VendingCmdServiceImpl implements IVendingCmdService 
{
	@Autowired
	private VendingCmdMapper vendingCmdMapper;
	
	@Autowired
	private IServerService serverService;
	
	private Logger log = LoggerFactory.getLogger(VendingCmdServiceImpl.class);

	/**
     * 查询售货机命令，按站点队列执行信息
     * 
     * @param logid 售货机命令，按站点队列执行ID
     * @return 售货机命令，按站点队列执行信息
     */
    @Override
	public VendingCmd selectVendingCmdById(String logid)
	{
	    return vendingCmdMapper.selectVendingCmdById(logid);
	}
	
	/**
     * 查询售货机命令，按站点队列执行列表
     * 
     * @param vendingCmd 售货机命令，按站点队列执行信息
     * @return 售货机命令，按站点队列执行集合
     */
	@Override
	public List<VendingCmd> selectVendingCmdList(VendingCmd vendingCmd)
	{
	    return vendingCmdMapper.selectVendingCmdList(vendingCmd);
	}
	
    /**
     * 新增售货机命令，按站点队列执行
     * 
     * @param vendingCmd 售货机命令，按站点队列执行信息
     * @return 结果
     */
	@Override
	public int insertVendingCmd(VendingCmd vendingCmd)
	{
		VendingCmd vendingCmdSelect = new VendingCmd();
		vendingCmdSelect.setCmdCode(vendingCmd.getCmdCode());
		vendingCmdSelect.setCmd(vendingCmd.getCmd());
		vendingCmdSelect.setCmdType(Constant.VENDING_CMD_TYPE_VENDING);
		vendingCmdSelect.setState(Constant.VENDING_CMD_STATE_EXCUTING);
		List<VendingCmd> vendingCmdList = vendingCmdMapper.selectVendingCmdList(vendingCmdSelect);
	    return vendingCmdMapper.insertVendingCmd(vendingCmd);
	}
	
	/**
     * 修改售货机命令，按站点队列执行
     * 
     * @param vendingCmd 售货机命令，按站点队列执行信息
     * @return 结果
     */
	@Override
	public int updateVendingCmd(VendingCmd vendingCmd)
	{
	    return vendingCmdMapper.updateVendingCmd(vendingCmd);
	}

	/**
     * 删除售货机命令，按站点队列执行对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingCmdByIds(String ids)
	{
		return vendingCmdMapper.deleteVendingCmdByIds(Convert.toStrArray(ids));
	}

	/**
     * 新增售货机命令，按站点队列执行
     * 
     * @param siteId 售货机编号
     * @param vendingCmd 售货机命令，按站点队列执行信息
     * @return 结果
     */
	@Override
	public int insertVendingCmdQuick(String siteId,VendingCmd vendingCmd,String corpId) {
		vendingCmd.setLogid(UUID.randomUUID().toString());
		vendingCmd.setCmdId(SystemUtil.getSeqId(corpId, "as_vending_cmd"));
		vendingCmd.setCmdLazy("1");
		vendingCmd.setCreateTime(DateUtils.getTime());
		vendingCmd.setState(Constant.VENDING_CMD_STATE_EXCUTING);
		vendingCmd.setStateTime("");
		vendingCmd.setResult("");
		vendingCmd.setCorpId(corpId);
		vendingCmdMapper.insertVendingCmd(vendingCmd);
		String result = serverService.vendingCommand1227(siteId, vendingCmd);
		if("0000".equals(result)) {
			return 1;
		}else {
			log.error("通知消息服务器失败,状态码:"+result);
			return 2;
		}
	}
	
}
