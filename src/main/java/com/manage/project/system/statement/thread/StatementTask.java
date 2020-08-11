/**
 * 
 */
package com.manage.project.system.statement.thread;

import java.util.List;

import com.manage.common.utils.spring.SpringUtils;
import com.manage.project.system.statement.domain.StatementSupply;
import com.manage.project.system.statement.service.IStatementSupplyService;
import com.manage.project.system.statement.vo.StatementVo;

/**
 * 导入对账多线程
 * 
 * @author zhangjiawei
 * @date 2018年10月29日
 * 
 */
public class StatementTask implements Runnable{
	
	/**导入的账单信息*/
	private List<StatementVo> importList;
	/**要对账的补货单查询条件*/
	private StatementSupply statementSupply;
	
	private IStatementSupplyService statementSupplyService= SpringUtils.getBean(IStatementSupplyService.class);;
	
	public List<StatementVo> getImportList() {
		return importList;
	}

	public void setImportList(List<StatementVo> importList) {
		this.importList = importList;
	}

	public StatementSupply getStatementSupply() {
		return statementSupply;
	}

	public void setStatementSupply(StatementSupply statementSupply) {
		this.statementSupply = statementSupply;
	}

	/**
	 * 执行对账过程
	 */
	@Override
	public void run() {
		statementSupplyService.importStatement(importList, statementSupply);
	}

}
