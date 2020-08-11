package com.manage.project.system.statement.service;

import com.manage.project.system.statement.vo.StatementSupplyVo;

/**
 * 补货账单结算服务层接口
 * 
 * @author zhangjiawei
 * @date 2018-10-16
 */
public interface IStatementBalanceService {
	

	/**
	 * 对账结算
	 * 
	 * @param logid 补货对账id
	 * @return
	 */
	public int balance(StatementSupplyVo statementSupplyVo);
	
}
