package com.manage.project.system.vending.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.common.exception.vending.CmdSendException;
import com.manage.common.exception.vending.VendingNotExitException;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingCmd;
import com.manage.project.system.vending.domain.VendingUpgrade;
import com.manage.project.system.vending.domain.VendingUpgradeTask;
import com.manage.project.system.vending.mapper.VendingMapper;
import com.manage.project.system.vending.mapper.VendingUpgradeMapper;
import com.manage.project.system.vending.mapper.VendingUpgradeTaskMapper;

/**
 * 控制设备的升级，升级包括app升级、固件升级、视频软件升级 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class VendingUpgradeServiceImpl implements IVendingUpgradeService 
{
	@Autowired
	private VendingUpgradeMapper vendingUpgradeMapper;
	
	@Autowired
	private VendingMapper vendingMapper;
	
	@Autowired
	private VendingUpgradeTaskMapper vendingUpgradeTaskMapper;
	
	@Autowired
	private IVendingCmdService vendingCmdService;

	/**
     * 查询控制设备的升级，升级包括app升级、固件升级、视频软件升级信息
     * 
     * @param vendingUpgradeId 控制设备的升级，升级包括app升级、固件升级、视频软件升级ID
     * @return 控制设备的升级，升级包括app升级、固件升级、视频软件升级信息
     */
    @Override
	public VendingUpgrade selectVendingUpgradeById(String vendingUpgradeId)
	{
	    return vendingUpgradeMapper.selectVendingUpgradeById(vendingUpgradeId);
	}
	
	/**
     * 查询控制设备的升级，升级包括app升级、固件升级、视频软件升级列表
     * 
     * @param vendingUpgrade 控制设备的升级，升级包括app升级、固件升级、视频软件升级信息
     * @return 控制设备的升级，升级包括app升级、固件升级、视频软件升级集合
     */
	@Override
	public List<VendingUpgrade> selectVendingUpgradeList(VendingUpgrade vendingUpgrade)
	{
	    return vendingUpgradeMapper.selectVendingUpgradeList(vendingUpgrade);
	}
	
    /**
     * 新增控制设备的升级，升级包括app升级、固件升级、视频软件升级
     * 
     * @param vendingUpgrade 控制设备的升级，升级包括app升级、固件升级、视频软件升级信息
     * @return 结果
     */
	@Override
	@Transactional
	public int insertVendingUpgrade(VendingUpgrade vendingUpgrade)
	{
		//数据校验
		int checkResult = checkVendingUpgrade(vendingUpgrade);
		if(checkResult!=1) {
			return checkResult;
		}
		String corpId=ShiroUtils.getCorpId();
		String upgradeId = SystemUtil.getSeqId(corpId, "as_vending_upgrade");
		vendingUpgrade.setLogid(UUID.randomUUID().toString());
		vendingUpgrade.setUpgradeId(upgradeId);
		if(Constant.UPGRADE_ISSUEDTYPE_NOW.equals(vendingUpgrade.getIssuedType())) {
			vendingUpgrade.setPlanTime(DateUtils.getTime());
		}
		vendingUpgrade.setCorpId(corpId);
		vendingUpgrade.setFactoryId("");
		vendingUpgrade.setDescription("");
		vendingUpgrade.setCreateTime(DateUtils.getTime());
		vendingUpgrade.setIsPush(Constant.IS_PUSH_FALSE);
		List<VendingUpgradeTask> vendingUpgradeTaskList = vendingUpgrade.getVendingUpgradeTaskList();
		for (VendingUpgradeTask vendingUpgradeTask : vendingUpgradeTaskList) {
			vendingUpgradeTask.setLogid(UUID.randomUUID().toString());
			vendingUpgradeTask.setTaskId(SystemUtil.getSeqId(corpId, "as_vending_upgrade_task"));
			String siteId = vendingUpgradeTask.getSiteId();
			Vending vending = SystemUtil.getVendingBase(siteId);
			if(vending==null) {
				throw new VendingNotExitException();
			}else {
				vendingUpgradeTask.setSiteName(vending.getSiteName());
			}			
			vendingUpgradeTask.setUpgradeId(upgradeId);
			vendingUpgradeTask.setState(Constant.UPGRADE_TASK_STATE_WAIT);
			vendingUpgradeTask.setCreateTime(DateUtils.getTime());
			vendingUpgradeTask.setCorpId(corpId);
			vendingUpgradeTaskMapper.insertVendingUpgradeTask(vendingUpgradeTask);
		}
	    vendingUpgradeMapper.insertVendingUpgrade(vendingUpgrade);
	    //立即投放通知终端
	    if(Constant.UPGRADE_ISSUEDTYPE_NOW.equals(vendingUpgrade.getIssuedType())) {
			int result = insertVendingCmd(vendingUpgrade);
			if(result!=1) {
				throw new CmdSendException();
			}
		}
	    return 1;
	}
	
	/**
	 * 通知终端升级指令
	 * 
	 * @param vendingUpgrade
	 * @return
	 */
	public int insertVendingCmd(VendingUpgrade vendingUpgrade) {
		List<VendingUpgradeTask> vendingUpgradeTaskList = vendingUpgrade.getVendingUpgradeTaskList();
		for (VendingUpgradeTask vendingUpgradeTask : vendingUpgradeTaskList) {
			if(Constant.UPGRADE_TASK_STATE_WAIT.equals(vendingUpgradeTask.getState())) {
				VendingCmd vendingCmd = new VendingCmd();
				vendingCmd.setCmdCode(vendingUpgradeTask.getSiteId());
				vendingCmd.setCmdType(Constant.VENDING_CMD_TYPE_VENDING);
				vendingCmd.setCmd("0106");
				vendingCmd.setCont("");
//				vendingUpgradeTask.setState(Constant.UPGRADE_TASK_STATE_UPDATING);
//				vendingUpgradeTaskMapper.updateVendingUpgradeTask(vendingUpgradeTask);
				int result = vendingCmdService.insertVendingCmdQuick(vendingUpgradeTask.getSiteId(),vendingCmd,vendingUpgrade.getCorpId());
				if(result!=1) {
					return result;
				}
			}else {
				continue;
			}
		}
		vendingUpgrade.setIsPush(Constant.IS_PUSH_TRUE);
		vendingUpgradeMapper.updateVendingUpgrade(vendingUpgrade);
		return 1;
	}

	/**
     * 修改控制设备的升级，升级包括app升级、固件升级、视频软件升级
     * 
     * @param vendingUpgrade 控制设备的升级，升级包括app升级、固件升级、视频软件升级信息
     * @return 结果
     */
	@Override
	@Transactional
	public int updateVendingUpgrade(VendingUpgrade vendingUpgrade)
	{
		//查询原纪录是否已经推送终端
		VendingUpgrade srcVendingUpgrade = vendingUpgradeMapper.selectVendingUpgradeById(vendingUpgrade.getUpgradeId());
		if(Constant.UPGRADE_ISSUEDTYPE_NOW.equals(srcVendingUpgrade.getIssuedType())) {
			return 10;
		}else {
			if(srcVendingUpgrade.getPlanTime().compareTo(DateUtils.getTime())<=0) {
				return 10;
			}
		}
		//数据校验
		int checkResult = checkVendingUpgrade(vendingUpgrade);
		if(checkResult!=1) {
			return checkResult;
		}
		String corpId=vendingUpgrade.getCorpId();
		String upgradeId = vendingUpgrade.getUpgradeId();
		if(Constant.UPGRADE_ISSUEDTYPE_NOW.equals(vendingUpgrade.getIssuedType())) {
			vendingUpgrade.setPlanTime(DateUtils.getTime());
		}
		List<VendingUpgradeTask> vendingUpgradeTaskList = vendingUpgrade.getVendingUpgradeTaskList();
		vendingUpgradeTaskMapper.deleteVendingUpgradeTaskByUpgradeIds(upgradeId.split(","));
		for (VendingUpgradeTask vendingUpgradeTask : vendingUpgradeTaskList) {
			vendingUpgradeTask.setLogid(UUID.randomUUID().toString());
			vendingUpgradeTask.setTaskId(SystemUtil.getSeqId(corpId, "as_vending_upgrade_task"));
			String siteId = vendingUpgradeTask.getSiteId();
			Vending vending = SystemUtil.getVendingBase(siteId);
			if(vending==null) {
				throw new VendingNotExitException();
			}else {
				vendingUpgradeTask.setSiteName(vending.getSiteName());
			}		
			vendingUpgradeTask.setUpgradeId(upgradeId);
			vendingUpgradeTask.setState(Constant.UPGRADE_TASK_STATE_WAIT);
			vendingUpgradeTask.setCreateTime(DateUtils.getTime());
			vendingUpgradeTask.setCorpId(corpId);
			
			vendingUpgradeTaskMapper.insertVendingUpgradeTask(vendingUpgradeTask);
		}
		vendingUpgrade.setIsPush(Constant.IS_PUSH_FALSE);
	    vendingUpgradeMapper.updateVendingUpgrade(vendingUpgrade);
	    //立即投放通知终端
	    if(Constant.UPGRADE_ISSUEDTYPE_NOW.equals(vendingUpgrade.getIssuedType())) {
	    	int result = insertVendingCmd(vendingUpgrade);
			if(result!=1) {
				throw new CmdSendException();
			}
		}
	    return 1;
	}

	/**
     * 删除控制设备的升级，升级包括app升级、固件升级、视频软件升级对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	@Transactional
	public int deleteVendingUpgradeByIds(String ids)
	{
		//编号不存在
		if(StringUtils.isEmpty(ids)) {
			return 2;
		}
		//要删除的升级信息已经推送终端
		String[] upgradeIds = ids.split(",");
		for (String upgradeId : upgradeIds) {
			VendingUpgrade srcVendingUpgrade = vendingUpgradeMapper.selectVendingUpgradeById(upgradeId);
			if(Constant.UPGRADE_ISSUEDTYPE_NOW.equals(srcVendingUpgrade.getIssuedType())) {
				return 3;
			}else {
				if(srcVendingUpgrade.getPlanTime().compareTo(DateUtils.getTime())<=0) {
					return 3;
				}
			}
		}
		vendingUpgradeMapper.deleteVendingUpgradeByIds(upgradeIds);
		vendingUpgradeTaskMapper.deleteVendingUpgradeTaskByUpgradeIds(upgradeIds);
		return 1;
	}
	
	/**
	 * 校验升级信息
	 * 
	 * @param vendingUpgrade
	 * @return
	 */
	private int checkVendingUpgrade(VendingUpgrade vendingUpgrade) {
		String uName = vendingUpgrade.getuName();
		//升级名称为空
		if(StringUtils.isEmpty(uName)) {
			return 2;
		}
		//升级名称已存在
		VendingUpgrade vendingUpgradeSelect = new VendingUpgrade();
		vendingUpgradeSelect.setuName(uName);
		vendingUpgradeSelect.setCorpId(ShiroUtils.getCorpId());
		List<VendingUpgrade> vendingUpgradeList = vendingUpgradeMapper.selectVendingUpgradeList(vendingUpgrade);
		if(vendingUpgradeList!=null&&!vendingUpgradeList.isEmpty()) {
			for (VendingUpgrade vendingUpgradeExit : vendingUpgradeList) {
				if(!vendingUpgradeExit.getUpgradeId().equals(vendingUpgrade.getUpgradeId())) {
					return 3;
				}
			}
		}
		//升级类型为空
		if(StringUtils.isEmpty(vendingUpgrade.getuType())) {
			return 4;
		}
		//延迟升级的延迟时间不能为空
		if(Constant.UPGRADE_ISSUEDTYPE_LATER.equals(vendingUpgrade.getIssuedType())) {
			if(StringUtils.isEmpty(vendingUpgrade.getIssuedType())) {
				return 5;
			}
		}
		List<VendingUpgradeTask> vendingUpgradeTaskList = vendingUpgrade.getVendingUpgradeTaskList();
		//下发的售货机为空
		if(vendingUpgradeTaskList==null||vendingUpgradeTaskList.isEmpty()) {
			return 6;
		}
		
		for (VendingUpgradeTask vendingUpgradeTask : vendingUpgradeTaskList) {
			String siteId = vendingUpgradeTask.getSiteId();
			Vending vending = vendingMapper.selectVendingBySiteId(siteId);
			String curState = vending.getCurState();
			//下发的售货机未认证或已删除
			if(Constant.VENDING_CURSTATE_NOTREGISTER.equals(curState)) {
				return 7;
			}
			if(Constant.VENDING_CURSTATE_DELETE.equals(curState)) {
				return 8;
			}
			//下发的售货机存在待升级的任务
			List<VendingUpgradeTask> upgradeTaskList = vendingUpgradeTaskMapper.selectNotFinishVendingUpgradeTaskListBySiteId(vendingUpgradeTask);
			if(upgradeTaskList!=null&&!upgradeTaskList.isEmpty()) {
				return 9;
			}
		}
		return 1;
	}

	/**
	 * 查询未推送的升级任务
	 * 
	 * @param vendingUpgrade
	 * @return
	 */
	@Override
	public List<VendingUpgrade> selectNotPushVendingUpgradeList(VendingUpgrade vendingUpgrade) {
		return vendingUpgradeMapper.selectNotPushVendingUpgradeList(vendingUpgrade);
	}
}
