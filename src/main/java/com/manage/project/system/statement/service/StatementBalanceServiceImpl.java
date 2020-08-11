package com.manage.project.system.statement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.common.utils.StringUtils;
import com.manage.project.system.base.domain.PayconfigAlipay;
import com.manage.project.system.base.domain.PayconfigWechat;
import com.manage.project.system.base.mapper.PayconfigAlipayMapper;
import com.manage.project.system.base.mapper.PayconfigWechatMapper;
import com.manage.project.system.statement.domain.StatementBalance;
import com.manage.project.system.statement.mapper.StatementBalanceMapper;
import com.manage.project.system.statement.mapper.StatementProductMapper;
import com.manage.project.system.statement.mapper.StatementSupplyMapper;
import com.manage.project.system.statement.vo.StatementProductVo;
import com.manage.project.system.statement.vo.StatementSupplyVo;
import com.manage.project.system.util.SystemUtil;

/**
 * 补货账单结算服务层实现
 * 
 * @author zhangjiawei
 * @date 2018-10-16
 */
@Service
public class StatementBalanceServiceImpl implements IStatementBalanceService {
	
	@Autowired
	private StatementBalanceMapper statementBalanceMapper;
	
	@Autowired
	private StatementSupplyMapper statementSupplyMapper;
	
	@Autowired
	private StatementProductMapper statementProductMapper;
	
	@Autowired
	private PayconfigAlipayMapper payconfigAlipayMapper;

	@Autowired
	private PayconfigWechatMapper payconfigWechatMapper;

	/**
	 * 对账结算
	 * 
	 * @param logid 补货对账id
	 * @return
	 */
	@Override
	@Transactional
	public int balance(StatementSupplyVo vo) {
		//StatementSupplyVo vo = statementSupplyMapper.selectStatementSupplyBySOrderIdAndSiteId(sOrderId, siteId);
		//查出每一项补货商品售出明细,遍历得到退款总金额
		StatementProductVo statementProductVo = new StatementProductVo();
		statementProductVo.setSOrderId(vo.getSOrderId());
		statementProductVo.setSiteId(vo.getSiteId());
		List<StatementProductVo> list = statementProductMapper.selectStatementProductList(statementProductVo);
		float saleReturn=0F;
		for (StatementProductVo statementProduct : list) {
			saleReturn+=statementProduct.getSaleReturn();
		}
		//总应给款
		float payPMoney=vo.getSaleRMoney()-saleReturn;
		//净收入金额
		float incomeMoney=payPMoney-vo.getBuyMoney();
		//根据字典表中的信息查出费率,计算手续费
		//后续加入缓存
		List<PayconfigAlipay> alipay = payconfigAlipayMapper.selectPayconfigAlipayList(null);
		float alipayRate=0F;
		if(StringUtils.isNotEmpty(alipay)) {
			alipayRate=alipay.get(0).getRate()/100;
		}
		List<PayconfigWechat> wechat = payconfigWechatMapper.selectPayconfigWechatList(null);
		float wechatRate=0F;
		if(StringUtils.isNotEmpty(wechat)) {
			wechatRate=alipay.get(0).getRate()/100;
		}
		float poundage=0;
		for (StatementProductVo statementProduct : list) {
			//支付宝手续费
			if("1".equals(statementProduct.getPayType())) {
				poundage+=statementProduct.getSaleRMoney()*alipayRate;
			}
			//微信手续费
			if("2".equals(statementProduct.getPayType())) {
				poundage+=statementProduct.getSaleRMoney()*wechatRate;
			}
		}
		//应给款
		float payRMoney=payPMoney-poundage;
		
		int ret=0;
		String corpId=vo.getCorpId();
		String balanceId=SystemUtil.getSeqId(corpId, "as_statement_balance");
		StatementBalance statementBalance = new StatementBalance();
		statementBalance.setLogid(UUID.randomUUID().toString());
		statementBalance.setBalanceId(balanceId);
		statementBalance.setCorpId(corpId);
		statementBalance.setDistrictId(vo.getDistrictId());
		statementBalance.setTradeSTime(vo.getTradeSTime());
		statementBalance.setTradeEtime(vo.getTradeEtime());
		statementBalance.setPayPMoney(payPMoney);
		statementBalance.setIncomeMoney(incomeMoney);
		statementBalance.setPoundage(poundage);
		statementBalance.setPayRMoney(payRMoney);
		
		int ret1 = statementBalanceMapper.insertStatementBalance(statementBalance);
		//修改补货对账表的结算状态和结算编号
		vo.setCurState("2");
		vo.setSbalanceId(balanceId);
		int ret2 = statementSupplyMapper.updateStatementSupply(vo);
		return (ret1+ret2==2?1:0);
	}
	
}
