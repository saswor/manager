package com.manage.project.system.base.mapper;

import java.util.List;

import com.manage.project.system.base.domain.PayconfigAlipay;	

/**
 * 支付宝支付配置 数据层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface PayconfigAlipayMapper 
{
	/**
     * 查询支付宝支付配置信息
     * 
     * @param logid 支付宝支付配置ID
     * @return 支付宝支付配置信息
     */
	public PayconfigAlipay selectPayconfigAlipayById(String logid);
	
	/**
     * 查询支付宝支付配置列表
     * 
     * @param payconfigAlipay 支付宝支付配置信息
     * @return 支付宝支付配置集合
     */
	public List<PayconfigAlipay> selectPayconfigAlipayList(PayconfigAlipay payconfigAlipay);
	
	/**
     * 新增支付宝支付配置
     * 
     * @param payconfigAlipay 支付宝支付配置信息
     * @return 结果
     */
	public int insertPayconfigAlipay(PayconfigAlipay payconfigAlipay);
	
	/**
     * 修改支付宝支付配置
     * 
     * @param payconfigAlipay 支付宝支付配置信息
     * @return 结果
     */
	public int updatePayconfigAlipay(PayconfigAlipay payconfigAlipay);
	
	/**
     * 删除支付宝支付配置
     * 
     * @param logid 支付宝支付配置ID
     * @return 结果
     */
	public int deletePayconfigAlipayById(String logid);
	
	/**
     * 批量删除支付宝支付配置
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deletePayconfigAlipayByIds(String[] logids);

	/**
     * 查询支付宝支付配置
     * 
     * @param payconfigAlipay 支付宝支付配置信息
     * @return 支付宝支付配置
     */
	public PayconfigAlipay selectPayconfigAlipay(PayconfigAlipay payconfigAlipay);
	
}