package com.manage.project.system.base.mapper;

import java.util.List;

import com.manage.project.system.base.domain.PayconfigWechat;	

/**
 * 微信支付配置 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface PayconfigWechatMapper 
{
	/**
     * 查询微信支付配置信息
     * 
     * @param logid 微信支付配置ID
     * @return 微信支付配置信息
     */
	public PayconfigWechat selectPayconfigWechatById(String logid);
	
	/**
     * 查询微信支付配置列表
     * 
     * @param payconfigWechat 微信支付配置信息
     * @return 微信支付配置集合
     */
	public List<PayconfigWechat> selectPayconfigWechatList(PayconfigWechat payconfigWechat);
	
	/**
     * 新增微信支付配置
     * 
     * @param payconfigWechat 微信支付配置信息
     * @return 结果
     */
	public int insertPayconfigWechat(PayconfigWechat payconfigWechat);
	
	/**
     * 修改微信支付配置
     * 
     * @param payconfigWechat 微信支付配置信息
     * @return 结果
     */
	public int updatePayconfigWechat(PayconfigWechat payconfigWechat);
	
	/**
     * 删除微信支付配置
     * 
     * @param logid 微信支付配置ID
     * @return 结果
     */
	public int deletePayconfigWechatById(String logid);
	
	/**
     * 批量删除微信支付配置
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deletePayconfigWechatByIds(String[] logids);

	/**
     * 查询微信支付配置
     * 
     * @param payconfigWechat 微信支付配置信息
     * @return 微信支付配置
     */
	public PayconfigWechat selectPayconfigWechat(PayconfigWechat payconfigWechat);
	
}