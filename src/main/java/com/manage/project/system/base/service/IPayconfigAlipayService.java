package com.manage.project.system.base.service;

import java.util.List;

import com.manage.project.system.base.domain.PayconfigAlipay;

/**
 * 支付宝支付配置 服务层
 * 
 * @author xufeng
 * @date 2018-09-02
 */
public interface IPayconfigAlipayService 
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
     * 删除支付宝支付配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePayconfigAlipayByIds(String ids);

	/**
     * 查询支付宝支付配置
     * 
     * @param payconfigAlipay 支付宝支付配置信息
     * @return 支付宝支付配置
     */
	public PayconfigAlipay selectPayconfigAlipay(PayconfigAlipay payconfigAlipay);
	
}
