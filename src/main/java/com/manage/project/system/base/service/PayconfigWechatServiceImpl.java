package com.manage.project.system.base.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.framework.config.ManageConfig;
import com.manage.project.system.base.domain.PayconfigWechat;
import com.manage.project.system.base.mapper.PayconfigWechatMapper;
import com.manage.project.system.util.SystemUtil;

/**
 * 微信支付配置 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class PayconfigWechatServiceImpl implements IPayconfigWechatService 
{
	@Autowired
	private PayconfigWechatMapper payconfigWechatMapper;
	
	@Autowired
	private ManageConfig manageConfig;

	/**
     * 查询微信支付配置信息
     * 
     * @param logid 微信支付配置ID
     * @return 微信支付配置信息
     */
    @Override
	public PayconfigWechat selectPayconfigWechatById(String logid)
	{
	    return payconfigWechatMapper.selectPayconfigWechatById(logid);
	}
	
	/**
     * 查询微信支付配置列表
     * 
     * @param payconfigWechat 微信支付配置信息
     * @return 微信支付配置集合
     */
	@Override
	public List<PayconfigWechat> selectPayconfigWechatList(PayconfigWechat payconfigWechat)
	{
	    return payconfigWechatMapper.selectPayconfigWechatList(payconfigWechat);
	}
	
    /**
     * 新增微信支付配置
     * 
     * @param payconfigWechat 微信支付配置信息
     * @return 结果
     */
	@Override
	public int insertPayconfigWechat(PayconfigWechat payconfigWechat)
	{
		payconfigWechat.setLogid(UUID.randomUUID().toString());		
		payconfigWechat.setPayConfigId(SystemUtil.getSeqId(payconfigWechat.getCorpId(), "as_payconfig_wechat"));
		payconfigWechat.setCreateTime(DateUtils.getTime());
		
		if(StringUtils.isEmpty(payconfigWechat.getTitle())) {
			payconfigWechat.setTitle("szZHILAI");
		}
		if(StringUtils.isEmpty(payconfigWechat.getBody())) {
			payconfigWechat.setBody("szZHILAI");
		}
		if(StringUtils.isEmpty(payconfigWechat.getSubMchId())) {
			payconfigWechat.setSubMchId("");
		}
		if(StringUtils.isEmpty(payconfigWechat.getDeviceInfo())) {
			payconfigWechat.setDeviceInfo("WEB");
		}
		if(StringUtils.isEmpty(payconfigWechat.getSpbillCreateIp())) {
			payconfigWechat.setSpbillCreateIp("127.0.0.1");
		}
		if(StringUtils.isEmpty(payconfigWechat.getFeeType())) {
			payconfigWechat.setFeeType("CNY");
		}
		if(StringUtils.isEmpty(payconfigWechat.getTradeType())) {
			payconfigWechat.setTradeType("NATIVE");
		}
		/*if(StringUtils.isNotEmpty(payconfigWechat.getLicense())&&(!payconfigWechat.getLicense().startsWith("/front"))) {
			payconfigWechat.setLicense(manageConfig.getLicenseFileUrl()+payconfigWechat.getLicense());
		}*/
		
	    return payconfigWechatMapper.insertPayconfigWechat(payconfigWechat);
	}
	
	/**
     * 修改微信支付配置
     * 
     * @param payconfigWechat 微信支付配置信息
     * @return 结果
     */
	@Override
	public int updatePayconfigWechat(PayconfigWechat payconfigWechat)
	{
		/*if(StringUtils.isNotEmpty(payconfigWechat.getLicense())&&(!payconfigWechat.getLicense().startsWith("/front"))) {
			payconfigWechat.setLicense(manageConfig.getLicenseFileUrl()+payconfigWechat.getLicense());
		}*/
		payconfigWechat.setCreateTime(DateUtils.getTime());
	    return payconfigWechatMapper.updatePayconfigWechat(payconfigWechat);
	}

	/**
     * 删除微信支付配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePayconfigWechatByIds(String ids)
	{
		return payconfigWechatMapper.deletePayconfigWechatByIds(Convert.toStrArray(ids));
	}

	/**
     * 查询微信支付配置
     * 
     * @param payconfigWechat 微信支付配置信息
     * @return 微信支付配置
     */
	@Override
	public PayconfigWechat selectPayconfigWechat(PayconfigWechat payconfigWechat) {
		return payconfigWechatMapper.selectPayconfigWechat(payconfigWechat);
	}
	
}
