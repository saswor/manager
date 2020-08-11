package com.manage.project.system.base.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.project.system.base.domain.PayconfigAlipay;
import com.manage.project.system.base.mapper.PayconfigAlipayMapper;
import com.manage.project.system.util.SystemUtil;

/**
 * 支付宝支付配置 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class PayconfigAlipayServiceImpl implements IPayconfigAlipayService 
{
	@Autowired
	private PayconfigAlipayMapper payconfigAlipayMapper;

	/**
     * 查询支付宝支付配置信息
     * 
     * @param logid 支付宝支付配置ID
     * @return 支付宝支付配置信息
     */
    @Override
	public PayconfigAlipay selectPayconfigAlipayById(String logid)
	{
	    return payconfigAlipayMapper.selectPayconfigAlipayById(logid);
	}
	
	/**
     * 查询支付宝支付配置列表
     * 
     * @param payconfigAlipay 支付宝支付配置信息
     * @return 支付宝支付配置集合
     */
	@Override
	public List<PayconfigAlipay> selectPayconfigAlipayList(PayconfigAlipay payconfigAlipay)
	{
	    return payconfigAlipayMapper.selectPayconfigAlipayList(payconfigAlipay);
	}
	
    /**
     * 新增微信支付配置
     * 
     * @param payconfigAlipay 微信支付配置信息
     * @return 结果
     */
	@Override
	public int insertPayconfigAlipay(PayconfigAlipay payconfigAlipay)
	{
		payconfigAlipay.setLogid(UUID.randomUUID().toString());		
		payconfigAlipay.setPayConfigId(SystemUtil.getSeqId(payconfigAlipay.getCorpId(), "as_payconfig_aliPay"));
		payconfigAlipay.setCreateTime(DateUtils.getTime());
		
		if(StringUtils.isEmpty(payconfigAlipay.getPublKey())) {
			payconfigAlipay.setPublKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZ+guBcUusCty6Gb8FotSex/RRX7bPDCKq0RIGJBc9bgPh+w/wX3EbngdQ7ox7QZxuiXikD3+pygiQqyQUO0zYdEgNB0RcDOzUcPkedAOff9lduQY3VTne5pGqJfWWtO+ZtqUzOUNY7liT7XDHT2W1Lt7WCSqOK8Ph++0Xv7TSkwIDAQAB");
		}
		if(StringUtils.isEmpty(payconfigAlipay.getSignType())) {
			payconfigAlipay.setSignType("RSA");
		}
		if(StringUtils.isEmpty(payconfigAlipay.getAlipayApi())) {
			payconfigAlipay.setAlipayApi("https://openapi.alipay.com/gateway.do");
		}
		if(StringUtils.isEmpty(payconfigAlipay.getAuthBack())) {
			payconfigAlipay.setAuthBack("http://localhost:8084/vending_server/aliNotify");
		}
		if(StringUtils.isEmpty(payconfigAlipay.getReturnNotice())) {
			payconfigAlipay.setReturnNotice("");
		}
		if(StringUtils.isEmpty(payconfigAlipay.getPrivKey())) {
			payconfigAlipay.setPrivKey("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJn6C4FxS6wK3LoZvwWi1J7H9FFfts8MIqrREgYkFz1uA+H7D/BfcRueB1DujHtBnG6JeKQPf6nKCJCrJBQ7TNh0SA0HRFwM7NRw+R50A59/2V25BjdVOd7mkaol9Za075m2pTM5Q1juWJPtcMdPZbUu3tYJKo4rw+H77Re/tNKTAgMBAAECgYEAkE3S8qEndjlH2/G9IJ8V/SkQRCMbxLdu1f/SkIbKcPvmD6enrHMGIgfce3DVn75BySZ1NeyAjLOXi12ZFozs2osCnxzyMFx3q21TPWDpML7uUluc0xK0eJwIsbbf8SBBQHL3iKh3SXEIbkJo1q/Ks7lELuLREsIiOUQisBVQ00ECQQDLKVg8zLzovshDa6Po1A+FrFrDQLScXcDa+k52N6ew2TuaIX7MY8fdydVC4PUr2nJPsw9yB8Ob1JQpUxVgg5TLAkEAwgXwdk0V74M5yeR2YALTA+ZIfcTqeXl0a9m487UH34yFErpfx/QD7x9Ld4hDijAzRE3nI3LLSE6wAu8nGbdIWQJBAJ321rnng1cAyFvLvRWSlUMdgTDiBcyzBPn+t7UpjwRt0+8C7kFjsAreHSi0G01uu4UGuBK0wL5Qaqc7B5sUlEMCQBh/jXwygYmLhTlzymqXELPWWOLggUazSjrv88V53zOf15GkzME/k1uXFP1TzEtfiKmgOIYmUXqgzOKh9crudIkCQQCCHbHii0FpZCh01jRzuHdAMJHdmqCCwVjIHNiIMOo2kXhP/ZV8IlGTH5jDRViz7UIkdC8r/JYhuLdGmE8xmbS+");
		}
		if(StringUtils.isEmpty(payconfigAlipay.getTitle())){
			payconfigAlipay.setTitle("ZhiLai");
		}
		if(StringUtils.isEmpty(payconfigAlipay.getBody())) {
			payconfigAlipay.setBody("ZhiLai");
		}
		if(StringUtils.isEmpty(payconfigAlipay.getTradeType())) {
			payconfigAlipay.setTradeType("10");
		}
		
	    return payconfigAlipayMapper.insertPayconfigAlipay(payconfigAlipay);
	}
	
	/**
     * 修改支付宝支付配置
     * 
     * @param payconfigAlipay 支付宝支付配置信息
     * @return 结果
     */
	@Override
	public int updatePayconfigAlipay(PayconfigAlipay payconfigAlipay)
	{
	    return payconfigAlipayMapper.updatePayconfigAlipay(payconfigAlipay);
	}

	/**
     * 删除支付宝支付配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePayconfigAlipayByIds(String ids)
	{
		return payconfigAlipayMapper.deletePayconfigAlipayByIds(Convert.toStrArray(ids));
	}

	/**
     * 查询支付宝支付配置
     * 
     * @param payconfigAlipay 支付宝支付配置信息
     * @return 支付宝支付配置
     */
	@Override
	public PayconfigAlipay selectPayconfigAlipay(PayconfigAlipay payconfigAlipay) {
		return payconfigAlipayMapper.selectPayconfigAlipay(payconfigAlipay);
	}
	
}
