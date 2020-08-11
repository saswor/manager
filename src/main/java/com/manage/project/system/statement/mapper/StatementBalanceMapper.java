package com.manage.project.system.statement.mapper;

import com.manage.project.system.statement.domain.StatementBalance;	

/**
 * 补货账单结算信息 数据层
 * 
 * @author zhangjiawei
 * @date 2018-10-16
 */
public interface StatementBalanceMapper {

	/**
     * 插入补货账单结算信息
     * 
     * @param statementBalance 补货账单结算信息
     * @return 结果
     */
	public int insertStatementBalance(StatementBalance statementBalance);
	
}